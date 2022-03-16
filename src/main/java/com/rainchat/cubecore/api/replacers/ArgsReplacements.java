package com.rainchat.cubecore.api.replacers;


import com.rainchat.cubecore.utils.placeholder.BaseReplacements;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ArgsReplacements extends BaseReplacements {

    public List<String> args;

    public static boolean isNumeric(String strNum) {
        int d = 0;
        if (strNum == null) {
            return false;
        }
        try {
            d = Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "args_";
    }

    @Override
    public String getReplacement(String base, String fullKey) {

        if (isNumeric(base)) {
            int number = Integer.parseInt(base);
            if (number <= args.size()) {
                return args.get(number);
            }
        }

        return "";

    }

}