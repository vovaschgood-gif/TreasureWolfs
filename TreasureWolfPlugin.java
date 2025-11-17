package com.yourname.treasurewolf;

import com.yourname.treasurewolf.commands.TreasureCommand;
import com.yourname.treasurewolf.data.TreasureConfig;
import com.yourname.treasurewolf.listeners.BlockBreakListener;
import com.yourname.treasurewolf.listeners.TreasureDigListener;
import com.yourname.treasurewolf.listeners.TreasureOpenListener;
import com.yourname.treasurewolf.listeners.WolfInteractListener;
import com.yourname.treasurewolf.managers.LootManager;
import com.yourname.treasurewolf.managers.TreasureManager;
import com.yourname.treasurewolf.managers.WolfManager;
import org.bukkit.plugin.java.JavaPlugin;

public class TreasureWolfPlugin extends JavaPlugin {
   private static TreasureWolfPlugin instance;
   private TreasureManager treasureManager;
   private LootManager lootManager;
   private TreasureConfig treasureConfig;
   private WolfManager wolfManager;

   public void onEnable() {
      instance = this;
      this.saveDefaultConfig();
      this.treasureConfig = new TreasureConfig(this);
      this.lootManager = new LootManager(this.treasureConfig);
      this.treasureManager = new TreasureManager(this, this.lootManager);
      this.wolfManager = new WolfManager(this, this.treasureManager);
      this.registerListeners();
      this.getCommand("treasure").setExecutor(new TreasureCommand(this));
      this.getLogger().info("TreasureWolf плагин успешно загружен!");
   }

   public void onDisable() {
      if (this.treasureManager != null) {
         this.treasureManager.cleanup();
      }

      this.getLogger().info("TreasureWolf плагин выключен!");
   }

   private void registerListeners() {
      this.getServer().getPluginManager().registerEvents(new WolfInteractListener(this, this.wolfManager), this);
      this.getServer().getPluginManager().registerEvents(new TreasureDigListener(this, this.treasureManager), this);
      this.getServer().getPluginManager().registerEvents(new TreasureOpenListener(this, this.treasureManager), this);
      this.getServer().getPluginManager().registerEvents(new BlockBreakListener(this.treasureManager), this);
   }

   public static TreasureWolfPlugin getInstance() {
      return instance;
   }

   public TreasureManager getTreasureManager() {
      return this.treasureManager;
   }

   public LootManager getLootManager() {
      return this.lootManager;
   }

   public TreasureConfig getTreasureConfig() {
      return this.treasureConfig;
   }

   public WolfManager getWolfManager() {
      return this.wolfManager;
   }
}
