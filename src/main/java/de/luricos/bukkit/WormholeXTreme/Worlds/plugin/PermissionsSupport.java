/*
 * Wormhole X-Treme Worlds Plugin for Bukkit
 * Copyright (C) 2011 Lycano <https://github.com/lycano/Wormhole-X-Treme/>
 *
 * Wormhole X-Treme Worlds Plugin for Bukkit
 * Copyright (C) 2011 Dean Bailey <https://github.com/alron/Wormhole-X-Treme-Worlds>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.luricos.bukkit.WormholeXTreme.Worlds.plugin;

import de.luricos.bukkit.WormholeXTreme.Worlds.WormholeXTremeWorlds;
import de.luricos.bukkit.WormholeXTreme.Worlds.config.ConfigManager;
import de.luricos.bukkit.WormholeXTreme.Worlds.utils.WXLogger;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.util.logging.Level;

/**
 * The Class PermissionsSupport.
 * 
 * @author alron
 */
public class PermissionsSupport {

    /** The Constant pluginManager. */
    private static final PluginManager pluginManager = WormholeXTremeWorlds.getThisPlugin().getServer().getPluginManager();

    /**
     * Disable permissions.
     */
    public static void disablePermissions() {
        if (ConfigManager.getServerOptionPermissions()) {
            if (PluginSupport.getPermissionHandler() != null) {
                PluginSupport.setPermissionHandler(null);
                WXLogger.prettyLog(Level.INFO, false, "Detached from Permissions plugin.");
            }
        }
    }

    /**
     * Enable permissions.
     */
    public static void enablePermissions() {
        if (ConfigManager.getServerOptionPermissions()) {
            if (PluginSupport.getPermissionHandler() == null) {
                final Plugin test = pluginManager.getPlugin("PermissionsEx");
                if (test != null) {
                    Double version = Double.parseDouble(test.getDescription().getVersion());
                    if (version < 1.8) {
                        WXLogger.prettyLog(Level.WARNING, false, "Found a not supported version of PermissionsEx. Recommended is at least 1.18");
                    }
                    
                    try {
                        PluginSupport.setPermissionHandler(PermissionsEx.getPermissionManager());
                        WXLogger.prettyLog(Level.INFO, false, "Attached to PermissionsEx version " + version.toString());
                    } catch (final ClassCastException e) {
                        WXLogger.prettyLog(Level.WARNING, false, "Failed to attach to PermissionsEx: " + e.getMessage());
                    }
                } else {
                    WXLogger.prettyLog(Level.INFO, false, "Permissions Plugin not yet available.");
                }
            }
        } else {
            WXLogger.prettyLog(Level.INFO, false, "Permissions Plugin support disabled via config.xml");
        }
    }
}
