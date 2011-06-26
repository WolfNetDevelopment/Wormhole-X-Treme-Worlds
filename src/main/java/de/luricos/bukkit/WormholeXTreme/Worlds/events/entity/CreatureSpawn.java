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
package de.luricos.bukkit.WormholeXTreme.Worlds.events.entity;

import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Flying;
import org.bukkit.entity.Monster;
import org.bukkit.entity.WaterMob;

import de.luricos.bukkit.WormholeXTreme.Worlds.world.WorldManager;
import de.luricos.bukkit.WormholeXTreme.Worlds.world.WormholeWorld;

/**
 * The Class CreatureSpawn.
 * 
 * @author alron
 */
class CreatureSpawn {

    /**
     * Handle creature spawn.
     * 
     * @param entity
     *            the entity
     * @return true, if successful
     */
    static boolean handleCreatureSpawn(final Entity entity) {
        if ((entity != null) && WorldManager.isWormholeWorld(entity.getWorld())) {
            final WormholeWorld wormholeWorld = WorldManager.getWormholeWorld(entity.getWorld());
            if (( !wormholeWorld.isWorldAllowSpawnHostiles() && ((entity instanceof Monster) || (entity instanceof Flying))) || ( !wormholeWorld.isWorldAllowSpawnNeutrals() && ((entity instanceof Animals) || (entity instanceof WaterMob)))) {
                return true;
            }
        }
        return false;
    }
}