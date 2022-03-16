package com.rainchat.cubecore.utils.loader;

import com.rainchat.cubecore.CubeCorePlugin;
import com.rainchat.cubecore.utils.objects.CustomFile;
import com.rainchat.cubecore.utils.general.ServerLog;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ResourceLoader {
    private static final int BUFFER_SIZE = 16 * 1024;
    private final File dataPath;

    private HashMap<String, Class<?>> cache = new HashMap<String, Class<?>>();

    private JarFile jarFile;

    private String jarFileName;

    private String packageName;

    private URLClassLoader classLoader;

    public ResourceLoader(JarFile jarFile, File folder, URLClassLoader classLoader) {
        this.jarFile = jarFile;
        this.dataPath = folder;
        this.classLoader = classLoader;
        cacheClasses();
    }

    public File getBaseDir() {
        return dataPath;
    }


    public File[] getFoldersFile(File direction, String homeFolder) {
        File directory = new File(direction, homeFolder);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        return directory.listFiles((pathname) -> {
            return pathname.getName().endsWith(".yml");
        });
    }

    private void cacheClasses() {


        Enumeration<JarEntry> entries = jarFile.entries();
        ArrayList<Class<?>> extensionClasses = new ArrayList<>();

        while (entries.hasMoreElements()) {

            JarEntry entry = entries.nextElement();


            if (entry.isDirectory() || !entry.getName().endsWith(".class"))
                continue;

            String className = entry.getName().substring(0, entry.getName().length() - 6).replace('/', '.');
            //ServerLog.log(Level.INFO, "Loader - Loading class: " + className);
            Class<?> clazz;
            try {
                clazz = classLoader.loadClass(className);
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
            cache.put(clazz.getName(), clazz);
        }

    }

    private InputStreamReader readerOf(String resource) {
        InputStream stream = classLoader.getResourceAsStream(resource);
        if (stream == null)
            throw new IllegalArgumentException("Resource \"" + resource + "\" could not be found");

        return new InputStreamReader(stream, StandardCharsets.UTF_8);
    }

    public YamlConfiguration loadFileConfig(String resource) {
        return YamlConfiguration.loadConfiguration(new File(dataPath, resource));
    }

    public YamlConfiguration loadJarConfig(String resource) {
        return YamlConfiguration.loadConfiguration(readerOf(resource));
    }

    public YamlConfiguration loadConfig(String resource)
            throws IOException, InvalidConfigurationException {
        YamlConfiguration result = new YamlConfiguration();
        File file = new File(dataPath, resource);

        result.setDefaults(YamlConfiguration.loadConfiguration(readerOf(resource)));

        if (!file.exists()) {
            if (!file.getParentFile().exists() && !file.getParentFile().mkdirs())
                throw new IOException("Could not create directories for: " + file.getName());

            // stream will not be null unless some other thread destroys the jar file
            // readerOf above would have thrown an exception had it been null
            try (InputStream stream = classLoader.getResourceAsStream(resource)) {
                try (FileOutputStream fos = new FileOutputStream(file)) {
                    byte[] buffer = new byte[BUFFER_SIZE];
                    int len;
                    while ((len = stream.read(buffer)) != -1)
                        fos.write(buffer, 0, len);
                }
            }
        }

        result.load(file);

        return result;
    }

    public CustomFile loadCustomFile(File direction, String fileName, String homeFolder, String jarHomeFolder) {
        YamlConfiguration result = new YamlConfiguration();
        File file = new File(direction, homeFolder + "/" + fileName);
        if (!file.exists()) {
            if (!file.getParentFile().exists() && !file.getParentFile().mkdirs()) {
                ServerLog.error("Could not create directories for: " + file.getName());
            }

            try {
                if (this.getResource(jarHomeFolder + "/" + fileName) == null) {
                    ServerLog.error("This path not exist - " + jarHomeFolder + "/" + fileName);
                }

                this.copyFile(this.getResource(jarHomeFolder + "/" + fileName), file);
            } catch (Exception var9) {
            }
        }

        try {
            result.load(file);
        } catch (InvalidConfigurationException | IOException var8) {
            var8.printStackTrace();
        }

        return new CustomFile(file, direction + "/" + homeFolder + "/" + fileName, CubeCorePlugin.getInstance());
    }

    public FileConfiguration loadCustomConfiguration(File direction,String fileName, String homeFolder, String jarHomeFolder) {

        YamlConfiguration result = new YamlConfiguration();
        File file = new File(direction, homeFolder + "/" + fileName);


        if (!file.exists()) {
            if (!file.getParentFile().exists() && !file.getParentFile().mkdirs()) {
                ServerLog.error("Could not create directories for: " + file.getName());
            }

            try {

                if (getResource(jarHomeFolder + "/" + fileName) == null) {
                    ServerLog.error("Такого пути нет - " + jarHomeFolder + "/" + fileName);
                }
                copyFile(getResource(jarHomeFolder + "/" + fileName),file);
            } catch (Exception exception) {
                exception.printStackTrace();
            }

        }

        try {
            result.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

        return result;
    }

    public YamlConfiguration loadConfig(File direction, String resource) {

        YamlConfiguration result = new YamlConfiguration();
        File file = new File(direction, resource);
        try {
            if (!file.exists()) {
                if (!file.getParentFile().exists() && !file.getParentFile().mkdirs()) {
                    ServerLog.error("Could not create directories for: " + file.getName());
                }

                copyFile(getResource(resource),file);

            }



            result.load(file);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public YamlConfiguration loadConfig(File direction, String fileDerection, String resource){

        YamlConfiguration result = new YamlConfiguration();
        File file = new File(direction, fileDerection);

        try {

            if (!file.exists()) {
                if (!file.getParentFile().exists() && !file.getParentFile().mkdirs()) {
                    ServerLog.error("Could not create directories for: " + file.getName());
                }
                copyFile(getResource(resource),file);
            }

            result.load(file);

        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return result;
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

    public void saveConfig(FileConfiguration config, String resource) {
        try {
            config.save(new File(dataPath, resource));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Nullable
    public final InputStream getResource(@NotNull String path) {
        JarEntry jarConfig = jarFile.getJarEntry(path);

        InputStream in = null;
        try {
            in = jarFile.getInputStream(jarConfig);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return in;
    }

}
