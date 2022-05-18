package com.abraxas.betternetheriterewrite.utils;

import com.abraxas.betternetheriterewrite.BetterNetheriteRewrite;
import com.abraxas.betternetheriterewrite.Config;
import org.bukkit.Bukkit;
import org.bukkit.Keyed;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.*;

import java.util.*;

public class RecipeManager {
    static BetterNetheriteRewrite main = BetterNetheriteRewrite.getInstance();

    static Config config = main.getConfiguration();

    public static void registerRecipes() {
        removeAllPluginRecipes();
        if (config.craftingMode == Config.CraftingMode.CRAFTING_TABLE)
            registerNetheriteItems();
        else if (config.craftingMode == Config.CraftingMode.IMPROVED_VANILLA)
            registerImprovedUpgradingSmithingTableItems();

        try {
            registerBetterNetheriteScrapSmelting();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void registerImprovedUpgradingSmithingTableItems() {
        // Wood to Stone
        addSmithingRecipe("wood_to_stone_sword_smithing",
                new ItemStack(Material.STONE_SWORD),
                Material.WOODEN_SWORD,
                new ItemStack(Material.COBBLESTONE));
        addSmithingRecipe("wood_to_stone_axe_smithing",
                new ItemStack(Material.STONE_AXE),
                Material.WOODEN_AXE,
                new ItemStack(Material.COBBLESTONE));
        addSmithingRecipe("wood_to_stone_shovel_smithing",
                new ItemStack(Material.STONE_SHOVEL),
                Material.WOODEN_SHOVEL,
                new ItemStack(Material.COBBLESTONE));
        addSmithingRecipe("wood_to_stone_hoe_smithing",
                new ItemStack(Material.STONE_HOE),
                Material.WOODEN_HOE,
                new ItemStack(Material.COBBLESTONE));
        addSmithingRecipe("wood_to_stone_pickaxe_smithing",
                new ItemStack(Material.STONE_PICKAXE),
                Material.WOODEN_PICKAXE,
                new ItemStack(Material.COBBLESTONE));

        // Stone to Iron
        addSmithingRecipe("stone_to_iron_sword_smithing",
                new ItemStack(Material.IRON_SWORD),
                Material.STONE_SWORD,
                new ItemStack(Material.IRON_INGOT));
        addSmithingRecipe("stone_to_iron_axe_smithing",
                new ItemStack(Material.IRON_AXE),
                Material.STONE_AXE,
                new ItemStack(Material.IRON_INGOT));
        addSmithingRecipe("stone_to_iron_shovel_smithing",
                new ItemStack(Material.IRON_SHOVEL),
                Material.STONE_SHOVEL,
                new ItemStack(Material.IRON_INGOT));
        addSmithingRecipe("stone_to_iron_hoe_smithing",
                new ItemStack(Material.IRON_HOE),
                Material.STONE_HOE,
                new ItemStack(Material.IRON_INGOT));
        addSmithingRecipe("stone_to_iron_pickaxe_smithing",
                new ItemStack(Material.IRON_PICKAXE),
                Material.STONE_PICKAXE,
                new ItemStack(Material.IRON_INGOT));

        // Iron to Gold
        addSmithingRecipe("iron_to_gold_sword_smithing",
                new ItemStack(Material.GOLDEN_SWORD),
                Material.IRON_SWORD,
                new ItemStack(Material.GOLD_INGOT));
        addSmithingRecipe("iron_to_gold_axe_smithing",
                new ItemStack(Material.GOLDEN_AXE),
                Material.IRON_AXE,
                new ItemStack(Material.GOLD_INGOT));
        addSmithingRecipe("iron_to_gold_shovel_smithing",
                new ItemStack(Material.GOLDEN_SHOVEL),
                Material.IRON_SHOVEL,
                new ItemStack(Material.GOLD_INGOT));
        addSmithingRecipe("iron_to_gold_hoe_smithing",
                new ItemStack(Material.GOLDEN_HOE),
                Material.IRON_HOE,
                new ItemStack(Material.GOLD_INGOT));
        addSmithingRecipe("iron_to_gold_pickaxe_smithing",
                new ItemStack(Material.GOLDEN_PICKAXE),
                Material.IRON_PICKAXE,
                new ItemStack(Material.GOLD_INGOT));
        addSmithingRecipe("iron_to_gold_helmet_smithing",
                new ItemStack(Material.GOLDEN_HELMET),
                Material.IRON_HELMET,
                new ItemStack(Material.GOLD_INGOT));
        addSmithingRecipe("iron_to_gold_chestplate_smithing",
                new ItemStack(Material.GOLDEN_CHESTPLATE),
                Material.IRON_CHESTPLATE,
                new ItemStack(Material.GOLD_INGOT));
        addSmithingRecipe("iron_to_gold_leggings_smithing",
                new ItemStack(Material.GOLDEN_LEGGINGS),
                Material.IRON_LEGGINGS,
                new ItemStack(Material.GOLD_INGOT));
        addSmithingRecipe("iron_to_gold_boots_smithing",
                new ItemStack(Material.GOLDEN_BOOTS),
                Material.IRON_BOOTS,
                new ItemStack(Material.GOLD_INGOT));

        // Iron to Diamond
        addSmithingRecipe("iron_to_diamond_sword_smithing",
                new ItemStack(Material.DIAMOND_SWORD),
                Material.IRON_SWORD,
                new ItemStack(Material.DIAMOND));
        addSmithingRecipe("iron_to_diamond_axe_smithing",
                new ItemStack(Material.DIAMOND_AXE),
                Material.IRON_AXE,
                new ItemStack(Material.DIAMOND));
        addSmithingRecipe("iron_to_diamond_shovel_smithing",
                new ItemStack(Material.DIAMOND_SHOVEL),
                Material.IRON_SHOVEL,
                new ItemStack(Material.DIAMOND));
        addSmithingRecipe("iron_to_diamond_hoe_smithing",
                new ItemStack(Material.DIAMOND_HOE),
                Material.IRON_HOE,
                new ItemStack(Material.DIAMOND));
        addSmithingRecipe("iron_to_diamond_pickaxe_smithing",
                new ItemStack(Material.DIAMOND_PICKAXE),
                Material.IRON_PICKAXE,
                new ItemStack(Material.DIAMOND));
        addSmithingRecipe("iron_to_diamond_helmet_smithing",
                new ItemStack(Material.DIAMOND_HELMET),
                Material.IRON_HELMET,
                new ItemStack(Material.DIAMOND));
        addSmithingRecipe("iron_to_diamond_chestplate_smithing",
                new ItemStack(Material.DIAMOND_CHESTPLATE),
                Material.IRON_CHESTPLATE,
                new ItemStack(Material.DIAMOND));
        addSmithingRecipe("iron_to_diamond_leggings_smithing",
                new ItemStack(Material.DIAMOND_LEGGINGS),
                Material.IRON_LEGGINGS,
                new ItemStack(Material.DIAMOND));
        addSmithingRecipe("iron_to_diamond_boots_smithing",
                new ItemStack(Material.DIAMOND_BOOTS),
                Material.IRON_BOOTS,
                new ItemStack(Material.DIAMOND));
    }

    static void registerNetheriteItems() {
        //
        //NETHERITE SWORD
        //
        addCraftingRecipe("netherite_sword",
                true,
                Material.NETHERITE_SWORD,
                1,
                Arrays.asList(" N ", " N ", " S "),
                new HashMap<Character, Material>() {{
                    put('N', Material.NETHERITE_INGOT);
                    put('S', Material.STICK);
                }});
        //
        //NETHERITE AXE
        //
        addCraftingRecipe("netherite_axe",
                true,
                Material.NETHERITE_AXE,
                1,
                Arrays.asList("NN ", "NS ", " S "),
                new HashMap<Character, Material>() {{
                    put('N', Material.NETHERITE_INGOT);
                    put('S', Material.STICK);
                }});
        addCraftingRecipe("netherite_axe_flipped",
                true,
                Material.NETHERITE_AXE,
                1,
                Arrays.asList(" NN", " SN", " S "),
                new HashMap<Character, Material>() {{
                    put('N', Material.NETHERITE_INGOT);
                    put('S', Material.STICK);
                }});
        //
        //NETHERITE SHOVEL
        //
        addCraftingRecipe("netherite_shovel",
                true,
                Material.NETHERITE_SHOVEL,
                1,
                Arrays.asList(" N ", " S ", " S "),
                new HashMap<Character, Material>() {{
                    put('N', Material.NETHERITE_INGOT);
                    put('S', Material.STICK);
                }});
        //
        //NETHERITE PICKAXE
        //
        addCraftingRecipe("netherite_pickaxe",
                true,
                Material.NETHERITE_PICKAXE,
                1,
                Arrays.asList("NNN", " S ", " S "),
                new HashMap<Character, Material>() {{
                    put('N', Material.NETHERITE_INGOT);
                    put('S', Material.STICK);
                }});
        //
        //NETHERITE HOE
        //
        addCraftingRecipe("netherite_hoe",
                true,
                Material.NETHERITE_HOE,
                1,
                Arrays.asList("NN ", " S ", " S "),
                new HashMap<Character, Material>() {{
                    put('N', Material.NETHERITE_INGOT);
                    put('S', Material.STICK);
                }});
        addCraftingRecipe("netherite_hoe_flipped",
                true,
                Material.NETHERITE_HOE,
                1,
                Arrays.asList(" NN", " S ", " S "),
                new HashMap<Character, Material>() {{
                    put('N', Material.NETHERITE_INGOT);
                    put('S', Material.STICK);
                }});
        //
        //NETHERITE HELMET
        //
        addCraftingRecipe("netherite_helmet",
                true,
                Material.NETHERITE_HELMET,
                1,
                Arrays.asList("NNN", "N N", "   "),
                new HashMap<Character, Material>() {{
                    put('N', Material.NETHERITE_INGOT);
                }});
        //
        //NETHERITE CHESTPLATE
        //
        addCraftingRecipe("netherite_chestplate",
                true,
                Material.NETHERITE_CHESTPLATE,
                1,
                Arrays.asList("N N", "NNN", "NNN"),
                new HashMap<Character, Material>() {{
                    put('N', Material.NETHERITE_INGOT);
                }});
        //
        //NETHERITE LEGGINGS
        //
        addCraftingRecipe("netherite_leggings",
                true,
                Material.NETHERITE_LEGGINGS,
                1,
                Arrays.asList("NNN", "N N", "N N"),
                new HashMap<Character, Material>() {{
                    put('N', Material.NETHERITE_INGOT);
                }});
        //
        //NETHERITE BOOTS
        //
        addCraftingRecipe("netherite_boots",
                true,
                Material.NETHERITE_BOOTS,
                1,
                Arrays.asList("   ", "N N", "N N"),
                new HashMap<Character, Material>() {{
                    put('N', Material.NETHERITE_INGOT);
                }});
    }

    static void registerBetterNetheriteScrapSmelting() throws Exception {
        removeRecipe("minecraft:netherite_scrap");
        removeRecipe("minecraft:netherite_scrap_from_blasting");

        var smeltType = Config.SmeltType.valueOf(config.ancientDebris.getStringOption("smelting.type"));
        addSmeltingRecipe("netherite_scrap",
                smeltType,
                Material.NETHERITE_SCRAP,
                config.ancientDebris.getIntOption("smelting.yield"),
                Material.ANCIENT_DEBRIS,
                config.ancientDebris.getIntOption("smelting.exp"),
                config.ancientDebris.getIntOption("smelting.time"));
    }

    static void addSmithingRecipe(String key, ItemStack result, Material baseMat, ItemStack itemNeeded) {
        NamespacedKey smithingRecKey = new NamespacedKey(main, key);
        SmithingRecipe smithingRec = new SmithingRecipe(smithingRecKey, result, new RecipeChoice.MaterialChoice(baseMat), new RecipeChoice.ExactChoice(itemNeeded));
        addRecipe(smithingRec);
    }

    static void addSmeltingRecipe(String key, Config.SmeltType type, Material result, int resultAmount, Material smelted, int EXP, int time) {
        NamespacedKey smeltingRecKey = new NamespacedKey(main, key);
        int am = resultAmount;
        if (am > 64) am = 64;
        if (am < 1) am = 1;
        ItemStack resultItem = new ItemStack(result, am);

        Recipe recToAdd;

        switch (type) {
            case FURNACE:
                recToAdd = new FurnaceRecipe(smeltingRecKey, resultItem, smelted, EXP, time);
                break;
            case BLAST_FURNACE:
                recToAdd = new BlastingRecipe(smeltingRecKey, resultItem, smelted, EXP, time);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
        addRecipe(recToAdd);
    }

    static void addSmeltingRecipe(String key, Config.SmeltType type, RecipeChoice result, int resultAmount, RecipeChoice smelted, int EXP, int time) {
        NamespacedKey smeltingRecKey = new NamespacedKey(main, key);
        int am = resultAmount;
        if (am > 64) am = 64;
        if (am < 1) am = 1;
        ItemStack resultItem = result.getItemStack();
        resultItem.setAmount(am);

        Recipe recToAdd;

        switch (type) {
            case FURNACE:
                recToAdd = new FurnaceRecipe(smeltingRecKey, resultItem, smelted, EXP, time);
                break;
            case BLAST_FURNACE:
                recToAdd = new BlastingRecipe(smeltingRecKey, resultItem, smelted, EXP, time);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
        addRecipe(recToAdd);
    }

    static void addCraftingRecipe(String key, boolean shaped, Material result, int resultAmount, List<String> rows, Map<Character, Material> ingredients) {
        NamespacedKey craftingRecKey = new NamespacedKey(main, key);
        int am = resultAmount;
        if (am > 64) am = 64;
        if (am < 1) am = 1;
        ItemStack resultItem = new ItemStack(result, am);

        Recipe recToAdd;

        if (shaped) {
            recToAdd = new ShapedRecipe(craftingRecKey, resultItem);
            ((ShapedRecipe) recToAdd).shape(rows.get(0), rows.get(1), rows.get(2));
            for (Character ingKey : ingredients.keySet()) {
                ((ShapedRecipe) recToAdd).setIngredient(ingKey, ingredients.get(ingKey));
            }
        } else {
            recToAdd = new ShapelessRecipe(craftingRecKey, resultItem);
            for (Character ingKey : ingredients.keySet()) {
                ((ShapelessRecipe) recToAdd).addIngredient(ingredients.get(ingKey));
            }
        }

        addRecipe(recToAdd);
    }

    public static void addRecipe(Recipe recipe) {
        Bukkit.addRecipe(recipe);
        if (config.debugMode) Utils.log("&aRecipe &e%s&a has been &2added.".formatted(((Keyed) recipe).getKey()));
    }

    public static void removeRecipe(String key) {
        Iterator<Recipe> it = Bukkit.recipeIterator();
        while (it.hasNext()) {
            Recipe rec = it.next();
            if (rec != null) {
                if (((Keyed) rec).getKey().toString().equals(key)) {
                    if (config.debugMode) Utils.log("&aRecipe &e%s&a has been &cremoved.".formatted(key));
                    it.remove();
                }
            }
        }
    }

    static void removeAllPluginRecipes() {
        Iterator<Recipe> it = Bukkit.recipeIterator();
        while (it.hasNext()) {
            Recipe rec = it.next();
            if (rec != null) {
                if (((Keyed) rec).getKey().toString().contains("betternetheriterewrite:")) {
                    if (config.debugMode)
                        Utils.log("&aRecipe &e%s&a has been &2added.".formatted(((Keyed) rec).getKey()));
                    it.remove();
                }
            }
        }
    }
}
