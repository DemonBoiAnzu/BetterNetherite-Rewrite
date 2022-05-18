package com.abraxas.betternetheriterewrite;

import com.abraxas.betternetheriterewrite.listeners.BlockEvents;
import com.abraxas.betternetheriterewrite.listeners.ServerEvents;
import com.abraxas.betternetheriterewrite.utils.RecipeManager;
import com.abraxas.betternetheriterewrite.utils.UpdateChecker;
import com.abraxas.betternetheriterewrite.utils.Utils;
import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIConfig;
import org.bukkit.plugin.java.JavaPlugin;

public final class BetterNetheriteRewrite extends JavaPlugin {
    static BetterNetheriteRewrite instance;
    Config config;

    public static BetterNetheriteRewrite getInstance() {
        return instance;
    }

    @Override
    public void onLoad() {
        CommandAPI.onLoad(new CommandAPIConfig());
    }

    @Override
    public void onEnable() {
        instance = this;
        CommandAPI.onEnable(this);
        config = new Config();

        RecipeManager.RegisterRecipes();
        Utils.registerEvents(new BlockEvents());
        Utils.registerEvents(new ServerEvents());
        Commands.register();

        UpdateChecker.checkForNewVersion();

        Utils.log("Successfully enabled.");
    }

    @Override
    public void onDisable() {
        Utils.log("Successfully disabled.");
    }

    public Config getConfiguration() {
        return config;
    }
}
