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
    private final static Map<TreeSpecies, Material> BOATS = ImmutableMap
        .<TreeSpecies, Material> builder()
        .put(TreeSpecies.GENERIC,  Material.BOAT)
        .put(TreeSpecies.REDWOOD,  Material.BOAT_SPRUCE)
        .put(TreeSpecies.BIRCH,    Material.BOAT_BIRCH)
        .put(TreeSpecies.JUNGLE,   Material.BOAT_JUNGLE)
        .put(TreeSpecies.ACACIA,   Material.BOAT_ACACIA)
        .put(TreeSpecies.DARK_OAK, Material.BOAT_DARK_OAK)
        .build();

    public static ItemStack boatToItemStack(Boat boat)
    {
        return new ItemStack( BOATS.get( boat.getWoodType() ), 1 );
    }
}
