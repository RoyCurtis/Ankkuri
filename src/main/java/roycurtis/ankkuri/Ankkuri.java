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
    private static final String PERM_BYPASS_PLACE = "ankkuri.bypass.place";
    private static final String PERM_BYPASS_RIDE  = "ankkuri.bypass.ride";

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
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK)
            return;

        ItemStack held = event.getItem();
        if (held == null)
            return;

        Material type = held.getType();
        if (type != Material.BOAT
            && type != Material.BOAT_ACACIA
            && type != Material.BOAT_BIRCH
            && type != Material.BOAT_DARK_OAK
            && type != Material.BOAT_JUNGLE
            && type != Material.BOAT_SPRUCE)
            return;

        if ( event.getPlayer().hasPermission(PERM_BYPASS_PLACE) )
            return;

        event.getPlayer().sendMessage("§c*** You don't have permission to place boats on the ground");
        event.setUseItemInHand(Event.Result.DENY);
        event.setCancelled(true);
    }
}
