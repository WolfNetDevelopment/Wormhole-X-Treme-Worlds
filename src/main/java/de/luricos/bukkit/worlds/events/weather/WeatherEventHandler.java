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
package de.luricos.bukkit.worlds.events.weather;

import java.util.logging.Level;

import org.bukkit.event.weather.LightningStrikeEvent;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.event.weather.WeatherListener;

import de.luricos.bukkit.worlds.WormholeXTremeWorlds;

/**
 * The Class WeatherEventHandler.
 * 
 * @author alron
 */
public class WeatherEventHandler extends WeatherListener {

    /** The Constant thisPlugin. */
    private static final WormholeXTremeWorlds thisPlugin = WormholeXTremeWorlds.getThisPlugin();

    /* (non-Javadoc)
     * @see org.bukkit.event.weather.WeatherListener#onLightningStrike(org.bukkit.event.weather.LightningStrikeEvent)
     */
    @Override
    public void onLightningStrike(final LightningStrikeEvent event) {
        thisPlugin.prettyLog(Level.FINE, false, "Lightning Strike caught on world: " + event.getWorld().getName());
        if ( !event.isCancelled() && LightningStrike.handleLightningStrike(event.getWorld().getName())) {
            event.setCancelled(true);
            thisPlugin.prettyLog(Level.FINE, false, "Lightning Strike cancelled on world: " + event.getWorld().getName());
        }
    }

    /* (non-Javadoc)
     * @see org.bukkit.event.weather.WeatherListener#onThunderChange(org.bukkit.event.weather.ThunderChangeEvent)
     */
    @Override
    public void onThunderChange(final ThunderChangeEvent event) {
        thisPlugin.prettyLog(Level.FINE, false, "Thunder Change caught on world: " + event.getWorld().getName() + " state: " + event.toThunderState());
        if ( !event.isCancelled() && ThunderChange.handleThunderChange(event.getWorld().getName(), event.toThunderState())) {
            event.setCancelled(true);
            thisPlugin.prettyLog(Level.FINE, false, "Thunder Change cancelled on world: " + event.getWorld().getName());
        }
    }

    /* (non-Javadoc)
     * @see org.bukkit.event.weather.WeatherListener#onWeatherChange(org.bukkit.event.weather.WeatherChangeEvent)
     */
    @Override
    public void onWeatherChange(final WeatherChangeEvent event) {
        thisPlugin.prettyLog(Level.FINE, false, "Weather Change caught on world: " + event.getWorld().getName() + " state: " + event.toWeatherState());
        if ( !event.isCancelled() && WeatherChange.handleWeatherChange(event.getWorld().getName(), event.toWeatherState())) {
            event.setCancelled(true);
            thisPlugin.prettyLog(Level.FINE, false, "Weather Change cancelled on world: " + event.getWorld().getName());
        }
    }
}
