package com.abraxas.betternetheriterewrite.listeners;

import com.abraxas.betternetheriterewrite.BetterNetheriteRewrite;
import com.abraxas.betternetheriterewrite.Config;
import com.abraxas.betternetheriterewrite.utils.UpdateChecker;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class ServerEvents implements Listener {
    BetterNetheriteRewrite main = BetterNetheriteRewrite.getInstance();

    Config config = main.getConfiguration();

    @EventHandler
    public void playerJoin(PlayerJoinEvent event) throws Exception {
        var updateCheckingJoinMsgEnabled = config.updateChecking.getBooleanOption("message.join");
        if (!updateCheckingJoinMsgEnabled) return;

        UpdateChecker.sendNewVersionNotif(event.getPlayer());
    }
}
