package com.yourname.treasurewolf.objects;

import java.util.List;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class TreasureData {
   private Block block;
   private List<ItemStack> loot;
   private Player owner;
   private Material originalMaterial;
   private int digStage;
   private boolean opened;

   public TreasureData(Block block, List<ItemStack> loot, Player owner, Material originalMaterial) {
      this.block = block;
      this.loot = loot;
      this.owner = owner;
      this.originalMaterial = originalMaterial;
      this.digStage = 0;
      this.opened = false;
   }

   public Block getBlock() {
      return this.block;
   }

   public List<ItemStack> getLoot() {
      return this.loot;
   }

   public Player getOwner() {
      return this.owner;
   }

   public Material getOriginalMaterial() {
      return this.originalMaterial;
   }

   public int getDigStage() {
      return this.digStage;
   }

   public void setDigStage(int stage) {
      this.digStage = stage;
   }

   public void incrementDigStage() {
      ++this.digStage;
   }

   public boolean isOpened() {
      return this.opened;
   }

   public void setOpened(boolean opened) {
      this.opened = opened;
   }
}
