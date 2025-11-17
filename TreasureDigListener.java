package com.yourname.treasurewolf.listeners;

import com.yourname.treasurewolf.TreasureWolfPlugin;
import com.yourname.treasurewolf.managers.TreasureManager;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class TreasureDigListener implements Listener {
   private final TreasureWolfPlugin plugin;
   private final TreasureManager treasureManager;

   public TreasureDigListener(TreasureWolfPlugin plugin, TreasureManager treasureManager) {
      this.plugin = plugin;
      this.treasureManager = treasureManager;
   }

   @EventHandler
   public void onDig(PlayerInteractEvent event) {
      if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
         Block block = event.getClickedBlock();
         if (block != null) {
            if (this.treasureManager.getTreasureData(block) != null) {
               Player player = event.getPlayer();
               ItemStack item = player.getInventory().getItemInMainHand();
               if (item.getType().toString().contains("SHOVEL")) {
                  event.setCancelled(true);
                  this.treasureManager.digTreasure(block, player);
               } else {
                  player.sendMessage("§cНужна лопата, чтобы выкопать клад!");
                  event.setCancelled(true);
               }
            }

         }
      }
   }
}
