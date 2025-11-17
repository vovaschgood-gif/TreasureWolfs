package com.yourname.treasurewolf.commands;

import com.yourname.treasurewolf.TreasureWolfPlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TreasureCommand implements CommandExecutor {
   private final TreasureWolfPlugin plugin;

   public TreasureCommand(TreasureWolfPlugin plugin) {
      this.plugin = plugin;
   }

   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
      if (args.length == 0) {
         this.sendHelp(sender);
         return true;
      } else {
         String var5 = args[0].toLowerCase();
         byte var6 = -1;
         switch(var5.hashCode()) {
         case -934641255:
            if (var5.equals("reload")) {
               var6 = 0;
            }
            break;
         case 3198785:
            if (var5.equals("help")) {
               var6 = 2;
            }
            break;
         case 3237038:
            if (var5.equals("info")) {
               var6 = 1;
            }
         }

         switch(var6) {
         case 0:
            if (!sender.hasPermission("treasurewolf.reload")) {
               sender.sendMessage(String.valueOf(ChatColor.RED) + "У вас нет прав!");
               return true;
            }

            this.plugin.getTreasureConfig().reload();
            sender.sendMessage(String.valueOf(ChatColor.GREEN) + "Конфигурация перезагружена!");
            return true;
         case 1:
            this.sendInfo(sender);
            return true;
         case 2:
         default:
            this.sendHelp(sender);
            return true;
         }
      }
   }

   private void sendHelp(CommandSender sender) {
      sender.sendMessage(String.valueOf(ChatColor.GOLD) + "=== TreasureWolf ===");
      String var10001 = String.valueOf(ChatColor.YELLOW);
      sender.sendMessage(var10001 + "/treasure reload" + String.valueOf(ChatColor.WHITE) + " - Перезагрузить конфиг");
      var10001 = String.valueOf(ChatColor.YELLOW);
      sender.sendMessage(var10001 + "/treasure info" + String.valueOf(ChatColor.WHITE) + " - Информация");
      var10001 = String.valueOf(ChatColor.YELLOW);
      sender.sendMessage(var10001 + "/treasure help" + String.valueOf(ChatColor.WHITE) + " - Помощь");
      sender.sendMessage("");
      sender.sendMessage(String.valueOf(ChatColor.AQUA) + "Как использовать:");
      sender.sendMessage(String.valueOf(ChatColor.WHITE) + "1. Приручите волка");
      sender.sendMessage(String.valueOf(ChatColor.WHITE) + "2. Покормите мясом в лесу/пустыне");
      sender.sendMessage(String.valueOf(ChatColor.WHITE) + "3. Следуйте за волком");
      sender.sendMessage(String.valueOf(ChatColor.WHITE) + "4. Копайте лопатой 9 раз");
      sender.sendMessage(String.valueOf(ChatColor.WHITE) + "5. Откройте сундук!");
   }

   private void sendInfo(CommandSender sender) {
      sender.sendMessage(String.valueOf(ChatColor.GOLD) + "=== TreasureWolf ===");
      String var10001 = String.valueOf(ChatColor.YELLOW);
      sender.sendMessage(var10001 + "Версия: " + String.valueOf(ChatColor.WHITE) + this.plugin.getDescription().getVersion());
      var10001 = String.valueOf(ChatColor.YELLOW);
      sender.sendMessage(var10001 + "Автор: " + String.valueOf(ChatColor.WHITE) + "YourName");
      if (sender instanceof Player) {
         Player player = (Player)sender;
         var10001 = String.valueOf(ChatColor.YELLOW);
         sender.sendMessage(var10001 + "Биом: " + String.valueOf(ChatColor.WHITE) + player.getLocation().getBlock().getBiome().name());
      }

   }
}
