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
package de.luricos.bukkit.WormholeXTreme.Worlds.block;

import org.bukkit.block.Block;
import org.bukkit.event.block.BlockIgniteEvent.IgniteCause;

import de.luricos.bukkit.WormholeXTreme.Worlds.world.WorldManager;
import de.luricos.bukkit.WormholeXTreme.Worlds.world.WormholeWorld;

/**
 * The Class BlockIgnite.
 * 
 * @author alron
 */
public class BlockIgnite {

    /**
     * Handle block ignite.
     * 
     * @param block
     *            the block
     * @param igniteCause
     *            the ignite cause
     * @return true, if successful
     */
    static boolean handleBlockIgnite(final Block block, final IgniteCause igniteCause) {
        final String worldName = block.getWorld().getName();
        if (WorldManager.isWormholeWorld(worldName)) {
            final WormholeWorld wormholeWorld = WorldManager.getWormholeWorld(worldName);
            if (!wormholeWorld.isWorldAllowFire()) {
                return true;
            } else if (igniteCause != null) {
                switch (igniteCause) {
                    case LAVA:
                        return wormholeWorld.isWorldAllowLavaFire() ? false : true;
                    case SPREAD:
                        return wormholeWorld.isWorldAllowFireSpread() ? false : true;
                    case LIGHTNING:
                        return wormholeWorld.isWorldAllowLightningFire() ? false : true;
                    case FLINT_AND_STEEL:
                        return wormholeWorld.isWorldAllowPlayerStartFire() ? false : true;
                    default:
                        break;
                }
            }
        }
        return false;
    }
}