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
package com.wormhole_xtreme.worlds.events;

import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.ServerListener;

import com.wormhole_xtreme.worlds.config.ConfigManager;
import com.wormhole_xtreme.worlds.plugin.HelpSupport;
import com.wormhole_xtreme.worlds.plugin.PermissionsSupport;

/**
 * The Class PluginDisable.
 * 
 * @author alron
 */
public class PluginDisable extends ServerListener {

    /* (non-Javadoc)
     * @see org.bukkit.event.server.ServerListener#onPluginDisable(org.bukkit.event.server.PluginDisableEvent)
     */
    @Override
    public void onPluginDisable(final PluginDisableEvent pluginDisableEvent) {
        final String disableEventPluginName = pluginDisableEvent.getPlugin().getDescription().getName();
        if (disableEventPluginName.equals("Permissions") && ConfigManager.getServerOptionPermissions()) {
            PermissionsSupport.disablePermissions();
        }
        else if (disableEventPluginName.equals("Help") && ConfigManager.getServerOptionHelp()) {
            HelpSupport.disableHelp();
        }
    }
}
