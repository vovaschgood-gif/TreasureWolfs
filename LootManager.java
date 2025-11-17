package com.yourname.treasurewolf.managers;

import com.yourname.treasurewolf.data.TreasureConfig;
import com.yourname.treasurewolf.utils.ItemBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class LootManager {
   private final TreasureConfig config;

   public LootManager(TreasureConfig config) {
      this.config = config;
   }

   public List<ItemStack> generateLoot(Biome biome) {
      List<ItemStack> loot = new ArrayList();

      for(int i = 0; i < 27; ++i) {
         if (ThreadLocalRandom.current().nextInt(100) < 50) {
            ItemStack item = this.getRandomItem();
            loot.add(item);
         } else {
            loot.add(new ItemStack(Material.AIR));
         }
      }

      return loot;
   }

   private ItemStack getRandomItem() {
      int roll = ThreadLocalRandom.current().nextInt(100);
      if (roll < 63) {
         return this.getCommonItem();
      } else if (roll < 83) {
         return this.getRareItem();
      } else if (roll < 93) {
         return this.getEpicItem();
      } else if (roll < 96) {
         return this.getBookItem();
      } else {
         return roll < 99 ? this.getMendingItem() : this.getLegendaryItem();
      }
   }

   private ItemStack getCommonItem() {
      List<ItemStack> items = Arrays.asList(new ItemStack(Material.STICK, this.random(1, 3)), new ItemStack(Material.STRING, this.random(1, 3)), new ItemStack(Material.COAL, this.random(1, 4)), new ItemStack(Material.FLINT, this.random(1, 3)), new ItemStack(Material.BONE, this.random(1, 2)), new ItemStack(Material.GOLD_NUGGET, this.random(1, 6)), new ItemStack(Material.IRON_NUGGET, this.random(1, 5)), new ItemStack(Material.BOWL), new ItemStack(Material.BUCKET), new ItemStack(Material.COBWEB), ItemBuilder.damagedItem(Material.BOW, this.random(70, 380)), new ItemStack(Material.LEATHER, this.random(1, 2)), new ItemStack(Material.PAPER, this.random(1, 4)), new ItemStack(Material.ROTTEN_FLESH, this.random(1, 2)));
      return ((ItemStack)items.get(ThreadLocalRandom.current().nextInt(items.size()))).clone();
   }

   private ItemStack getRareItem() {
      List<ItemStack> items = Arrays.asList(new ItemStack(Material.DIAMOND, this.random(1, 2)), new ItemStack(Material.EMERALD, this.random(1, 3)), new ItemStack(Material.RAW_GOLD, this.random(1, 3)), new ItemStack(Material.RAW_IRON, this.random(1, 3)), new ItemStack(Material.SADDLE), new ItemStack(Material.ENDER_PEARL), new ItemStack(Material.ARROW, this.random(1, 3)), ItemBuilder.damagedItem(Material.IRON_SHOVEL, this.random(105, 241)), ItemBuilder.damagedItem(Material.IRON_PICKAXE, this.random(105, 241)));
      return ((ItemStack)items.get(ThreadLocalRandom.current().nextInt(items.size()))).clone();
   }

   private ItemStack getEpicItem() {
      List<ItemStack> items = Arrays.asList(new ItemStack(Material.EXPERIENCE_BOTTLE, this.random(1, 2)), new ItemStack(Material.GOLDEN_APPLE), new ItemStack(Material.NAME_TAG), new ItemStack(Material.SPYGLASS), ItemBuilder.damagedItem(Material.DIAMOND_BOOTS, this.random(105, 420)), ItemBuilder.damagedItem(Material.DIAMOND_HELMET, this.random(105, 360)));
      return ((ItemStack)items.get(ThreadLocalRandom.current().nextInt(items.size()))).clone();
   }

   private ItemStack getLegendaryItem() {
      List<ItemStack> items = Arrays.asList(new ItemStack(Material.NETHERITE_SCRAP), new ItemStack(Material.NETHERITE_INGOT), new ItemStack(Material.TOTEM_OF_UNDYING), new ItemStack(Material.SHULKER_SHELL));
      return ((ItemStack)items.get(ThreadLocalRandom.current().nextInt(items.size()))).clone();
   }

   private ItemStack getBookItem() {
      List<ItemStack> items = Arrays.asList(ItemBuilder.enchantedBook(Enchantment.PROTECTION_ENVIRONMENTAL, this.random(1, 4)), ItemBuilder.enchantedBook(Enchantment.PROTECTION_FALL, this.random(1, 4)), ItemBuilder.enchantedBook(Enchantment.DAMAGE_ALL, this.random(1, 5)), ItemBuilder.enchantedBook(Enchantment.ARROW_DAMAGE, this.random(1, 5)), ItemBuilder.enchantedBook(Enchantment.LOOT_BONUS_MOBS, this.random(1, 3)));
      return ((ItemStack)items.get(ThreadLocalRandom.current().nextInt(items.size()))).clone();
   }

   private ItemStack getMendingItem() {
      List<ItemStack> items = Arrays.asList(ItemBuilder.enchantedBook(Enchantment.MENDING, 1), ItemBuilder.enchantedBook(Enchantment.DURABILITY, this.random(1, 3)), ItemBuilder.enchantedBook(Enchantment.DIG_SPEED, this.random(1, 5)), ItemBuilder.enchantedBook(Enchantment.LOOT_BONUS_BLOCKS, this.random(1, 3)));
      return ((ItemStack)items.get(ThreadLocalRandom.current().nextInt(items.size()))).clone();
   }

   private int random(int min, int max) {
      return ThreadLocalRandom.current().nextInt(min, max + 1);
   }
}
