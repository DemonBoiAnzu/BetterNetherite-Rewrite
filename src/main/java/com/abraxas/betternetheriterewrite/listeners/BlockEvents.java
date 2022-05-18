package com.abraxas.betternetheriterewrite.listeners;

import com.abraxas.betternetheriterewrite.BetterNetheriteRewrite;
import com.abraxas.betternetheriterewrite.Config;
import com.abraxas.betternetheriterewrite.utils.Utils;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.abraxas.betternetheriterewrite.utils.Utils.*;

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
            player.sendMessage(colorize("%s&cThe usage of Smithing Tables is disabled. If you're wanting to craft Netherite, use a crafting table instead!".formatted(main.getConfiguration().pluginPrefix)));
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void smithingTableClick(InventoryClickEvent event) throws Exception {
        //definitely in need of improvements, i cant be fucked right now, so, deal with it :p
        if (config.craftingMode != Config.CraftingMode.IMPROVED_VANILLA) return;

        var player = (Player) event.getWhoClicked();
        var slot = event.getSlot();
        if (player.getOpenInventory().getTitle().contains(colorize("Upgrade Gear"))) {
            Material matNeeded = null;
            ItemStack slot0Item = player.getOpenInventory().getItem(0);
            ItemStack slot1Item = player.getOpenInventory().getItem(1);
            ItemStack slot2Item = player.getOpenInventory().getItem(2);

            List<Material> validUpgradableItems = null;

            if (event.getSlot() != 2 ||
                    slot0Item == null ||
                    slot1Item == null ||
                    slot2Item == null ||
                    slot0Item.getType() == Material.AIR ||
                    slot2Item.getType() == Material.AIR ||
                    slot1Item.getType() == Material.AIR)
                return;

            if (config.improvedUpgrading.getBooleanOption("repair_on_upgrade") && slot2Item.getType() != Material.AIR) {
                var itemMeta = (Damageable) slot2Item.getItemMeta();
                if (itemMeta.getDamage() > 0) {
                    itemMeta.setDamage(0);
                    slot2Item.setItemMeta(itemMeta);
                    player.getOpenInventory().setItem(2, slot2Item);
                }
            }

            int matAmount = 0;

            if (slot0Item.getType().toString().contains("WOODEN")) {
                matAmount = config.improvedUpgrading.getIntOption("wood_to_stone_mat_req");
                if (matAmount < 1) return;
                validUpgradableItems = new ArrayList<>(Arrays.asList(Material.WOODEN_AXE,
                        Material.WOODEN_HOE,
                        Material.WOODEN_PICKAXE,
                        Material.WOODEN_SWORD,
                        Material.WOODEN_SHOVEL));
                matNeeded = Material.COBBLESTONE;
            } else if (slot0Item.getType().toString().contains("STONE")) {
                matAmount = config.improvedUpgrading.getIntOption("stone_to_iron_mat_req");
                if (matAmount < 1) return;
                validUpgradableItems = new ArrayList<>(Arrays.asList(Material.STONE_AXE,
                        Material.STONE_HOE,
                        Material.STONE_PICKAXE,
                        Material.STONE_SWORD,
                        Material.STONE_SHOVEL));
                matNeeded = Material.IRON_INGOT;
            } else if (slot0Item.getType().toString().contains("IRON") && slot1Item.getType().equals(Material.DIAMOND)) {
                matAmount = config.improvedUpgrading.getIntOption("iron_to_diamond_mat_req");
                if (matAmount < 1) return;
                validUpgradableItems = new ArrayList<>(Arrays.asList(Material.IRON_AXE,
                        Material.IRON_HOE,
                        Material.IRON_PICKAXE,
                        Material.IRON_SWORD,
                        Material.IRON_SHOVEL,
                        Material.IRON_HELMET,
                        Material.IRON_CHESTPLATE,
                        Material.IRON_LEGGINGS,
                        Material.IRON_BOOTS));
                matNeeded = Material.DIAMOND;
            } else if (slot0Item.getType().toString().contains("IRON") && slot1Item.getType().equals(Material.GOLD_INGOT)) {
                matAmount = config.improvedUpgrading.getIntOption("iron_to_gold_mat_req");
                if (matAmount < 1) return;
                validUpgradableItems = new ArrayList<>(Arrays.asList(Material.IRON_AXE,
                        Material.IRON_HOE,
                        Material.IRON_PICKAXE,
                        Material.IRON_SWORD,
                        Material.IRON_SHOVEL,
                        Material.IRON_HELMET,
                        Material.IRON_CHESTPLATE,
                        Material.IRON_LEGGINGS,
                        Material.IRON_BOOTS));
                matNeeded = Material.GOLD_INGOT;
            } else if (slot0Item.getType().toString().contains("DIAMOND")) {
                matAmount = config.improvedUpgrading.getIntOption("diamond_to_netherite_mat_req");
                if (matAmount < 1) return;
                validUpgradableItems = new ArrayList<>(Arrays.asList(Material.DIAMOND_AXE,
                        Material.DIAMOND_HOE,
                        Material.DIAMOND_PICKAXE,
                        Material.DIAMOND_SWORD,
                        Material.DIAMOND_SHOVEL,
                        Material.DIAMOND_HELMET,
                        Material.DIAMOND_CHESTPLATE,
                        Material.DIAMOND_LEGGINGS,
                        Material.DIAMOND_BOOTS));
                matNeeded = Material.NETHERITE_INGOT;
            }

            if (slot1Item.getType() != matNeeded) return;

            if (!validUpgradableItems.contains(slot0Item.getType())) return;

            if (slot1Item.getAmount() < matAmount) {
                event.setCancelled(true);
                player.sendMessage(Utils.colorize("%s&cNot enough &e%s &cprovided to upgrade your &e%s. &7(&ex%s Needed&7)".formatted(config.pluginPrefix, formalizedString(matNeeded.toString()), formalizedString(slot0Item.getType().toString()), matAmount)));
            } else {
                slot1Item.setAmount(slot1Item.getAmount() - (matAmount - 1));
                player.sendMessage(Utils.colorize("%s&aSuccessfully upgraded your &e%s &afor &ex%s %s.".formatted(config.pluginPrefix, formalizedString(slot0Item.getType().toString()), matAmount, formalizedString(matNeeded.toString()))));
            }
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
