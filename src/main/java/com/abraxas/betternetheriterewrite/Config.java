package com.abraxas.betternetheriterewrite;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import static com.abraxas.betternetheriterewrite.BetterNetheriteRewrite.getInstance;

public class Config {
    public boolean debugMode;
    public String pluginPrefix;
    public CraftingMode craftingMode;
    public ConfigOption updateChecking = new ConfigOption("update_checking");
    public ConfigOption ancientDebris = new ConfigOption("ancient_debris");
    public ConfigOption improvedUpgrading = new ConfigOption("improved_upgrading_settings");
    BetterNetheriteRewrite main = BetterNetheriteRewrite.getInstance();
    String configPath = "%s/config.json".formatted(getInstance().getDataFolder());
    File configFile;

    public Config() {
        setDefaults();
        try {
            loadConfig();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void setDefaults() {
        debugMode = false;
        pluginPrefix = "&6Better&cNetherite &7» ";
        craftingMode = CraftingMode.VANILLA;

        updateChecking.options.put("message.reload", true);
        updateChecking.options.put("message.join", true);

        ancientDebris.options.put("smelting.yield", 4);
        ancientDebris.options.put("smelting.time", 150);
        ancientDebris.options.put("smelting.exp", 30);
        ancientDebris.options.put("smelting.type", SmeltType.BLAST_FURNACE.toString());

        ancientDebris.options.put("mining.scrap_on_mine.enabled", true);
        ancientDebris.options.put("mining.scrap_on_mine.yield", 2);
        ancientDebris.options.put("mining.scrap_on_mine.chance", 15);

        ancientDebris.options.put("mining.ingot_on_mine.enabled", true);
        ancientDebris.options.put("mining.ingot_on_mine.yield", 1);
        ancientDebris.options.put("mining.ingot_on_mine.chance", 3);

        improvedUpgrading.options.put("repair_on_upgrade", true);
        improvedUpgrading.options.put("wood_to_stone_mat_req", 2);
        improvedUpgrading.options.put("stone_to_iron_mat_req", 2);
        improvedUpgrading.options.put("iron_to_diamond_mat_req", 2);
        improvedUpgrading.options.put("iron_to_gold_mat_req", 2);
        improvedUpgrading.options.put("diamond_to_netherite_mat_req", 2);
    }

    void loadConfig() throws Exception {
        if (!Files.exists(Path.of(configPath))) {
            main.saveResource("config.json", true);
            return;
        }
        configFile = new File(configPath);

        String fileContents = Files.readString(configFile.toPath(), StandardCharsets.UTF_8);
        JsonObject configObject = JsonParser.parseString(fileContents).getAsJsonObject();

        debugMode = configObject.get("debug").getAsBoolean();

        JsonObject settingsObject = configObject.get("settings").getAsJsonObject();
        pluginPrefix = settingsObject.get("chat_prefix").getAsString();
        craftingMode = CraftingMode.valueOf(settingsObject.get("crafting_mode").getAsString());

        JsonObject ancientDebrisObject = settingsObject.get("ancient_debris").getAsJsonObject();

        JsonObject ancientDebrisSmeltingObject = ancientDebrisObject.get("smelting").getAsJsonObject();
        ancientDebris.setOption("smelting.yield", ancientDebrisSmeltingObject.get("yield").getAsInt());
        ancientDebris.setOption("smelting.time", ancientDebrisSmeltingObject.get("time").getAsInt());
        ancientDebris.setOption("smelting.exp", ancientDebrisSmeltingObject.get("exp").getAsInt());
        SmeltType type = SmeltType.valueOf(ancientDebrisSmeltingObject.get("type").getAsString());
        ancientDebris.setOption("smelting.type", type.toString());

        JsonObject ancientDebrisMiningObject = ancientDebrisObject.get("mining").getAsJsonObject();

        JsonObject ancientDebrisScrapOnMineObject = ancientDebrisMiningObject.get("scrap_on_mine").getAsJsonObject();
        ancientDebris.setOption("mining.scrap_on_mine.enabled", ancientDebrisScrapOnMineObject.get("enabled").getAsBoolean());
        ancientDebris.setOption("mining.scrap_on_mine.yield", ancientDebrisScrapOnMineObject.get("yield").getAsInt());
        ancientDebris.setOption("mining.scrap_on_mine.chance", ancientDebrisScrapOnMineObject.get("chance").getAsInt());

        JsonObject ancientDebrisIngotOnMineObject = ancientDebrisMiningObject.get("ingot_on_mine").getAsJsonObject();
        ancientDebris.setOption("mining.ingot_on_mine.enabled", ancientDebrisIngotOnMineObject.get("enabled").getAsBoolean());
        ancientDebris.setOption("mining.ingot_on_mine.yield", ancientDebrisIngotOnMineObject.get("yield").getAsInt());
        ancientDebris.setOption("mining.ingot_on_mine.chance", ancientDebrisIngotOnMineObject.get("chance").getAsInt());

        JsonObject updateCheckingObject = settingsObject.get("update_checking").getAsJsonObject();
        JsonObject updateCheckMessageObject = updateCheckingObject.get("message").getAsJsonObject();
        updateChecking.setOption("message.reload", updateCheckMessageObject.get("reload").getAsBoolean());
        updateChecking.setOption("message.join", updateCheckMessageObject.get("join").getAsBoolean());

        JsonObject improvedUpgradingObject = settingsObject.get("improved_upgrading_settings").getAsJsonObject();
        improvedUpgrading.setOption("repair_on_upgrade", improvedUpgradingObject.get("repair_on_upgrade").getAsBoolean());
        improvedUpgrading.setOption("wood_to_stone_mat_req", improvedUpgradingObject.get("wood_to_stone_mat_req").getAsInt());
        improvedUpgrading.setOption("stone_to_iron_mat_req", improvedUpgradingObject.get("stone_to_iron_mat_req").getAsInt());
        improvedUpgrading.setOption("iron_to_diamond_mat_req", improvedUpgradingObject.get("iron_to_diamond_mat_req").getAsInt());
        improvedUpgrading.setOption("iron_to_gold_mat_req", improvedUpgradingObject.get("iron_to_gold_mat_req").getAsInt());
        improvedUpgrading.setOption("diamond_to_netherite_mat_req", improvedUpgradingObject.get("diamond_to_netherite_mat_req").getAsInt());
    }

    public enum CraftingMode {
        VANILLA, IMPROVED_VANILLA, CRAFTING_TABLE
    }

    public enum SmeltType {
        FURNACE, BLAST_FURNACE
    }

    public static class ConfigOption {
        String id;
        Map<String, Object> options;

        public ConfigOption(String id) {
            this.id = id;
            this.options = new HashMap<>();
        }

        public String getId() {
            return id;
        }

        public Map<String, Object> getOptions() {
            return options;
        }

        public int getIntOption(String optionId) throws Exception {
            if (!options.containsKey(optionId))
                throw new Exception("The option id '%s' does not exist in the config option '%s'.".formatted(optionId, id));
            if (options.get(optionId) instanceof Integer result) return result;
            return -1;
        }

        public String getStringOption(String optionId) throws Exception {
            if (!options.containsKey(optionId))
                throw new Exception("The option id '%s' does not exist in the config option '%s'.".formatted(optionId, id));
            if (options.get(optionId) instanceof String result) return result;
            return "";
        }

        public boolean getBooleanOption(String optionId) throws Exception {
            if (!options.containsKey(optionId))
                throw new Exception("The option id '%s' does not exist in the config option '%s'.".formatted(optionId, id));
            if (options.get(optionId) instanceof Boolean result) return result;
            return false;
        }

        public void setOption(String optionId, Object newValue) throws Exception {
            if (!options.containsKey(optionId))
                throw new Exception("The option id '%s' does not exist in the config option '%s'.".formatted(optionId, id));
            options.replace(optionId, newValue);
        }
    }
}
