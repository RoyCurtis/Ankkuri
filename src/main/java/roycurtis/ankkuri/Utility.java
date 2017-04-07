package roycurtis.ankkuri;

import com.google.common.collect.ImmutableMap;
import org.bukkit.Material;
import org.bukkit.TreeSpecies;
import org.bukkit.entity.Boat;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

/** Static class for utility methods */
public class Utility
{
    /** Maps wood types to their boat item equivalents */
    private final static Map<TreeSpecies, Material> BOATS = ImmutableMap
        .<TreeSpecies, Material> builder()
        .put(TreeSpecies.GENERIC,  Material.BOAT)
        .put(TreeSpecies.REDWOOD,  Material.BOAT_SPRUCE)
        .put(TreeSpecies.BIRCH,    Material.BOAT_BIRCH)
        .put(TreeSpecies.JUNGLE,   Material.BOAT_JUNGLE)
        .put(TreeSpecies.ACACIA,   Material.BOAT_ACACIA)
        .put(TreeSpecies.DARK_OAK, Material.BOAT_DARK_OAK)
        .build();

    /** Gets a single boat item matching the given boat */
    public static ItemStack boatToItemStack(Boat boat)
    {
        return new ItemStack( BOATS.get( boat.getWoodType() ), 1 );
    }

    /** Returns true if item is a boat, false if not or if null */
    public static boolean isBoatItem(ItemStack item)
    {
        return item != null && BOATS.containsValue( item.getType() );
    }

    /** Checks if given block type is valid for boats; allows air to compensate for waterfalls */
    public static boolean isSailableBlock(Material material)
    {
        return material == Material.WATER
            || material == Material.STATIONARY_WATER
            || material == Material.AIR;
    }

    /** Checks if given block type is icy */
    public static boolean isIcyBlock(Material material)
    {
        return material == Material.ICE
            || material == Material.PACKED_ICE
            || material == Material.FROSTED_ICE;
    }
}
