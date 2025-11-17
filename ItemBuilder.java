package com.yourname.treasurewolf.utils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemBuilder {
   public static ItemStack damagedItem(Material material, int damage) {
      ItemStack item = new ItemStack(material);
      ItemMeta meta = item.getItemMeta();
      if (meta instanceof Damageable) {
         ((Damageable)meta).setDamage(damage);
         item.setItemMeta(meta);
      }

      return item;
   }

   public static ItemStack enchantedBook(Enchantment enchantment, int level) {
      ItemStack book = new ItemStack(Material.ENCHANTED_BOOK);
      EnchantmentStorageMeta meta = (EnchantmentStorageMeta)book.getItemMeta();
      if (meta != null) {
         meta.addStoredEnchant(enchantment, level, true);
         book.setItemMeta(meta);
      }

      return book;
   }
}
