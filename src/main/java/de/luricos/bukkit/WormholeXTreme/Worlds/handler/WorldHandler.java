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
package de.luricos.bukkit.WormholeXTreme.Worlds.handler;

import de.luricos.bukkit.WormholeXTreme.Worlds.config.ResponseType;
import de.luricos.bukkit.WormholeXTreme.Worlds.permissions.CheckPerms;
import de.luricos.bukkit.WormholeXTreme.Worlds.utils.WXLogger;
import de.luricos.bukkit.WormholeXTreme.Worlds.world.WorldManager;
import de.luricos.bukkit.WormholeXTreme.Worlds.world.WormholeWorld;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.logging.Level;

/**
 * The Class WorldHandler.
 * 
 * @author alron
 */
public class WorldHandler {

    /**
     * Adds the chunk sticky and loads the Wormhole World if it is unloaded.
     * 
     * @param stickyChunk the sticky chunk
     * @param ownerPlugin the owner plugin
     * @return true, if successful
     */
    public boolean addStickyChunk(final Chunk stickyChunk, final String ownerPlugin) {
        if ((stickyChunk != null) && (ownerPlugin != null)) {
            final String worldName = stickyChunk.getWorld().getName();
            final int stickyChunkX = stickyChunk.getX();
            final int stickyChunkZ = stickyChunk.getZ();
            WormholeWorld wormholeWorld = WorldManager.getWormholeWorld(worldName);
            WXLogger.prettyLog(Level.FINE, false, "Sticky Chunk Addition: " + stickyChunk.toString() + " World: " + stickyChunk.getWorld().getName() + " Plugin: " + ownerPlugin);
            if (wormholeWorld != null) {
                if (wormholeWorld.isWorldLoaded() && wormholeWorld.addWorldStickyChunk(stickyChunk, ownerPlugin)) {
                    if (!wormholeWorld.getWorld().isChunkLoaded(stickyChunkX, stickyChunkZ)) {
                        wormholeWorld.getWorld().loadChunk(stickyChunkX, stickyChunkZ);
                        WXLogger.prettyLog(Level.FINE, false, "Loaded Sticky Chunk: " + stickyChunk.toString());
                    }
                    return WorldManager.addWorld(wormholeWorld);
                } else if (WorldManager.loadWorld(wormholeWorld) && ((wormholeWorld = WorldManager.getWormholeWorld(worldName)) != null) && wormholeWorld.addWorldStickyChunk(stickyChunk, ownerPlugin)) {
                    if (!wormholeWorld.getWorld().isChunkLoaded(stickyChunkX, stickyChunkZ)) {
                        wormholeWorld.getWorld().loadChunk(stickyChunkX, stickyChunkZ);
                        WXLogger.prettyLog(Level.FINE, false, "Loaded Sticky Chunk: " + stickyChunk.toString());
                    }
                    return WorldManager.addWorld(wormholeWorld);
                }
            }
        }
        return false;
    }

    /**
     * Gets the player respawn location.
     * 
     * @param player the player
     * @return the player respawn location
     */
    public Location getPlayerRespawnLocation(final Player player) {
        final String worldName = player.getWorld().getName();
        return WorldManager.isWormholeWorld(worldName)
                ? WorldManager.getSafeSpawnLocation(WorldManager.getWormholeWorld(worldName), player) : null;
    }

    /**
     * Load world.
     * 
     * @param worldName the world name
     * @return true, if successful
     */
    public boolean loadWorld(final String worldName) {
        if (!WorldManager.isWormholeWorld(worldName)) {
            return false;
        }

        final WormholeWorld wormholeWorld = WorldManager.getWormholeWorld(worldName);
        if (wormholeWorld.isWorldLoaded()) {
            return true;
        }
        
        WorldManager.loadWorld(wormholeWorld);
        
        return true;
    }

    /**
     * Delete sticky chunk only if a world is loaded.
     * 
     * @param stickyChunk the sticky chunk
     * @param ownerPlugin the owner plugin
     * @return true, if successful
     */
    public boolean removeStickyChunk(final Chunk stickyChunk, final String ownerPlugin) {
        if ((stickyChunk != null) && (ownerPlugin != null)) {
            final String worldName = stickyChunk.getWorld().getName();
            final WormholeWorld wormholeWorld = WorldManager.getWormholeWorld(worldName);
            final int stickyChunkX = stickyChunk.getX();
            final int stickyChunkZ = stickyChunk.getZ();
            WXLogger.prettyLog(Level.FINE, false, "Sticky Chunk Removal: " + stickyChunk.toString() + " World: " + stickyChunk.getWorld().getName() + " Plugin: " + ownerPlugin);
            if ((wormholeWorld != null) && wormholeWorld.isWorldLoaded() && wormholeWorld.removeWorldStickyChunk(stickyChunk, ownerPlugin)) {
                if (wormholeWorld.getWorld().isChunkLoaded(stickyChunkX, stickyChunkZ) && !wormholeWorld.isWorldStickyChunk(stickyChunk)) {
                    wormholeWorld.getWorld().unloadChunkRequest(stickyChunkX, stickyChunkZ);
                    WXLogger.prettyLog(Level.FINE, false, "Unload Queued Former Sticky Chunk: " + stickyChunk.toString());
                }
                return WorldManager.addWorld(wormholeWorld);
            }
        }
        return false;
    }

    /**
     * Spawn player.
     * 
     * @param player the player
     * @return true, if successful
     */
    public boolean spawnPlayer(final Player player) {
        return spawnPlayer(player, null, true);
    }

    /**
     * Spawn player.
     * 
     * @param player
     *            the player
     * @param permissionCheck
     *            the permission check
     * @return true, if successful
     */
    public boolean spawnPlayer(final Player player, final boolean permissionCheck) { // NO_UCD
        return spawnPlayer(player, null, permissionCheck);
    }

    /**
     * Spawn player.
     * 
     * @param player the player
     * @param worldName the world name
     * @param permissionCheck the permission check
     * @return true, if successful
     */
    public boolean spawnPlayer(final Player player, final String worldName, final boolean permissionCheck) { // NO_UCD
        if (player != null) {
            if (permissionCheck && !CheckPerms.hasPermission(player, "wxw.spawn")) {
                player.sendMessage(ResponseType.ERROR_PERMISSION_NO.toString());
            } else {
                final WormholeWorld wormholeWorld = worldName != null ? WorldManager.getWormholeWorld(worldName)
                        : WorldManager.getWorldFromPlayer(player);
                if (wormholeWorld != null) {
                    return player.teleport(WorldManager.getSafeSpawnLocation(wormholeWorld, player));
                } else {
                    player.sendMessage(ResponseType.ERROR_COMMAND_ONLY_MANAGED_WORLD.toString());
                }
            }
        }
        return false;
    }
}
