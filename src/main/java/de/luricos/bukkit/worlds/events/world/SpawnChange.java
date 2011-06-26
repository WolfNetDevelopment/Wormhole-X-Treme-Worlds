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
package de.luricos.bukkit.worlds.events.world;

import org.bukkit.World;

import de.luricos.bukkit.worlds.world.WorldManager;
import de.luricos.bukkit.worlds.world.WormholeWorld;

/**
 * The Class SpawnChange.
 * 
 * @author alron
 */
class SpawnChange {

    /**
     * Handle spawn change.
     * 
     * @param world
     *            the world
     * @return true, if successful
     */
    static boolean handleSpawnChange(final World world) {
        final WormholeWorld wormholeWorld = WorldManager.getWormholeWorld(world);
        if (wormholeWorld != null) {
            if ((wormholeWorld.getWorldSpawn() != null) && !wormholeWorld.getWorldSpawn().equals(world.getSpawnLocation())) {
                wormholeWorld.setWorldSpawn(world.getSpawnLocation());
                wormholeWorld.setWorldCustomSpawn(wormholeWorld.getWorldSpawnToInt());
                WorldManager.addWorld(wormholeWorld);
                return true;
            }
        }
        return false;
    }
}
