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
 * The Enum TimeLockType.
 * The Time types we support locking a world to.
 * 
 * @author alron
 */
public enum TimeLockType {
    /** No specific time. */
    NONE(0),

    /** Day. */
    DAY(1),

    /** Night. */
    NIGHT(2);

    /** The id. */
    private final int id;

    /** The Constant lookupId. */
    private static final Map<Integer, TimeLockType> lookupId = new HashMap<Integer, TimeLockType>();

    /** The Constant lookupName. */
    private static final Map<String, TimeLockType> lookupName = new HashMap<String, TimeLockType>();

    static {
        for (final TimeLockType timeType : values()) {
            lookupId.put(timeType.getId(), timeType);
            lookupName.put(timeType.name(), timeType);
        }
    }

    /**
     * Gets the time type.
     * 
     * @param id
     *            the id
     * @return the time type
     */
    public static TimeLockType getTimeType(final int id) { // NO_UCD
        return lookupId.get(id);
    }

    /**
     * Gets the time type.
     * 
     * @param name
     *            the name
     * @return the time type
     */
    public static TimeLockType getTimeType(final String name) {
        final TimeLockType tT = lookupName.get(name);
        return tT != null ? tT : TimeLockType.NONE;
    }

    /**
     * Instantiates a new time type.
     * 
     * @param id
     *            the id
     */
    private TimeLockType(final int id) {
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
