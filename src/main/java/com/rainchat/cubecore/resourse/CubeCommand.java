package com.rainchat.cubecore.resourse;


import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import co.aikar.commands.annotation.Syntax;
import com.rainchat.cubecore.api.CubeCore;
import com.rainchat.cubecore.api.builder.MenuBuilder;
import com.rainchat.cubecore.gui.menu.SimpleInventory;
import com.rainchat.cubecore.gui.elements.DynamicGuiElement;
import com.rainchat.cubecore.gui.elements.GuiElementGroup;
import com.rainchat.cubecore.gui.elements.GuiPageElement;
import com.rainchat.cubecore.gui.elements.StaticGuiElement;
import com.rainchat.cubecore.utils.general.Chat;
import com.rainchat.cubecore.utils.general.Message;
import com.rainchat.cubecore.utils.general.ServerLog;
import com.rainchat.cubecore.utils.items.Item;
import com.rainchat.cubecore.utils.loader.CoreExtension;
import com.rainchat.cubecore.utils.objects.CustomFile;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@CommandAlias("cubecore")
public class CubeCommand extends BaseCommand {

    @Subcommand("open")
    @Syntax("<menu_name>")
    @CommandPermission("cubecore.commands.open")
    public void onAddClaims(Player player, String name) {
        File file = new File(CubeCore.getPlugin().getDataFolder() + File.separator + "menu", "test.yml");
        ServerLog.warning(file.getPath() + " " + file.getName());
        CustomFile customFile = new CustomFile(file,"menu", CubeCore.getPlugin());
        SimpleInventory simpleInventory = MenuBuilder.INSTANCE.getMenu(customFile.getName(), customFile);

        simpleInventory.show(player);
    }


    @Subcommand("reload")
    @CommandPermission("aspect.reload")
    public void onReload(CommandSender commandSender) {
        CubeCore.getAPI().onReload();
        Chat.sendTranslation(commandSender,true, Message.RELOAD.toString());
    }

    @Subcommand("module")
    @CommandPermission("aspect.module.*")
    public class SubCube extends BaseCommand {

        // Will require /acf test test1 to access (or any of the alternate formats such as /acf txt td1)
        @Subcommand("disable")
        public void onDisable(CommandSender sender, String testX) {
            sender.sendMessage("You got test inner test1: " + testX);
        }

        @Subcommand("enable")
        public void onEnable(CommandSender sender, String testX) {
            sender.sendMessage("You got test inner test1: " + testX);
        }

        @Subcommand("list")
        public void list(CommandSender sender) {
            List<CoreExtension> coreExtensionList = CubeCore.getAPI().getExtensions().getActiveExtensions();
            StringBuilder string = new StringBuilder();
            for (CoreExtension coreExtension: coreExtensionList) {
                if (coreExtension.isInitialized()) {
                    string.append(" &a").append(coreExtension.getName());
                } else {
                    string.append(" &c").append(coreExtension.getName());
                }
                string.append("&f,");
            }

            sender.sendMessage(Chat.translateRaw("&f&lModules (&a" + coreExtensionList.size() + "&f&l):" ));
            sender.sendMessage(Chat.translateRaw(string.toString()));
        }

    }


}
