package roycurtis.ankkuri;

import org.bukkit.configuration.Configuration;

import static roycurtis.ankkuri.Ankkuri.PLUGIN;

/** Container and manager class for plugin's configuration values */
class Config
{
    /** If enabled, prevents grounded boats from capturing mobs */
    boolean blockGroundedBoatCapture   = true;
    /** If enabled, auto-ejects non-bypassing players and mobs from grounded boats */
    boolean autoEjectFromGroundedBoats = false;
    /** If enabled, auto-destroys boats that run aground */
    boolean autoDestroyGroundedBoats   = false;

    Config()
    {
        PLUGIN.saveDefaultConfig();
        PLUGIN.reloadConfig();

        Configuration config = PLUGIN.getConfig();

        blockGroundedBoatCapture = config.getBoolean(
            "blockGroundedBoatCapture", blockGroundedBoatCapture
        );

        autoEjectFromGroundedBoats = config.getBoolean(
            "autoEjectFromGroundedBoats", autoEjectFromGroundedBoats
        );

        autoDestroyGroundedBoats = config.getBoolean(
            "autoDestroyGroundedBoats", autoDestroyGroundedBoats
        );
    }
}