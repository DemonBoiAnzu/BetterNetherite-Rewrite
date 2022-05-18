package com.abraxas.betternetheriterewrite.listeners;

import com.abraxas.betternetheriterewrite.BetterNetheriteRewrite;
import com.abraxas.betternetheriterewrite.Config;
import com.abraxas.betternetheriterewrite.utils.Utils;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import static com.abraxas.betternetheriterewrite.utils.Utils.getRandom;

public class BlockEvents implements Listener {
    BetterNetheriteRewrite main = BetterNetheriteRewrite.getInstance();

    Config config = main.getConfiguration();

    @EventHandler
    public void smithingTableUse(PlayerInteractEvent event) {
        var player = event.getPlayer();
        var hand = event.getHand();
        var action = event.getAction();
        var block = event.getClickedBlock();

        if (hand == null || !hand.equals(EquipmentSlot.HAND)) return;
        if (!action.equals(Action.RIGHT_CLICK_BLOCK)) return;
        if (block == null || !block.getType().equals(Material.SMITHING_TABLE)) return;

        var craftingMode = config.craftingMode;
        if (craftingMode.equals(Config.CraftingMode.CRAFTING_TABLE)) {
            player.sendMessage(Utils.colorize("%s&cThe usage of Smithing Tables is disabled. If you're wanting to craft Netherite, use a crafting table instead!".formatted(main.getConfiguration().pluginPrefix)));
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void placeAncientDebris(BlockPlaceEvent event) {
        var player = event.getPlayer();
        var block = event.getBlock();

        if (!block.getType().equals(Material.ANCIENT_DEBRIS)) return;
        if (config.debugMode) return;
        block.setMetadata("placedByPlayer", new FixedMetadataValue(main, true));
    }

    @EventHandler
    public void mineAncientDebris(BlockBreakEvent event) throws Exception {
        if (event.isCancelled() || !event.isDropItems()) return;

        var block = event.getBlock();
        var player = event.getPlayer();
        var world = player.getWorld();

        if (!player.getGameMode().equals(GameMode.SURVIVAL)) return;
        if (!block.getType().equals(Material.ANCIENT_DEBRIS)) return;
        if (block.hasMetadata("placedByPlayer")) return;

        var scrapOnMine = config.ancientDebris.getBooleanOption("mining.scrap_on_mine.enabled");
        if (scrapOnMine) {
            var yield = config.ancientDebris.getIntOption("mining.scrap_on_mine.yield");
            var chance = config.ancientDebris.getIntOption("mining.scrap_on_mine.chance");

            if (Utils.chanceOf(chance)) {
                int am = getRandom().nextInt(yield);
                if (am < 1) am = 1;
                ItemStack scrap = new ItemStack(Material.NETHERITE_SCRAP, am);
                world.dropItem(block.getLocation(), scrap);
            }
            return;
        }

        var ingotOnMine = config.ancientDebris.getBooleanOption("mining.ingot_on_mine.enabled");
        if (ingotOnMine) {
            var yield = config.ancientDebris.getIntOption("mining.ingot_on_mine.yield");
            var chance = config.ancientDebris.getIntOption("mining.ingot_on_mine.chance");

            if (Utils.chanceOf(chance)) {
                int am = getRandom().nextInt(yield);
                if (am < 1) am = 1;
                ItemStack ingot = new ItemStack(Material.NETHERITE_INGOT, am);
                world.dropItem(block.getLocation(), ingot);
            }
        }
    }
}
