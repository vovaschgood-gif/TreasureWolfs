package com.yourname.treasurewolf.listeners;

import com.yourname.treasurewolf.TreasureWolfPlugin;
import com.yourname.treasurewolf.managers.WolfManager;
import com.yourname.treasurewolf.utils.BiomeUtils;
import com.yourname.treasurewolf.utils.MessageUtils;
import java.util.Arrays;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;

public class WolfInteractListener implements Listener {
   private final TreasureWolfPlugin plugin;
   private final WolfManager wolfManager;
   private static final List<Material> VALID_MEAT;

   public WolfInteractListener(TreasureWolfPlugin plugin, WolfManager wolfManager) {
      this.plugin = plugin;
      this.wolfManager = wolfManager;
   }

   @EventHandler
   public void onWolfInteract(PlayerInteractEntityEvent event) {
      if (event.getRightClicked() instanceof Wolf) {
         Wolf wolf = (Wolf)event.getRightClicked();
         Player player = event.getPlayer();
         if (wolf.hasMetadata("treasure_finder")) {
            event.setCancelled(true);
         } else if (wolf.isTamed() && wolf.getOwner().equals(player)) {
            if (!player.isSneaking()) {
               ItemStack itemInHand = player.getInventory().getItemInMainHand();
               if (VALID_MEAT.contains(itemInHand.getType())) {
                  if (BiomeUtils.isValidBiome(player.getLocation())) {
                     if (!(wolf.getHealth() < 20.0D)) {
                        if (player.hasMetadata("wolf_cooldown")) {
                           long cooldownEnd = ((MetadataValue)player.getMetadata("wolf_cooldown").get(0)).asLong();
                           if (System.currentTimeMillis() < cooldownEnd) {
                              event.setCancelled(true);
                              wolf.getWorld().playSound(wolf.getLocation(), Sound.ENTITY_WOLF_WHINE, 1.0F, 0.8F);
                              MessageUtils.sendActionBar(player, "&cВаш питомец еще не может найти клад");
                              return;
                           }

                           player.removeMetadata("wolf_cooldown", this.plugin);
                        }

                        event.setCancelled(true);
                        itemInHand.setAmount(itemInHand.getAmount() - 1);
                        player.swingMainHand();
                        wolf.getWorld().playSound(wolf.getLocation(), Sound.ENTITY_WOLF_AMBIENT, 1.0F, 1.0F);
                        wolf.getWorld().playSound(wolf.getLocation(), Sound.ENTITY_FOX_EAT, 1.0F, 0.6F);
                        wolf.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, wolf.getLocation().add(0.0D, 1.0D, 0.0D), 5, 1.0D, 0.05D, 1.0D);
                        this.wolfManager.startTreasureSearch(player, wolf);
                     }
                  }
               }
            }
         }
      }
   }

   static {
      VALID_MEAT = Arrays.asList(Material.PORKCHOP, Material.COOKED_PORKCHOP, Material.BEEF, Material.COOKED_BEEF, Material.CHICKEN, Material.COOKED_CHICKEN, Material.RABBIT, Material.COOKED_RABBIT, Material.MUTTON, Material.COOKED_MUTTON);
   }
}
