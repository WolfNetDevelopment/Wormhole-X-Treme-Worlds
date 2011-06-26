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
package de.luricos.bukkit.WormholeXTreme.Worlds.handler;

import java.util.logging.Level;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import de.luricos.bukkit.WormholeXTreme.Worlds.WormholeXTremeWorlds;
import de.luricos.bukkit.WormholeXTreme.Worlds.config.ResponseType;
import de.luricos.bukkit.WormholeXTreme.Worlds.permissions.PermissionType;
import de.luricos.bukkit.WormholeXTreme.Worlds.world.WorldManager;
import de.luricos.bukkit.WormholeXTreme.Worlds.world.WormholeWorld;

/**
 * The Class WorldHandler.
 * 
 * @author alron
 */
public class WorldHandler {

    /** The Constant thisPlugin. */
    private static final WormholeXTremeWorlds thisPlugin = WormholeXTremeWorlds.getThisPlugin();

    /**
     * Adds the chunk sticky and loads the Wormhole World if it is unloaded.
     * 
     * @param stickyChunk
     *            the sticky chunk
     * @param ownerPlugin
     *            the owner plugin
     * @return true, if successful
     */
    public boolean addStickyChunk(final Chunk stickyChunk, final String ownerPlugin) {
        if ((stickyChunk != null) && (ownerPlugin != null)) {
            final String worldName = stickyChunk.getWorld().getName();
            final int stickyChunkX = stickyChunk.getX();
            final int stickyChunkZ = stickyChunk.getZ();
            WormholeWorld wormholeWorld = WorldManager.getWormholeWorld(worldName);
            thisPlugin.prettyLog(Level.FINE, false, "Sticky Chunk Addition: " + stickyChunk.toString() + " World: " + stickyChunk.getWorld().getName() + " Plugin: " + ownerPlugin);
            if (wormholeWorld != null) {
                if (wormholeWorld.isWorldLoaded() && wormholeWorld.addWorldStickyChunk(stickyChunk, ownerPlugin)) {
                    if ( !wormholeWorld.getThisWorld().isChunkLoaded(stickyChunkX, stickyChunkZ)) {
                        wormholeWorld.getThisWorld().loadChunk(stickyChunkX, stickyChunkZ);
                        thisPlugin.prettyLog(Level.FINE, false, "Loaded Sticky Chunk: " + stickyChunk.toString());
                    }
                    return WorldManager.addWorld(wormholeWorld);
                }
                else if (WorldManager.loadWorld(wormholeWorld) && ((wormholeWorld = WorldManager.getWormholeWorld(worldName)) != null) && wormholeWorld.addWorldStickyChunk(stickyChunk, ownerPlugin)) {
                    if ( !wormholeWorld.getThisWorld().isChunkLoaded(stickyChunkX, stickyChunkZ)) {
                        wormholeWorld.getThisWorld().loadChunk(stickyChunkX, stickyChunkZ);
                        thisPlugin.prettyLog(Level.FINE, false, "Loaded Sticky Chunk: " + stickyChunk.toString());
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
     * @param player
     *            the player
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
     * @param worldName
     *            the world name
     * @return true, if successful
     */
    public boolean loadWorld(final String worldName) {
        if (WorldManager.isWormholeWorld(worldName)) {
            final WormholeWorld wormholeWorld = WorldManager.getWormholeWorld(worldName);
            if ( !wormholeWorld.isWorldLoaded()) {
                WorldManager.loadWorld(wormholeWorld);
            }
            return true;
        }
        return false;
    }

    /**
     * Delete sticky chunk only if a world is loaded.
     * 
     * @param stickyChunk
     *            the sticky chunk
     * @param ownerPlugin
     *            the owner plugin
     * @return true, if successful
     */
    public boolean removeStickyChunk(final Chunk stickyChunk, final String ownerPlugin) {
        if ((stickyChunk != null) && (ownerPlugin != null)) {
            final String worldName = stickyChunk.getWorld().getName();
            final WormholeWorld wormholeWorld = WorldManager.getWormholeWorld(worldName);
            final int stickyChunkX = stickyChunk.getX();
            final int stickyChunkZ = stickyChunk.getZ();
            thisPlugin.prettyLog(Level.FINE, false, "Sticky Chunk Removal: " + stickyChunk.toString() + " World: " + stickyChunk.getWorld().getName() + " Plugin: " + ownerPlugin);
            if ((wormholeWorld != null) && wormholeWorld.isWorldLoaded() && wormholeWorld.removeWorldStickyChunk(stickyChunk, ownerPlugin)) {
                if (wormholeWorld.getThisWorld().isChunkLoaded(stickyChunkX, stickyChunkZ) && !wormholeWorld.isWorldStickyChunk(stickyChunk)) {
                    wormholeWorld.getThisWorld().unloadChunkRequest(stickyChunkX, stickyChunkZ);
                    thisPlugin.prettyLog(Level.FINE, false, "Unload Queued Former Sticky Chunk: " + stickyChunk.toString());
                }
                return WorldManager.addWorld(wormholeWorld);
            }
        }
        return false;
    }

    /**
     * Spawn player.
     * 
     * @param player
     *            the player
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
     * @param player
     *            the player
     * @param worldName
     *            the world name
     * @param permissionCheck
     *            the permission check
     * @return true, if successful
     */
    public boolean spawnPlayer(final Player player, final String worldName, final boolean permissionCheck) { // NO_UCD
        if (player != null) {
            if (permissionCheck && !PermissionType.SPAWN.checkPermission(player)) {
                player.sendMessage(ResponseType.ERROR_PERMISSION_NO.toString());
            }
            else {
                final WormholeWorld wormholeWorld = worldName != null ? WorldManager.getWormholeWorld(worldName)
                    : WorldManager.getWorldFromPlayer(player);
                if (wormholeWorld != null) {
                    return player.teleport(WorldManager.getSafeSpawnLocation(wormholeWorld, player));
                }
                else {
                    player.sendMessage(ResponseType.ERROR_COMMAND_ONLY_MANAGED_WORLD.toString());
                }
            }
        }
        return false;
    }

}
