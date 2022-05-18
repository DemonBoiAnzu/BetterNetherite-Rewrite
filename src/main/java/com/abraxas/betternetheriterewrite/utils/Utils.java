package com.abraxas.betternetheriterewrite.utils;

import com.abraxas.betternetheriterewrite.BetterNetheriteRewrite;
import net.md_5.bungee.api.ChatColor;
import org.apache.commons.lang.WordUtils;
import org.bukkit.event.Listener;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    static Random random = new Random();

    public static String colorize(String string) {
        Pattern HEX_PATTERN = Pattern.compile("<#([A-Fa-f0-9]){6}>");
        Matcher matcher = HEX_PATTERN.matcher(string);
        while (matcher.find()) {
            final net.md_5.bungee.api.ChatColor hexColor = net.md_5.bungee.api.ChatColor.of(matcher.group().substring(1, matcher.group().length() - 1));
            final String before = string.substring(0, matcher.start());
            final String after = string.substring(matcher.end());
            string = before + hexColor + after;
            matcher = HEX_PATTERN.matcher(string);
        }
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static String formalizedString(String string) {
        return WordUtils.capitalize(string.toLowerCase().replace("_", " "));
    }

    public static void registerEvents(Listener listener) {
        var main = BetterNetheriteRewrite.getInstance();
        main.getServer().getPluginManager().registerEvents(listener, main);
    }

    public static boolean chanceOf(int chance) {
        return random.nextInt(chance) == 0;
    }

    public static Random getRandom() {
        return random;
    }

    public static void log(String message) {
        var main = BetterNetheriteRewrite.getInstance();
        main.getLogger().info(colorize(message));
    }
}
