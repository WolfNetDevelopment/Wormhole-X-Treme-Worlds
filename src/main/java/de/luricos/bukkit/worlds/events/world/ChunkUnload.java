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

import org.bukkit.Chunk;

import de.luricos.bukkit.worlds.world.WorldManager;
import de.luricos.bukkit.worlds.world.WormholeWorld;

/**
 * The Class ChunkUnload.
 * 
 * @author alron
 */
class ChunkUnload {

    /**
     * Handle chunk unload.
     * 
     * @param chunk
     *            the chunk
     * @return true, if successful
     */
    static boolean handleChunkUnload(final Chunk chunk) {
        if (WorldManager.isWormholeWorld(chunk.getWorld())) {
            final WormholeWorld wormholeWorld = WorldManager.getWormholeWorld(chunk.getWorld());
            return wormholeWorld.isWorldStickyChunk(chunk);
        }
        return false;
    }
}
