package com.yourname.treasurewolf.listeners;

import com.yourname.treasurewolf.TreasureWolfPlugin;
import com.yourname.treasurewolf.managers.TreasureManager;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

public class TreasureOpenListener implements Listener {
   private final TreasureWolfPlugin plugin;
   private final TreasureManager treasureManager;

   public TreasureOpenListener(TreasureWolfPlugin plugin, TreasureManager treasureManager) {
      this.plugin = plugin;
      this.treasureManager = treasureManager;
   }

   @EventHandler
   public void onInventoryClose(InventoryCloseEvent event) {
      Inventory inv = event.getInventory();
      if (inv.getHolder() instanceof Chest) {
         Chest chest = (Chest)inv.getHolder();
         Block block = chest.getBlock();
         if (this.treasureManager.getTreasureData(block) != null && inv.isEmpty()) {
            this.treasureManager.finishTreasure(block);
            event.getPlayer().sendMessage("§aвы забрали все сокровища! земля восстановилась.");
         }
      }

   }
}
