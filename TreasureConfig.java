package com.yourname.treasurewolf.data;

import com.yourname.treasurewolf.TreasureWolfPlugin;
import org.bukkit.configuration.file.FileConfiguration;

public class TreasureConfig {
   private final TreasureWolfPlugin plugin;
   private final FileConfiguration config;
   private int updateRadius;
   private int deadRadius;
   private int fishyPlaceMinDistance;
   private boolean useGlobalCooldown;
   private int globalCooldownMin;
   private int globalCooldownMax;

   public TreasureConfig(TreasureWolfPlugin plugin) {
      this.plugin = plugin;
      this.config = plugin.getConfig();
      this.loadConfig();
   }

   private void loadConfig() {
      this.updateRadius = this.config.getInt("settings.update_radius", 95);
      this.deadRadius = this.config.getInt("settings.dead_radius", 25);
      this.fishyPlaceMinDistance = this.config.getInt("settings.fishy_place_min_distance", 45);
      this.useGlobalCooldown = this.config.getBoolean("cooldowns.use_global", false);
      this.globalCooldownMin = this.config.getInt("cooldowns.global.min", 5);
      this.globalCooldownMax = this.config.getInt("cooldowns.global.max", 15);
      this.config.addDefault("settings.update_radius", 95);
      this.config.addDefault("settings.dead_radius", 25);
      this.config.addDefault("settings.fishy_place_min_distance", 45);
      this.config.addDefault("cooldowns.use_global", false);
      this.config.addDefault("cooldowns.global.min", 5);
      this.config.addDefault("cooldowns.global.max", 15);
      this.config.addDefault("messages.treasure_found", "&aВаш питомец нашел клад!");
      this.config.addDefault("messages.treasure_not_found", "&cВаш питомец еще не может найти клад");
      this.config.addDefault("messages.wolf_cooldown", "&cВаш питомец устал и не может искать клады");
      this.config.addDefault("messages.wolf_not_healthy", "&cВаш волк должен быть здоров (полное HP)");
      this.config.addDefault("messages.treasure_chest_opened", "&6Выкопанный сундук");
      this.config.options().copyDefaults(true);
      this.plugin.saveConfig();
   }

   public int getUpdateRadius() {
      return this.updateRadius;
   }

   public int getDeadRadius() {
      return this.deadRadius;
   }

   public int getFishyPlaceMinDistance() {
      return this.fishyPlaceMinDistance;
   }

   public boolean isUseGlobalCooldown() {
      return this.useGlobalCooldown;
   }

   public int getGlobalCooldownMin() {
      return this.globalCooldownMin;
   }

   public int getGlobalCooldownMax() {
      return this.globalCooldownMax;
   }

   public int getCooldownMin(String biome) {
      return this.useGlobalCooldown ? this.globalCooldownMin : this.config.getInt("cooldowns." + biome + ".min", this.config.getInt("cooldowns.default.min", 5));
   }

   public int getCooldownMax(String biome) {
      return this.useGlobalCooldown ? this.globalCooldownMax : this.config.getInt("cooldowns." + biome + ".max", this.config.getInt("cooldowns.default.max", 20));
   }

   public String getMessage(String path) {
      return this.config.getString("messages." + path, "Message not found");
   }

   public void reload() {
      this.plugin.reloadConfig();
      this.loadConfig();
   }
}
