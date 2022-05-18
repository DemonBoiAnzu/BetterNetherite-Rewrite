package com.abraxas.betternetheriterewrite;

import com.abraxas.betternetheriterewrite.utils.RecipeManager;
import com.abraxas.betternetheriterewrite.utils.UpdateChecker;
import com.abraxas.betternetheriterewrite.utils.Utils;
import dev.jorel.commandapi.CommandAPICommand;

public class Commands {
    static BetterNetheriteRewrite main = BetterNetheriteRewrite.getInstance();

    static Config config = main.getConfiguration();

    public static void register() {
        new CommandAPICommand("betternetherite")
                .withAliases("bn")
                .withSubcommand(new CommandAPICommand("reload")
                        .withPermission("betternetherite.admin")
                        .executes((sender, args) -> {
                            try {
                                config.loadConfig();
                                RecipeManager.RegisterRecipes();
                                UpdateChecker.checkForNewVersion();
                                sender.sendMessage(Utils.colorize("%s&aSuccessfully reloaded the config!".formatted(config.pluginPrefix)));

                                var updateCheckingReloadMsgEnabled = config.updateChecking.getBooleanOption("message.reload");
                                if (!updateCheckingReloadMsgEnabled) return;

                                UpdateChecker.sendNewVersionNotif(sender);
                            } catch (Exception e) {
                                e.printStackTrace();
                                sender.sendMessage(Utils.colorize("%s&cAn error occurred, please check console.".formatted(config.pluginPrefix)));
                            }
                        })
                ).register();
    }
}
