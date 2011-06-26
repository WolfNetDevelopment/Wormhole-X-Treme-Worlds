Wormhole X-Treme Worlds v0.5: A multiple worlds management plugin for Bukkit.

To Install:
1. Extract WormholeXTremeWorlds.jar into your plugins/ folder.
2. (Optionally) Extract WormholeXTremeWorldsSpawn.jar into your plugins/ folder, if you want WXW to handle
   '/spawn' and on death respawn.
3. Start server.
4. Add all existing worlds to WXW via the '/wxw create' command.

To Upgrade: 
1. Extract updated WormholwXtremeWorlds.jar (and optionally WormholeXTremeWorldsSpawn.jar) into your plugins/ folder.
2. Start Server, all new config options will be added and run with their default value.
   If you wish to change any of them, just stop the server and edit the config file.


Command List with Permissions Nodes:
    '/wxw go [world]' - Go to spawn of specified world. - wxw.admin.go
    '/wxw list' - List all loaded and configured worlds. - wxw.admin.list
    '/wxw remove [world]' - Remove world from configuration. - wxw.admin.remove
    '/wxw load [world]' - Load unloaded world. - wxw.admin.load
    '/wxw modify [args]' - Modify settings of specified world. - wxw.admin.modify
    '/wxw info [world]' - Get info about specified world. - wxw.admin.info
    '/wxw setspawn' - Set spawn of current world to current location. - wxw.admin.setspawn
    '/wxw create [args]' - Create new world with specified args. - wxw.admin.create
    '/wxw spawn' - Go to spawn of current world. - wxw.spawn
    '/spawn' - Go to spawn of current world. - wxw.spawn (Only available via WormholeXTremeWorldsSpawn.jar)

Config.xml options with defaults:
    serverOptionPermissions - Enable or disable Permissions plugin support. Default: true (enabled)
    serverOptionOpsBypassPermissions - Ops bypass Permissions plugin access checks. Default: true (disabled)
    serverOptionHelp - Enable or disable Help plugin support. Default: true (enabled)
    serverOptionTimelock - Option: Enable or disable timelock functionality. Default: true (enabled)
    
World configuration files are stored in xml, and are able to be edited by player, though it is not
recommended. 