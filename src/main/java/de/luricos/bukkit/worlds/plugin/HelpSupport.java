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
package de.luricos.bukkit.worlds.plugin;

import java.util.logging.Level;

import me.taylorkelly.help.Help;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import de.luricos.bukkit.worlds.WormholeXTremeWorlds;
import de.luricos.bukkit.worlds.config.ConfigManager;
import de.luricos.bukkit.worlds.permissions.PermissionType;

/**
 * The Class PluginUtilities.
 * 
 * @author alron
 */
public class HelpSupport {

    /** The Constant thisPlugin. */
    private static final WormholeXTremeWorlds thisPlugin = WormholeXTremeWorlds.getThisPlugin();

    /** The Constant pluginManager. */
    private static final PluginManager pluginManager = WormholeXTremeWorlds.getThisPlugin().getServer().getPluginManager();

    /**
     * Disable help.
     */
    public static void disableHelp() {
        if (ConfigManager.getServerOptionHelp()) {
            if (PluginSupport.getHelp() != null) {
                PluginSupport.setHelp(null);
                thisPlugin.prettyLog(Level.INFO, false, "Detached from Help plugin.");
            }
        }
    }

    /**
     * Enable help.
     */
    public static void enableHelp() {
        if (ConfigManager.getServerOptionHelp()) {
            if (PluginSupport.getHelp() == null) {
                final Plugin helpTest = pluginManager.getPlugin("Help");
                if (helpTest != null) {
                    final String version = helpTest.getDescription().getVersion();
                    if ( !version.startsWith("0.2")) {
                        thisPlugin.prettyLog(Level.WARNING, false, "Not a support version of Help: " + version + " Recommended is: 0.2.x");
                    }
                    try {
                        PluginSupport.setHelp((Help) helpTest);
                        thisPlugin.prettyLog(Level.INFO, false, "Attached to Help version: " + version);
                    }
                    catch (final ClassCastException e) {
                        thisPlugin.prettyLog(Level.WARNING, false, "Failed to get cast to Help: " + e.getMessage());
                    }
                }
                else {
                    thisPlugin.prettyLog(Level.INFO, false, "Help plugin is not yet available; there will be no Help integration until it is loaded.");
                }
            }
        }
        else {
            thisPlugin.prettyLog(Level.INFO, false, "Help Plugin support disabled via config.xml");
        }
    }

    /**
     * Register help commands.
     */
    public static void registerHelpCommands() {
        if ((PluginSupport.getHelp() != null) && ConfigManager.getServerOptionHelp()) {
            PluginSupport.getHelp().registerCommand("wxw", "Wormhole X-Treme Worlds administration, configuration and utilities command", thisPlugin, true);
            if ((PluginSupport.getPermissionHandler() != null) && ConfigManager.getServerOptionPermissions()) {

                PluginSupport.getHelp().registerCommand("wxw go [world]", "Go to spawn of specified world.", thisPlugin, PermissionType.GO.getPermission());
                PluginSupport.getHelp().registerCommand("wxw list", "List all loaded and configured worlds.", thisPlugin, PermissionType.LIST.getPermission());
                PluginSupport.getHelp().registerCommand("wxw remove [world]", "Remove world from configuration.", thisPlugin, PermissionType.REMOVE.getPermission());
                PluginSupport.getHelp().registerCommand("wxw connect [world]", "Load unloaded world.", thisPlugin, PermissionType.LOAD.getPermission());
                PluginSupport.getHelp().registerCommand("wxw modify [args]", "Modify settings of specified world.", thisPlugin, PermissionType.MODIFY.getPermission());
                PluginSupport.getHelp().registerCommand("wxw info [world]", "Get info about specified world.", thisPlugin, PermissionType.INFO.getPermission());
                PluginSupport.getHelp().registerCommand("wxw setspawn", "Set spawn of current world to current location.", thisPlugin, PermissionType.SET_SPAWN.getPermission());
                PluginSupport.getHelp().registerCommand("wxw create [args]", "Create new world with specified args.", thisPlugin, PermissionType.CREATE.getPermission());
                PluginSupport.getHelp().registerCommand("wxw spawn", "Go to spawn of current world.", thisPlugin, PermissionType.SPAWN.getPermission());
            }
            else {
                PluginSupport.getHelp().registerCommand("wxw go [world]", "Go to spawn of specified world.", thisPlugin, "OP");
                PluginSupport.getHelp().registerCommand("wxw list", "List all loaded and configured worlds.", thisPlugin, "OP");
                PluginSupport.getHelp().registerCommand("wxw remove [world]", "Remove world from configuration.", thisPlugin, "OP");
                PluginSupport.getHelp().registerCommand("wxw connect [world]", "Load unloaded world.", thisPlugin, "OP");
                PluginSupport.getHelp().registerCommand("wxw modify [args]", "Modify settings of specified world.", thisPlugin, "OP");
                PluginSupport.getHelp().registerCommand("wxw info [world]", "Get info about specified world.", thisPlugin, "OP");
                PluginSupport.getHelp().registerCommand("wxw setspawn", "Set spawn of current world to current location.", thisPlugin, "OP");
                PluginSupport.getHelp().registerCommand("wxw create [args]", "Create new world with specified args.", thisPlugin, "OP");
                PluginSupport.getHelp().registerCommand("wxw spawn", "Go to spawn of current world.", thisPlugin, "OP");
            }
        }
    }
}
