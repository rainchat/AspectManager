package com.rainchat.cubecore.managers;

import com.rainchat.cubecore.api.replacers.ArgsReplacements;
import com.rainchat.cubecore.api.replacers.PlayerReplacements;
import com.rainchat.cubecore.utils.general.Color;
import com.rainchat.cubecore.utils.objects.StatickBuilder;
import com.rainchat.cubecore.utils.placeholder.BaseReplacements;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlaceholderManager  extends StatickBuilder<BaseReplacements> {
    private static final Pattern PH_KEY = Pattern.compile("%([\\w\\._-]+)%");

    public PlaceholderManager() {
    }

    public void registerDefaultActons() {
        this.register(new ArgsReplacements(), "args", new String[0]);
        this.register(new PlayerReplacements(), "player", new String[0]);
    }

    public String getReplacements(Player player, String template) {
        return this.getReplacements(player, template, this.getList(), new HashMap());
    }

    public String getReplacements(Player player, String template, List<BaseReplacements> replacements, HashMap<String, String> option) {
        Matcher m = PH_KEY.matcher(template);


            while(m.find()) {

                for (BaseReplacements replacement : replacements) {
                    replacement.setup(player, new String[]{(String) option.get(replacement.getName())});
                    String replace = replacement.getReplacement(m.group(1));
                    if (replace != null && !replace.isEmpty()) {
                        template = template.replace(m.group(), replace);
                        break;
                    }
                }
            }

            return Color.parseHexString(template);

    }

    public List<String> getReplacements(Player player, List<String> list, List<BaseReplacements> replacements, HashMap<String, String> option) {
        List<String> tempList = new ArrayList();
        Iterator var6 = list.iterator();

        while(var6.hasNext()) {
            String template = (String)var6.next();
            tempList.add(this.getReplacements(player, template, replacements, option));
        }

        return tempList;
    }
}
