package com.yourname.treasurewolf.managers;

import com.yourname.treasurewolf.TreasureWolfPlugin;
import com.yourname.treasurewolf.managers.WolfManager.1;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

public class WolfManager {
   private final TreasureWolfPlugin plugin;
   private final TreasureManager treasureManager;

   public WolfManager(TreasureWolfPlugin plugin, TreasureManager treasureManager) {
      this.plugin = plugin;
      this.treasureManager = treasureManager;
   }

   public void startTreasureSearch(Player player, Wolf wolf) {
      if (wolf.hasMetadata("wolf_cooldown")) {
         long cooldownEnd = ((MetadataValue)wolf.getMetadata("wolf_cooldown").get(0)).asLong();
         if (System.currentTimeMillis() < cooldownEnd) {
            player.sendMessage(this.plugin.getTreasureConfig().getMessage("wolf_cooldown"));
            return;
         }
      }

      Location treasureLoc = this.treasureManager.createTreasure(player, wolf);
      if (treasureLoc == null) {
         player.sendMessage(this.plugin.getTreasureConfig().getMessage("treasure_not_found"));
      } else {
         this.setCooldown(player, wolf);
         this.startWolfMovementTask(player, wolf, treasureLoc);
      }
   }

   private void startWolfMovementTask(Player player, Wolf wolf, Location target) {
      ArmorStand bait = (ArmorStand)wolf.getWorld().spawnEntity(target, EntityType.ARMOR_STAND);
      bait.setVisible(false);
      bait.setGravity(false);
      bait.setSmall(true);
      bait.setMarker(true);
      wolf.setSitting(false);
      wolf.setTarget(bait);
      (new 1(this, wolf, player, bait, target)).runTaskTimer(this.plugin, 0L, 20L);
   }

   private void setCooldown(Player player, Wolf wolf) {
      int min = 5;
      long cooldownTime = System.currentTimeMillis() + (long)(min * 60) * 1000L;
      wolf.setMetadata("wolf_cooldown", new FixedMetadataValue(this.plugin, cooldownTime));
   }
}
