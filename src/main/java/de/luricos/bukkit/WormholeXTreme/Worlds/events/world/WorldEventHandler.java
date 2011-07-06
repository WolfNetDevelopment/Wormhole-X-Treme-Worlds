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

import java.util.logging.Level;

import org.bukkit.World;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.event.world.SpawnChangeEvent;
import org.bukkit.event.world.WorldListener;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.event.world.WorldSaveEvent;

import de.luricos.bukkit.WormholeXTreme.Worlds.WormholeXTremeWorlds;

/**
 * The Class WorldEventHandler.
 * 
 * @author alron
 */
public class WorldEventHandler extends WorldListener {

    /** The Constant thisPlugin. */
    private static final WormholeXTremeWorlds thisPlugin = WormholeXTremeWorlds.getThisPlugin();

    /* (non-Javadoc)
     * @see org.bukkit.event.world.WorldListener#onChunkUnload(org.bukkit.event.world.ChunkUnloadEvent)
     */
    @Override
    public void onChunkUnload(final ChunkUnloadEvent event) {
        if (!event.isCancelled() && (event.getChunk() != null) && ChunkUnload.handleChunkUnload(event.getChunk())) {
            event.setCancelled(true);
            thisPlugin.prettyLog(Level.FINE, false, "Chunk Unload Cancelled: " + event.getChunk().toString() + " World: " + event.getWorld().getName());
        }
    }

    /* (non-Javadoc)
     * @see org.bukkit.event.world.WorldListener#onSpawnChange(org.bukkit.event.world.SpawnChangeEvent)
     */
    @Override
    public void onSpawnChange(final SpawnChangeEvent event) {
        if (SpawnChange.handleSpawnChange(event.getWorld())) {
            thisPlugin.prettyLog(Level.FINE, false, "Set worldSpawn to new location:" + event.getWorld().getSpawnLocation().toString() + " for world: " + event.getWorld().getName());
        }
    }

    /* (non-Javadoc)
     * @see org.bukkit.event.world.WorldListener#onWorldLoad(org.bukkit.event.world.WorldLoadEvent)
     */
    @Override
    public void onWorldLoad(final WorldLoadEvent event) {
        final World world = event.getWorld();
        if ((world != null) && WorldLoad.handleWorldLoad(world.getName())) {
            thisPlugin.prettyLog(Level.FINE, false, "World Load caught and handled: " + world.getName());
        }
    }

    /* (non-Javadoc)
     * @see org.bukkit.event.world.WorldListener#onWorldSave(org.bukkit.event.world.WorldSaveEvent)
     */
    @Override
    public void onWorldSave(final WorldSaveEvent event) {
        final World world = event.getWorld();
        if (world != null) {
            thisPlugin.prettyLog(Level.FINE, false, "Caught World Save: " + world.getName());
        }
    }
}
