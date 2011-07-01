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
package de.luricos.bukkit.WormholeXTreme.Worlds.world;

import java.util.HashMap;
import java.util.Map;

/**
 * The Enum WeatherLockType.
 * The types of weather we support locking a world to.
 * 
 * @author alron
 */
public enum WeatherLockType {

    /** No specific weather. */
    NONE(0),
    /** Clear Skys. */
    CLEAR(1),
    /** Rainy weather. */
    RAIN(2),
    /** Thunderstorms. */
    STORM(3);
    /** The id. */
    private final int id;
    /** The Constant lookupId. */
    private static final Map<Integer, WeatherLockType> lookupId = new HashMap<Integer, WeatherLockType>();
    /** The Constant lookupName. */
    private static final Map<String, WeatherLockType> lookupName = new HashMap<String, WeatherLockType>();

    static {
        for (final WeatherLockType weatherLockType : values()) {
            lookupId.put(weatherLockType.getId(), weatherLockType);
            lookupName.put(weatherLockType.name(), weatherLockType);
        }
    }

    /**
     * Gets the weather type.
     * 
     * @param id
     *            the id
     * @return the weather type
     */
    public static WeatherLockType getWeatherType(final int id) { // NO_UCD
        return lookupId.get(id);
    }

    /**
     * Gets the weather type.
     * 
     * @param name
     *            the name
     * @return the weather type
     */
    public static WeatherLockType getWeatherType(final String name) {
        final WeatherLockType wT = lookupName.get(name);
        return wT != null ? wT : WeatherLockType.NONE;
    }

    /**
     * Instantiates a new weather type.
     * 
     * @param id
     *            the id
     */
    private WeatherLockType(final int id) {
        this.id = id;
    }

    /**
     * Gets the id.
     * 
     * @return the id
     */
    public int getId() {
        return id;
    }
}
