package roycurtis.ankkuri;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.event.vehicle.VehicleMoveEvent;

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
    public void onBoatCreate(PlayerInteractEvent event)
    {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK)
            return;

        Material blockType = event.getClickedBlock().getType();
        Material relType   = event.getClickedBlock().getRelative( event.getBlockFace() ).getType();

        // Only handle if held item is at least a boat
        if ( !Utility.isBoatItem( event.getItem() ) )
            return;

        // Only handle if boat is being placed on non-water surface; compensate for underwater
        if ( Utility.isSailableBlock(blockType) || Utility.isSailableBlock(relType) )
            return;

        // Bypass if player is placing on non-water and has the right permission
        if ( event.getPlayer().hasPermission(PERM_BYPASS_PLACE) )
            return;

        // Bypass if player is placing on ice and has the right permission
        if ( Utility.isIcyBlock(blockType) )
        if ( event.getPlayer().hasPermission(PERM_BYPASS_PLACEONICE) )
            return;

        event.getPlayer()
            .sendMessage("§c*** You don't have permission to place boats on the ground");

        event.setUseItemInHand(Event.Result.DENY);
        event.setCancelled(true);
    }

    @EventHandler
    public void onBoatEnter(VehicleEnterEvent event)
    {
        if (event.getVehicle().getType() != EntityType.BOAT)
            return;

        Entity   rider     = event.getEntered();
        Boat     boat      = (Boat) event.getVehicle();
        Material blockType = boat.getLocation()
            .add(0, -0.4, 0)
            .getBlock().getType();

        // Compensate for tall hitbox blocks (e.g. fences)
        if (blockType == Material.AIR)
            blockType = boat.getLocation()
                .add(0, -1.4, 0)
                .getBlock().getType();

        // Only handle if boat is on invalid surface
        if ( Utility.isSailableBlock(blockType) )
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
        if ( Utility.isIcyBlock(blockType) )
        if ( rider.hasPermission(PERM_BYPASS_RIDEONICE) )
            return;

        rider.sendMessage("§c*** You don't have permission to ride grounded boats");
        event.setCancelled(true);
    }

    @EventHandler
    public void onBoatMove(VehicleMoveEvent event)
    {
        if (event.getVehicle().getType() != EntityType.BOAT)
            return;

        // Do nothing if neither of these are enabled
        if (!CONFIG.autoEjectFromGroundedBoats && !CONFIG.autoDestroyGroundedBoats)
            return;

        Boat     boat      = (Boat) event.getVehicle();
        Location locTo     = event.getTo();
        // WARN: Mutates locTo; new object created
        Block    block     = locTo.add(0, -0.4, 0).getBlock();
        Material blockType = block.getType();

        // Compensate for tall hitbox blocks (e.g. fences)
        if (blockType == Material.AIR)
        {
            // WARN: Mutates locTo; new object created
            block     = locTo.add(0, -1, 0).getBlock();
            blockType = block.getType();
        }

        // Only handle if boat is on invalid surface
        if ( Utility.isSailableBlock(blockType) )
            return;

        if (CONFIG.autoDestroyGroundedBoats)
        {
            boat.getWorld().dropItemNaturally( boat.getLocation(), Utility.boatToItemStack(boat) );
            boat.remove();

            // Since destroying a boat auto-ejects passengers, we don't need to continue
            return;
        }

        if (!CONFIG.autoEjectFromGroundedBoats)
            return;

        // Handle auto-ejection for all passengers in boat
        for ( Entity entity : boat.getPassengers() )
        {
            // Eject mobs in grounded boats, if enabled
            if (entity.getType() != EntityType.PLAYER)
            {
                if (CONFIG.blockGroundedBoatCapture)
                    boat.removePassenger(entity);

                continue;
            }

            // Bypass if boat on non-water and player has the right permission
            if ( entity.hasPermission(PERM_BYPASS_RIDE) )
                continue;

            // Bypass if boat on ice and player has the right permission
            if (blockType == Material.ICE || blockType == Material.PACKED_ICE)
            if ( entity.hasPermission(PERM_BYPASS_RIDEONICE) )
                continue;

            entity.sendMessage("§c*** Ejected; you don't have permission to ride grounded boats");
            boat.removePassenger(entity);
        }
    }
}
