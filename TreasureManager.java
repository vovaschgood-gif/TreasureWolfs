package com.yourname.treasurewolf.managers;

import com.yourname.treasurewolf.TreasureWolfPlugin;
import com.yourname.treasurewolf.objects.TreasureData;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

public class TreasureManager {
   private final TreasureWolfPlugin plugin;
   private final LootManager lootManager;
   private final Map<Block, TreasureData> activeTreasures = new HashMap();

   public TreasureManager(TreasureWolfPlugin plugin, LootManager lootManager) {
      this.plugin = plugin;
      this.lootManager = lootManager;
   }

   public Location createTreasure(Player player, Wolf wolf) {
      Location center = player.getLocation();
      int radius = 50;
      int x = center.getBlockX() + ThreadLocalRandom.current().nextInt(-radius, radius);
      int z = center.getBlockZ() + ThreadLocalRandom.current().nextInt(-radius, radius);
      int y = center.getWorld().getHighestBlockYAt(x, z);
      Location loc = new Location(center.getWorld(), (double)x, (double)y, (double)z);
      Block block = loc.getBlock().getRelative(0, -1, 0);
      if (block.getType() != Material.SAND && block.getType() != Material.GRASS_BLOCK && block.getType() != Material.DIRT && block.getType() != Material.SNOW_BLOCK && block.getType() != Material.PODZOL) {
         return null;
      } else {
         block.setMetadata("treasure_block", new FixedMetadataValue(this.plugin, true));
         TreasureData data = new TreasureData(block, this.lootManager.generateLoot(block.getBiome()), player, block.getType());
         this.activeTreasures.put(block, data);
         return loc;
      }
   }

   public void revealTreasure(Block block) {
      TreasureData data = (TreasureData)this.activeTreasures.get(block);
      if (data != null) {
         block.setType(Material.COARSE_DIRT);
         block.getWorld().playSound(block.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 2.0F);
         block.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, block.getLocation().add(0.5D, 1.0D, 0.5D), 15, 0.5D, 0.5D, 0.5D);
      }

   }

   public TreasureData getTreasureData(Block block) {
      return (TreasureData)this.activeTreasures.get(block);
   }

   public void digTreasure(Block block, Player player) {
      TreasureData data = (TreasureData)this.activeTreasures.get(block);
      if (data != null) {
         data.incrementDigStage();
         block.getWorld().spawnParticle(Particle.BLOCK_CRACK, block.getLocation().add(0.5D, 1.0D, 0.5D), 20, 0.2D, 0.2D, 0.2D, block.getBlockData());
         block.getWorld().playSound(block.getLocation(), Sound.BLOCK_GRAVEL_BREAK, 1.0F, 1.0F);
         player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§6копаем... " + data.getDigStage() + "/9"));
         if (data.getDigStage() >= 9) {
            this.spawnPhysicalChest(block, data);
         }

      }
   }

   private void spawnPhysicalChest(Block block, TreasureData data) {
      block.setType(Material.CHEST);
      if (block.getState() instanceof Chest) {
         Chest chest = (Chest)block.getState();
         Iterator var4 = data.getLoot().iterator();

         while(var4.hasNext()) {
            ItemStack item = (ItemStack)var4.next();
            if (item != null) {
               chest.getInventory().addItem(new ItemStack[]{item});
            }
         }
      }

      block.getWorld().playSound(block.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 1.0F, 1.0F);
      block.getWorld().spawnParticle(Particle.TOTEM, block.getLocation().add(0.5D, 1.0D, 0.5D), 30, 0.5D, 0.5D, 0.5D);
   }

   public void finishTreasure(Block block) {
      TreasureData data = (TreasureData)this.activeTreasures.get(block);
      if (data != null) {
         block.setType(data.getOriginalMaterial());
         block.getWorld().spawnParticle(Particle.CLOUD, block.getLocation().add(0.5D, 0.5D, 0.5D), 10, 0.1D, 0.1D, 0.1D);
         block.getWorld().playSound(block.getLocation(), Sound.BLOCK_SAND_PLACE, 1.0F, 1.0F);
         this.removeTreasure(block);
      }

   }

   public void removeTreasure(Block block) {
      this.activeTreasures.remove(block);
      block.removeMetadata("treasure_block", this.plugin);
   }

   public void cleanup() {
      this.activeTreasures.clear();
   }
}
