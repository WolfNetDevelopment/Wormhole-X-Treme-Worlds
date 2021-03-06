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

import de.luricos.bukkit.WormholeXTreme.Worlds.config.ConfigManager;

import ru.tehkode.permissions.PermissionManager;

import java.util.HashMap;
import java.util.Map;

/**
 * @author alron
 * 
 */
public class PluginSupport {
    public enum SupportedPlugin {
        PERMISSIONS("PermissionsEx");

        private final String name;
        private static final Map<String, SupportedPlugin> lookupMap = new HashMap<String, SupportedPlugin>();
        
        private SupportedPlugin(String name) {
            this.name = name;
        }
        
        public String getName() {
            return this.name;
        }
        
        public static SupportedPlugin getSupportedPlugin(String pluginName) {
            return lookupMap.get(pluginName);
        }
        
        static {
            for (SupportedPlugin supPl : values()) {
                lookupMap.put(supPl.getName(), supPl);
            }
        }
    }
    
    /** The permission handler. */
    private static PermissionManager permissionHandler = null;

    /**
     * Gets the permission handler.
     * 
     * @return the permission handler
     */
    public static PermissionManager getPermissionHandler() {
        return permissionHandler;
    }

    public static SupportedPlugin[] getSupportedPlugin() {
        return SupportedPlugin.values();
    }

    /**
     * Sets the permission handler.
     *
     * @param permissionHandler
     *            the new permission handler
     */
    public static void setPermissionHandler(final PermissionManager permissionHandler) {
        PluginSupport.permissionHandler = permissionHandler;
    }
    
    public static void disableSupportedPlugins() {
        for (SupportedPlugin plugin : SupportedPlugin.values()) {
            disableSupport(plugin);
        }
    }
    
    public static void disableSupport(SupportedPlugin plugin) {
        disableSupport(plugin.getName());
    }
    
    public static void disableSupport(String plugin) {
        if (plugin.equals(SupportedPlugin.PERMISSIONS.getName()) && ConfigManager.getServerOptionPermissions()) {
            PermissionsSupport.disablePermissions();
        }
    }

    public static void enableSupportedPlugins() {
        for (SupportedPlugin plugin : SupportedPlugin.values()) {
            enableSupport(plugin);
        }
    }    
    
    public static void enableSupport(SupportedPlugin plugin) {
        enableSupport(plugin.getName());
    }    
    
    public static void enableSupport(String plugin) {
        if (plugin.equals(SupportedPlugin.PERMISSIONS.getName()) && ConfigManager.getServerOptionPermissions()) {
            PermissionsSupport.enablePermissions();
        }
    }
}
