package roycurtis.ankkuri;

import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

/** Core class of the Ankkuri plugin. Handles listener creation */
public class Ankkuri extends JavaPlugin implements Listener
{
    private static final String PERM_BYPASS_PLACE      = "ankkuri.bypass.place";
    private static final String PERM_BYPASS_PLACEONICE = "ankkuri.bypass.placeonice";
    private static final String PERM_BYPASS_RIDE       = "ankkuri.bypass.ride";

    private static Logger LOGGER;

    @Override
    public void onLoad()
    {
        LOGGER = getLogger();
    }

    @Override
    public void onEnable()
    {
        getServer().getPluginManager().registerEvents(this, this);
    }

    /** Has to use PlayerInteractEvent, as VehicleCreateEvent does not track creator */
    @EventHandler
    @SuppressWarnings("deprecation")
    public void onBoatCreate(PlayerInteractEvent event)
    {
        // Handle only placement of boat items
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK)
            return;

        ItemStack held = event.getItem();
        if (held == null)
            return;

        Material blockType = event.getClickedBlock().getType();
        Material relType   = event.getClickedBlock().getRelative( event.getBlockFace() ).getType();
        Material itemType  = held.getType();
        if (itemType != Material.BOAT
            && itemType != Material.BOAT_ACACIA
            && itemType != Material.BOAT_BIRCH
            && itemType != Material.BOAT_DARK_OAK
            && itemType != Material.BOAT_JUNGLE
            && itemType != Material.BOAT_SPRUCE)
            return;

        // Only handle if boat is being placed on non-water surface; compensate for underwater
        if (blockType == Material.WATER || blockType == Material.STATIONARY_WATER)
            return;
        else if (relType == Material.WATER || relType == Material.STATIONARY_WATER)
            return;

        // Bypass if player is placing on ice and has the right permission
        if (blockType == Material.ICE || blockType == Material.PACKED_ICE)
        if ( event.getPlayer().hasPermission(PERM_BYPASS_PLACEONICE) )
            return;

        // Bypass if player is placing on non-water and has the right permission
        if ( event.getPlayer().hasPermission(PERM_BYPASS_PLACE) )
            return;

        event.getPlayer().sendMessage("§c*** You don't have permission to place boats on the ground");
        event.setUseItemInHand(Event.Result.DENY);
        event.setCancelled(true);
    }
}
