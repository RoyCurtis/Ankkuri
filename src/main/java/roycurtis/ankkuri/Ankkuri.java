package roycurtis.ankkuri;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

/** Core class of the Ankkuri plugin */
public class Ankkuri extends JavaPlugin
{
    static Ankkuri  PLUGIN;
    static Config   CONFIG;
    static Listener LISTENER;

    @Override
    public void onEnable()
    {
        PLUGIN   = this;
        CONFIG   = new Config();
        LISTENER = new Listener();
        getServer().getPluginManager().registerEvents(LISTENER, this);
    }

    @Override
    public void onDisable()
    {
        CONFIG = null;
    }

    @Override
    public boolean onCommand(CommandSender who, Command what, String label, String[] args)
    {
        CONFIG = new Config();
        who.sendMessage("*** Ankkuri's config reloaded");

        if ( !(who instanceof ConsoleCommandSender) )
            getServer().getConsoleSender().sendMessage("[Ankkuri] Config reloaded");

        return true;
    }
}
