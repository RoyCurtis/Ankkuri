package roycurtis.ankkuri;

import org.bukkit.configuration.Configuration;

import static roycurtis.ankkuri.Ankkuri.PLUGIN;

/** Container and manager class for plugin's configuration values */
class Config
{
    /** If enabled, prevents grounded boats from capturing mobs */
    boolean blockGroundedBoatCapture = true;

    Config()
    {
        PLUGIN.saveDefaultConfig();
        PLUGIN.reloadConfig();

        Configuration config = PLUGIN.getConfig();

        blockGroundedBoatCapture = config.getBoolean(
            "blockGroundedBoatCapture", blockGroundedBoatCapture
        );
    }
}