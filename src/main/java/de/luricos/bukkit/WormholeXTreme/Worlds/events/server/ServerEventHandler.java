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

import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.event.server.ServerListener;

/**
 * The Class ServerEventHandler.
 * 
 * @author alron
 */
public class ServerEventHandler extends ServerListener {

    /* (non-Javadoc)
     * @see org.bukkit.event.server.ServerListener#onPluginDisable(org.bukkit.event.server.PluginDisableEvent)
     */
    @Override
    public void onPluginDisable(final PluginDisableEvent pluginDisableEvent) {
        final String plugin = pluginDisableEvent.getPlugin().toString();
        if (plugin != null) {
            PluginDisable.handlePluginDisable(plugin);
        }
    }

    /* (non-Javadoc)
     * @see org.bukkit.event.server.ServerListener#onPluginEnable(org.bukkit.event.server.PluginEnableEvent)
     */
    @Override
    public void onPluginEnable(final PluginEnableEvent pluginEnableEvent) {
        final String plugin = pluginEnableEvent.getPlugin().toString();
        if (plugin != null) {
            PluginEnable.handlePluginEnable(plugin);
        }
    }
}
