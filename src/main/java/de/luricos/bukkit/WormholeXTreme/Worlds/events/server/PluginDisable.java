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
package de.luricos.bukkit.WormholeXTreme.Worlds.events.server;

import de.luricos.bukkit.WormholeXTreme.Worlds.config.ConfigManager;
import de.luricos.bukkit.WormholeXTreme.Worlds.plugin.HelpSupport;
import de.luricos.bukkit.WormholeXTreme.Worlds.plugin.PermissionsSupport;

/**
 * The Class PluginDisable.
 * 
 * @author alron
 */
class PluginDisable {

    /**
     * Handle plugin disable.
     * 
     * @param plugin
     *            the plugin
     */
    static void handlePluginDisable(final String plugin) {
        if (plugin.equals("Permissions") && ConfigManager.getServerOptionPermissions()) {
            PermissionsSupport.disablePermissions();
        }
        else if (plugin.equals("Help") && ConfigManager.getServerOptionHelp()) {
            HelpSupport.disableHelp();
        }
    }
}
