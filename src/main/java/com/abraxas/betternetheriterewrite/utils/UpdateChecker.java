package com.abraxas.betternetheriterewrite.utils;

import com.abraxas.betternetheriterewrite.BetterNetheriteRewrite;
import com.abraxas.betternetheriterewrite.Config;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.util.Consumer;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public class UpdateChecker {
    private static final String resourceLink = "https://www.spigotmc.org/resources/better-netherite.84526/";
    private static final int resourceId = 84526;
    static BetterNetheriteRewrite main = BetterNetheriteRewrite.getInstance();
    static Config config = main.getConfiguration();
    private static boolean outdated;
    private static String newVersion;

    public static void checkForNewVersion() {
        Utils.log(Utils.colorize("&7Checking for updates..."));
        getVersion(version -> {
            var latestVersion = Integer.parseInt(version.replaceAll("\\.", ""));
            var currentVersion = Integer.parseInt(main.getDescription().getVersion().replaceAll("\\.", ""));

            outdated = false;
            if (currentVersion == latestVersion)
                Utils.log(Utils.colorize("&7I'm on the latest version! (&e%s&7)".formatted(main.getDescription().getVersion())));
            else if (currentVersion > latestVersion)
                Utils.log(Utils.colorize("&7I'm on an in-dev version! (&e%s&7)".formatted(main.getDescription().getVersion())));
            else {
                outdated = true;
                newVersion = version;
                Utils.log(Utils.colorize("&7A new version is available to download! (&e%s - %s&7)".formatted(version, resourceLink)));
            }
        });
    }

    public static void sendNewVersionNotif(CommandSender sender) {
        if (!sender.isOp() || !sender.hasPermission("betternetherite.admin") || !outdated) return;

        TextComponent main = new TextComponent(Utils.colorize("%s&aA new version is available for download! ".formatted(config.pluginPrefix)));
        TextComponent link = new TextComponent(Utils.colorize("&7&o(&e&o%s&7&o)".formatted(newVersion)));
        link.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, resourceLink));
        main.addExtra(link);
        sender.spigot().sendMessage(main);
    }

    static void getVersion(final Consumer<String> consumer) {
        Bukkit.getScheduler().runTaskAsynchronously(main, () -> {
            try (InputStream inputStream = new URL("https://api.spigotmc.org/legacy/update.php?resource=%d".formatted(resourceId)).openStream(); Scanner scanner = new Scanner(inputStream)) {
                if (scanner.hasNext()) consumer.accept(scanner.next());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
