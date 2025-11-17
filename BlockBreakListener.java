package com.yourname.treasurewolf.listeners;

import com.yourname.treasurewolf.managers.TreasureManager;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakListener implements Listener {
   private final TreasureManager treasureManager;

   public BlockBreakListener(TreasureManager treasureManager) {
      this.treasureManager = treasureManager;
   }

   @EventHandler
   public void onBlockBreak(BlockBreakEvent event) {
      Block block = event.getBlock();
      if (block.hasMetadata("treasure_block")) {
         event.setCancelled(true);
      } else {
         if (block.getType() == Material.CHEST && this.treasureManager.getTreasureData(block) != null) {
            this.treasureManager.removeTreasure(block);
         }

      }
   }
}
