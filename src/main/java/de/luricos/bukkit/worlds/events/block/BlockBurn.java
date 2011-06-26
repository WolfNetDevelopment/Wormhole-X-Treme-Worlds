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
package de.luricos.bukkit.worlds.events.block;

import org.bukkit.block.Block;

import de.luricos.bukkit.worlds.world.WorldManager;
import de.luricos.bukkit.worlds.world.WormholeWorld;

/**
 * The Class BlockBurn.
 * 
 * @author alron
 */
class BlockBurn {

    /**
     * Handle block burn.
     * 
     * @param block
     *            the block
     * @return true, if successful
     */
    static boolean handleBlockBurn(final Block block) {
        final String worldName = block.getWorld().getName();
        if (WorldManager.isWormholeWorld(worldName)) {
            final WormholeWorld wormholeWorld = WorldManager.getWormholeWorld(worldName);
            if ( !wormholeWorld.isWorldAllowFireSpread() || !wormholeWorld.isWorldAllowFire()) {
                return true;
            }
        }
        return false;
    }
}
