package roycurtis.ankkuri;

import org.bukkit.Material;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.inventory.ItemStack;

import static roycurtis.ankkuri.Ankkuri.CONFIG;

/** Listens to various boat events, to handle grounded boats */
public class Listener implements org.bukkit.event.Listener
{
    private static final String PERM_BYPASS_PLACE      = "ankkuri.bypass.place";
    private static final String PERM_BYPASS_PLACEONICE = "ankkuri.bypass.placeonice";
    private static final String PERM_BYPASS_RIDE       = "ankkuri.bypass.ride";
    private static final String PERM_BYPASS_RIDEONICE  = "ankkuri.bypass.rideonice";

    /** Has to use PlayerInteractEvent, as VehicleCreateEvent does not track creator */
    @EventHandler
    @SuppressWarnings("deprecation")
    public void onBoatCreate(PlayerInteractEvent event)
    {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK)
            return;

        ItemStack held = event.getItem();
        if (held == null)
            return;

        Material blockType = event.getClickedBlock().getType();
        Material relType   = event.getClickedBlock().getRelative( event.getBlockFace() ).getType();
        Material itemType  = held.getType();

        // Only handle if held item is at least a boat
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

        // Bypass if player is placing on non-water and has the right permission
        if ( event.getPlayer().hasPermission(PERM_BYPASS_PLACE) )
            return;

        // Bypass if player is placing on ice and has the right permission
        if (blockType == Material.ICE || blockType == Material.PACKED_ICE)
        if ( event.getPlayer().hasPermission(PERM_BYPASS_PLACEONICE) )
            return;

        event.getPlayer()
            .sendMessage("§c*** You don't have permission to place boats on the ground");

        event.setUseItemInHand(Event.Result.DENY);
        event.setCancelled(true);
    }

    @EventHandler
    @SuppressWarnings("deprecation")
    public void onBoatEnter(VehicleEnterEvent event)
    {
        if (event.getVehicle().getType() != EntityType.BOAT)
            return;

        Entity rider     = event.getEntered();
        Boat boat      = (Boat) event.getVehicle();
        Material blockType = boat.getLocation()
            .add(0, -0.4, 0)
            .getBlock().getType();

        // Compensate for tall hitbox blocks (e.g. fences)
        if (blockType == Material.AIR)
            blockType = boat.getLocation()
                .add(0, -1.4, 0)
                .getBlock().getType();

        // Only handle if boat is on non-water/air surface. Allows air to compensate for waterfalls.
        if (blockType == Material.WATER
            || blockType == Material.STATIONARY_WATER
            || blockType == Material.AIR)
            return;

        // Prevent capturing of mobs in grounded boats, if enabled
        if (event.getEntered().getType() != EntityType.PLAYER)
        {
            if (CONFIG.blockGroundedBoatCapture)
                event.setCancelled(true);

            return;
        }

        // Bypass if boat on non-water and player has the right permission
        if ( rider.hasPermission(PERM_BYPASS_RIDE) )
            return;

        // Bypass if boat on ice and player has the right permission
        if (blockType == Material.ICE || blockType == Material.PACKED_ICE)
        if ( rider.hasPermission(PERM_BYPASS_RIDEONICE) )
            return;

        rider.sendMessage("§c*** You don't have permission to ride grounded boats");
        event.setCancelled(true);
    }
}
