package com.yourname.treasurewolf.utils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.bukkit.Location;
import org.bukkit.block.Biome;

public class BiomeUtils {
   private static final Set<Biome> VALID_BIOMES;

   public static boolean isValidBiome(Location location) {
      return location != null && location.getWorld() != null ? VALID_BIOMES.contains(location.getBlock().getBiome()) : false;
   }

   static {
      VALID_BIOMES = new HashSet(Arrays.asList(Biome.FOREST, Biome.TAIGA, Biome.DARK_FOREST, Biome.BIRCH_FOREST, Biome.OLD_GROWTH_BIRCH_FOREST, Biome.OLD_GROWTH_PINE_TAIGA, Biome.OLD_GROWTH_SPRUCE_TAIGA, Biome.FLOWER_FOREST, Biome.DESERT, Biome.BEACH, Biome.JUNGLE, Biome.BAMBOO_JUNGLE));
   }
}
