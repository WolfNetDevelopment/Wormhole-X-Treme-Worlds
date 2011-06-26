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
package de.luricos.bukkit.WormholeXTreme.Worlds.events.weather;

import de.luricos.bukkit.WormholeXTreme.Worlds.world.WorldManager;
import de.luricos.bukkit.WormholeXTreme.Worlds.world.WormholeWorld;

/**
 * The Class ThunderChange.
 * 
 * @author alron
 */
class ThunderChange {

    /**
     * Handle thunder change.
     * 
     * @param worldName
     *            the world name
     * @return true, if successful
     */
    static boolean handleThunderChange(final String worldName, final boolean thunder) {
        if (WorldManager.isWormholeWorld(worldName)) {
            final WormholeWorld wormholeWorld = WorldManager.getWormholeWorld(worldName);
            if (wormholeWorld.isWorldWeatherLock()) {
                switch (wormholeWorld.getWorldWeatherLockType()) {
                    case CLEAR :
                    case RAIN :
                        return thunder ? true : false;
                    case STORM :
                        return thunder ? false : true;
                    case NONE :
                    default :
                        return false;
                }
            }
        }
        return false;
    }
}
