package com.yourname.treasurewolf.utils;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class MessageUtils {
   public static void sendActionBar(Player player, String message) {
      player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.translateAlternateColorCodes('&', message)));
   }

   public static void sendMessage(Player player, String message) {
      player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
   }

   public static String colorize(String message) {
      return ChatColor.translateAlternateColorCodes('&', message);
   }
}
