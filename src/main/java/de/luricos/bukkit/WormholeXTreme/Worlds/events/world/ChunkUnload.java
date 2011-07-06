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
package de.luricos.bukkit.WormholeXTreme.Worlds.events.world;

import org.bukkit.Chunk;

import de.luricos.bukkit.WormholeXTreme.Worlds.world.WorldManager;
import de.luricos.bukkit.WormholeXTreme.Worlds.world.WormholeWorld;

/**
 * The Class ChunkUnload.
 * 
 * @author alron
 */
public class ChunkUnload {

    /**
     * Handle chunk unload.
     * 
     * @param chunk
     *            the chunk
     * @return true, if successful
     */
    public static boolean handleChunkUnload(final Chunk chunk) {
        if (WorldManager.isWormholeWorld(chunk.getWorld())) {
            final WormholeWorld wormholeWorld = WorldManager.getWormholeWorld(chunk.getWorld());
            return wormholeWorld.isWorldStickyChunk(chunk);
        }
        return false;
    }
}
