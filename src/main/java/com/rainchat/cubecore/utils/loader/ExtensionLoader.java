package com.rainchat.cubecore.utils.loader;


import com.rainchat.cubecore.utils.general.ServerLog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;

public final class ExtensionLoader {
    private final ClassLoader loader;
    private final File folder;
    private final Map<ClassLoader, List<CoreExtension>> classLoaders = new HashMap<>();

    public ExtensionLoader(ClassLoader loader, File folder) {
        this.loader = loader;
        this.folder = folder;
    }

    private URL urlOf(File file) {
        try {
            return file.toURI().toURL();
        } catch (Exception e) {
            return null;
        }
    }

    public List<CoreExtension> loadLocal() {
        // This is as much as bukkit checks, good enough for me!
        List<File> extensionFiles = processFilesFromFolder(folder);

        List<CoreExtension> extensions = new ArrayList<>();
        // Not a directory or unable to list files for some reason
        for (File f : extensionFiles) {
            extensions.addAll(load(f));
        }

        return extensions;
    }

    public List<File> processFilesFromFolder(File folder)
    {
        List<File> files = new ArrayList<>();
        File[] folderEntries = folder.listFiles();
        for (File entry : folderEntries)
        {
            if (entry.isDirectory()) {
                files.addAll(processFilesFromFolder(entry));
            } else if (entry.getName().endsWith(".jar")){
                files.add(entry);
            }
            // иначе вам попался файл, обрабатывайте его!
        }
        return files;
    }

    public List<CoreExtension> load(File extensionFile) {

        ServerLog.info("Loader - Reading file: " + extensionFile.getName());



        JarFile jar;
        try {
            jar = new JarFile(extensionFile);

        } catch (Exception e) {
            ServerLog.warning("Failed to load \"" + extensionFile + "\": is it a valid jar file?");
            e.printStackTrace();
            return Collections.emptyList();
        }



        URL[] jarURLs = {urlOf(extensionFile)};

        URLClassLoader newLoader = AccessController.doPrivileged(new PrivilegedAction<URLClassLoader>() {
            @Override
            public URLClassLoader run() {
                return new URLClassLoader(jarURLs, loader);
            }
        });




        Enumeration<JarEntry> entries = jar.entries();
        ArrayList<Class<?>> extensionClasses = new ArrayList<>();

        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();

            if (entry.isDirectory() || !entry.getName().endsWith(".class"))
                continue;

            String className = entry.getName().substring(0, entry.getName().length() - 6).replace('/', '.');
            //ServerLog.log(Level.INFO, "Loader - Loading class: " + className);
            Class<?> clazz;
            try {
                clazz = newLoader.loadClass(className);
            } catch (Throwable e) {
                // Hide these exceptions because extension may not be enabled
                ServerLog.warning("Could not load class \"" + className + "\"");
                // e.printStackTrace();
                continue;
            }

            if (CoreExtension.class.isAssignableFrom(clazz)) {
                //ServerLog.log(Level.WARNING, "Loader - Found extension class: " + className);
                extensionClasses.add(clazz);
            }
        }

        List<CoreExtension> extensions = new ArrayList<>();

        for (Class<?> extensionClass : extensionClasses) {
            //ServerLog.log(Level.INFO, "Loader - Constructing: " + extensionClass.getName());
            CoreExtension extension;
            try {
                extension = (CoreExtension) extensionClass.getConstructor().newInstance();
            } catch (Throwable e) {
                ServerLog.log(Level.WARNING, "Exception while constructing extension class \"" + extensionClass + "\"!");
                ServerLog.log(Level.WARNING, "Is it missing a default constructor?");
                e.printStackTrace();
                continue;
            }
            File file = new File(extensionFile.getParentFile(),extensionFile.getName().replace(".jar",""));
            file.mkdirs();
            extension.setDirection(file);
            extension.setLoader(new ResourceLoader(jar, file, newLoader));
            extensions.add(extension);
        }

        classLoaders.put(newLoader, extensions);

        return extensions;
    }

    private void copyFile(InputStream in, File out) throws Exception {
        try (InputStream fis = in; FileOutputStream fos = new FileOutputStream(out)) {
            byte[] buf = new byte[1024];
            int i;
            while ((i = fis.read(buf)) != -1) {
                fos.write(buf, 0, i);
            }
        }
    }

    void unload() {

    }

    public Map<ClassLoader, List<CoreExtension>> getClassLoaders(){
        return classLoaders;
    }
}
