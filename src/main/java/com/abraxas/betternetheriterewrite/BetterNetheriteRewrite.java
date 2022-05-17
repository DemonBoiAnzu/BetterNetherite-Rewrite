package com.abraxas.betternetheriterewrite;

import com.abraxas.betternetheriterewrite.listeners.Blocks;
import org.bukkit.plugin.java.JavaPlugin;

public final class BetterNetheriteRewrite extends JavaPlugin {
    static BetterNetheriteRewrite instance;
    Config config;

    @Override
    public void onEnable() {
        instance = this;
        config = new Config();

        Utils.registerEvents(new Blocks());
    }

    @Override
    public void onDisable() {
    }

    public Config getConfiguration() {
        return config;
    }

    public static BetterNetheriteRewrite getInstance(){
        return instance;
    }
}
