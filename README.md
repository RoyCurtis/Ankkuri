**Ankkuri** is a Bukkit plugin for 1.11.2 and above. Its sole purpose is to restrict the placing and
mounting of boats on non-water surfaces. Optionally, it can restrict it to just ice surfaces, to
allow for ice boat tracks and nothing else.

The name *ankkuri* is a Finnish term, meaning *anchor*. [This plugin was written for PsychoLynx for
the Pelitcraft community.][1]

[Click here to watch Ankkuri in action.][3]

# Links

* [Downloads](https://github.com/RoyCurtis/Ankkuri/releases)
* [BukkitDev](http://dev.bukkit.org/bukkit-plugins/ankkuri/)

# Behavior

By default, Ankkuri will block the following:

* **Placement of boats on non-water surfaces** - Makes it impossible for players to place boats on
surfaces such as roads, and other inappropriate places.
* **Entering of grounded boats** - If a boat is grounded, players will be unable to enter it.
* **Capturing of mobs by grounded boats** - This prevents boats being used as wilderness traps.

If configured, Ankkuri can also:

* **Auto-eject from grounded boats** - If players (and, optionally, mobs) are in a boat that becomes
grounded, they can be auto-ejected if they don't have the right bypass permissions.
* **Auto-destroy grounded boats** - Any boat, regardless of passengers inside, can be popped off
into an item drop when grounded.

# Configuration

On first start, Ankkuri will generate its default config file in its plugin folder. You can change
this config and then do `/ankkuri` to reload the changes. No server restart needed.

[The configuration file itself is well commented; please read it for help what the options do.][4]

# Permissions

You can grant any player or group the following permissions to bypass the blocks:

* `ankkuri.bypass.place` - Allows player to place boats anywhere
* `ankkuri.bypass.placeonice` - Allows player to place boats on ice and water only
* `ankkuri.bypass.ride` - Allows player to enter boats that are on any block
* `ankkuri.bypass.rideonice` - Allows player to enter boats that are on ice, water and air only
* `ankkuri.bypass.*` - Bypasses all of the above
* `ankkuri.reload` - Allows player to reload Ankkuri's config
* `ankkuri.*` - Grants all of the above

All these permissions are automatically granted to opped players.

# Building, debugging and debug logging

For instructions and screenshots on how to. . .

* Compile this plugin from scratch
* Build a JAR of this plugin
* Debug this plugin on a server
* Enable debug logging levels such as `FINE` and `FINER`

. . .[please follow the linked guide on this Google document.][2]

[1]: https://redd.it/63nsl4
[2]: https://docs.google.com/document/d/1TTDXG7IZ9M0D2-rzbILAWg1CKjynHK8fNGxbf3W4wBk/view
[3]: http://vanderprot.gamealition.com/img/f931b.mp4
[4]: https://github.com/RoyCurtis/Ankkuri/blob/master/src/main/resources/config.yml