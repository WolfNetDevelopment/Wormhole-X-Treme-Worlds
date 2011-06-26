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
package de.luricos.bukkit.WormholeXTreme.Worlds.events;

import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.PluginManager;

import de.luricos.bukkit.WormholeXTreme.Worlds.WormholeXTremeWorlds;
import de.luricos.bukkit.WormholeXTreme.Worlds.events.block.BlockEventHandler;
import de.luricos.bukkit.WormholeXTreme.Worlds.events.entity.EntityEventHandler;
import de.luricos.bukkit.WormholeXTreme.Worlds.events.server.ServerEventHandler;
import de.luricos.bukkit.WormholeXTreme.Worlds.events.weather.WeatherEventHandler;
import de.luricos.bukkit.WormholeXTreme.Worlds.events.world.WorldEventHandler;

/**
 * The Class EventUtilities.
 * 
 * @author alron
 */
public class EventUtilities {

    /** The Constant thisPlugin. */
    private static final WormholeXTremeWorlds thisPlugin = WormholeXTremeWorlds.getThisPlugin();

    /** The Constant pluginManager. */
    private static final PluginManager pluginManager = thisPlugin.getServer().getPluginManager();

    /** The Constant blockEventHandler. */
    private static final BlockEventHandler blockEventHandler = new BlockEventHandler();

    /** The Constant entityEventHandler. */
    private static final EntityEventHandler entityEventHandler = new EntityEventHandler();

    /** The Constant serverEventHandler. */
    private static final ServerEventHandler serverEventHandler = new ServerEventHandler();

    /** The Constant worldEventHandler. */
    private static final WorldEventHandler worldEventHandler = new WorldEventHandler();

    /** The Constant weatherEventHandler. */
    private static final WeatherEventHandler weatherEventHandler = new WeatherEventHandler();

    /**
     * Register events.
     */
    public static void registerEvents() {
        pluginManager.registerEvent(Event.Type.PLUGIN_ENABLE, serverEventHandler, Priority.Monitor, thisPlugin);
        pluginManager.registerEvent(Event.Type.PLUGIN_DISABLE, serverEventHandler, Priority.Monitor, thisPlugin);

        pluginManager.registerEvent(Event.Type.WORLD_LOAD, worldEventHandler, Priority.Monitor, thisPlugin);
        pluginManager.registerEvent(Event.Type.WORLD_SAVE, worldEventHandler, Priority.Monitor, thisPlugin);
        pluginManager.registerEvent(Event.Type.SPAWN_CHANGE, worldEventHandler, Priority.Monitor, thisPlugin);
        pluginManager.registerEvent(Event.Type.CHUNK_UNLOAD, worldEventHandler, Priority.High, thisPlugin);

        pluginManager.registerEvent(Event.Type.BLOCK_FROMTO, blockEventHandler, Priority.Lowest, thisPlugin);
        pluginManager.registerEvent(Event.Type.BLOCK_IGNITE, blockEventHandler, Priority.Lowest, thisPlugin);
        pluginManager.registerEvent(Event.Type.BLOCK_BURN, blockEventHandler, Priority.Lowest, thisPlugin);

        pluginManager.registerEvent(Event.Type.CREATURE_SPAWN, entityEventHandler, Priority.Lowest, thisPlugin);
        pluginManager.registerEvent(Event.Type.ENTITY_DAMAGE, entityEventHandler, Priority.Lowest, thisPlugin);

        pluginManager.registerEvent(Event.Type.WEATHER_CHANGE, weatherEventHandler, Priority.Lowest, thisPlugin);
        pluginManager.registerEvent(Event.Type.THUNDER_CHANGE, weatherEventHandler, Priority.Lowest, thisPlugin);
        pluginManager.registerEvent(Event.Type.LIGHTNING_STRIKE, weatherEventHandler, Priority.Lowest, thisPlugin);
    }
}
