/*
 * Wormhole X-Treme Worlds Plugin for Bukkit
 * Copyright (C) 2011 Dean Bailey
 * 
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package de.luricos.bukkit.WormholeXTreme.Worlds.plugin;

import java.util.logging.Level;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import com.nijikokun.bukkit.Permissions.Permissions;
import de.luricos.bukkit.WormholeXTreme.Worlds.WormholeXTremeWorlds;
import de.luricos.bukkit.WormholeXTreme.Worlds.config.ConfigManager;

/**
 * The Class PermissionsSupport.
 * 
 * @author alron
 */
public class PermissionsSupport {

    /** The Constant thisPlugin. */
    private static final WormholeXTremeWorlds thisPlugin = WormholeXTremeWorlds.getThisPlugin();

    /** The Constant pluginManager. */
    private static final PluginManager pluginManager = WormholeXTremeWorlds.getThisPlugin().getServer().getPluginManager();

    /**
     * Disable permissions.
     */
    public static void disablePermissions() {
        if (ConfigManager.getServerOptionPermissions()) {
            if (PluginSupport.getPermissionHandler() != null) {
                PluginSupport.setPermissionHandler(null);
                thisPlugin.prettyLog(Level.INFO, false, "Detached from Permissions plugin.");
            }
        }
    }

    /**
     * Enable permissions.
     */
    public static void enablePermissions() {
        if (ConfigManager.getServerOptionPermissions()) {
            if (PluginSupport.getPermissionHandler() == null) {
                final Plugin test = pluginManager.getPlugin("Permissions");
                if (test != null) {
                    final String version = test.getDescription().getVersion();
                    if ( !version.startsWith("2.5") && !version.startsWith("2.6") && !version.startsWith("2.7") && !version.startsWith("3.0")) {
                        thisPlugin.prettyLog(Level.WARNING, false, "Not a supported version of Permissions. Recommended is 3.0.x");
                    }
                    try {
                        PluginSupport.setPermissionHandler(((Permissions) test).getHandler());
                        thisPlugin.prettyLog(Level.INFO, false, "Attached to Permissions version " + version);
                    }
                    catch (final ClassCastException e) {
                        thisPlugin.prettyLog(Level.WARNING, false, "Failed to get cast to Permissions: " + e.getMessage());
                    }
                }
                else {
                    thisPlugin.prettyLog(Level.INFO, false, "Permission Plugin not yet available.");
                }
            }
        }
        else {
            thisPlugin.prettyLog(Level.INFO, false, "Permission Plugin support disabled via config.xml");
        }
    }
}
