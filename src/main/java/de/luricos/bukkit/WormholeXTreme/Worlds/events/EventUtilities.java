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
package de.luricos.bukkit.WormholeXTreme.Worlds.events;

import de.luricos.bukkit.WormholeXTreme.Worlds.WormholeXTremeWorlds;
import de.luricos.bukkit.WormholeXTreme.Worlds.block.BlockEventHandler;
import de.luricos.bukkit.WormholeXTreme.Worlds.events.entity.EntityEventHandler;
import de.luricos.bukkit.WormholeXTreme.Worlds.events.weather.WeatherEventHandler;
import de.luricos.bukkit.WormholeXTreme.Worlds.events.world.WorldEventHandler;
import org.bukkit.plugin.PluginManager;

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
    /** The Constant worldEventHandler. */
    private static final WorldEventHandler worldEventHandler = new WorldEventHandler();
    /** The Constant weatherEventHandler. */
    private static final WeatherEventHandler weatherEventHandler = new WeatherEventHandler();

    /**
     * Register events.
     */
    public static void registerEvents() {
        pluginManager.registerEvents(blockEventHandler, thisPlugin); // blockEvents
        pluginManager.registerEvents(worldEventHandler, thisPlugin); // worldEvents
        pluginManager.registerEvents(entityEventHandler, thisPlugin); // entityEvents
        pluginManager.registerEvents(weatherEventHandler, thisPlugin); // weatherEvents
    }
}
