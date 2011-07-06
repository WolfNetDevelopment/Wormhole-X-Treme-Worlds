/*
 * Wormhole X-Treme Worlds Plugin for Bukkit
 * Copyright (C) 2011 Dean Bailey <https://github.com/alron/Wormhole-X-Treme-Worlds>
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
package de.luricos.bukkit.WormholeXTreme.Worlds.world;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.World.Environment;

/**
 * The WormholeWorld instance. Everything that we know about a world can be found here.
 * What we know about the world is distrusted though, as we cannot be sure data fed to us
 * by users is reliable.
 * 
 * @author alron
 */
public class WormholeWorld {

    /** The allow player contact damage. */
    private boolean playerAllowContactDamage = true;
    /** The allow player damage. */
    private boolean playerAllowDamage = true;
    /** The allow player drown. */
    private boolean playerAllowDrown = true;
    /** The allow player explosion damage. */
    private boolean playerAllowExplosionDamage = true;
    /** The allow player fall damage. */
    private boolean playerAllowFallDamage = true;
    /** Allow player fire damage. */
    private boolean playerAllowFireDamage = true;
    /** The allow player lava damage. */
    private boolean playerAllowLavaDamage = true;
    /** Allow player lightning damage. */
    private boolean playerAllowLightningDamage = true;
    /** The allow player suffocation. */
    private boolean playerAllowSuffocation = true;
    /** The allow player void damage. */
    private boolean playerAllowVoidDamage = true;
    /** The this world. */
    private World world = null;
    /** The world allow fire. */
    private boolean worldAllowFire = true;
    /** The allow fire spread. */
    private boolean worldAllowFireSpread = true;
    /** The allow lava fire. */
    private boolean worldAllowLavaFire = true;
    /** The allow lava spread. */
    private boolean worldAllowLavaSpread = true;
    /** Allow lightning fire. */
    private boolean worldAllowLightningFire = true;
    /** Allow players to start fires. */
    private boolean worldAllowPlayerStartFire = true;
    /** Allow Player versus Player. */
    private boolean worldAllowPvP = true;
    /** The allow hostiles. */
    private boolean worldAllowSpawnHostiles = true;
    /** The allow neutrals. */
    private boolean worldAllowSpawnNeutrals = true;
    /** The allow water spread. */
    private boolean worldAllowWaterSpread = true;
    /** The allow thunder. */
    private boolean worldAllowWeatherThunder = true;
    /** The autoconnect world. */
    private boolean worldAutoload = true;
    /** The world custom spawn. */
    private int[] worldCustomSpawn = null;
    /** The loaded. */
    private boolean worldLoaded = false;
    /** The world name. */
    private String worldName = "";
    /** The world seed. */
    private long worldSeed = 0;
    /** The world spawn. */
    private Location worldSpawn = null;
    /** The world sticky chunks. */
    private final ConcurrentHashMap<Chunk, ConcurrentHashMap<String, Integer>> worldStickyChunks = new ConcurrentHashMap<Chunk, ConcurrentHashMap<String, Integer>>();
    /** The time lock. */
    private boolean worldTimeLock = false;
    /** The time lock type. */
    private TimeLockType worldTimeLockType = TimeLockType.NONE;
    /** The normal world. */
    private boolean worldTypeNormal = false;    
    /** The nether world. */
    private boolean worldTypeNether = false;
    /** The skyland world */
    private boolean worldTypeSkylands = false;
    /** The weather lock. */
    private boolean worldWeatherLock = false;
    /** The weather lock type. */
    private WeatherLockType worldWeatherLockType = WeatherLockType.NONE;

    /**
     * Instantiates a new world.
     */
    public WormholeWorld() {
    }

    /**
     * Adds the world sticky chunk.
     * 
     * @param stickyChunk
     *            the sticky chunk
     * @param ownerPlugin
     *            the owner plugin
     * @return true, if successful
     */
    public boolean addWorldStickyChunk(final Chunk stickyChunk, final String ownerPlugin) {
        if ((stickyChunk != null) && (ownerPlugin != null)) {
            if (getWorldStickyChunks().containsKey(stickyChunk)) {
                getWorldStickyChunks().get(stickyChunk).put(ownerPlugin, getWorldStickyChunks().get(stickyChunk).containsKey(ownerPlugin)
                        ? getWorldStickyChunks().get(stickyChunk).get(ownerPlugin) + 1 : 1);
            } else {
                final ConcurrentHashMap<String, Integer> ownerPlugins = new ConcurrentHashMap<String, Integer>();
                ownerPlugins.put(ownerPlugin, 1);
                getWorldStickyChunks().put(stickyChunk, ownerPlugins);
            }
            return true;
        }
        return false;
    }

    /**
     * Gets the all world sticky chunks.
     * 
     * @return the all world sticky chunks
     */
    public Set<Chunk> getAllWorldStickyChunks() {
        return getWorldStickyChunks().keySet();
    }

    /**
     * Gets the this world.
     * 
     * @return the world
     */
    public World getWorld() {
        return world;
    }

    /**
     * Gets the world custom spawn.
     * 
     * @return the world custom spawn
     */
    public int[] getWorldCustomSpawn() {
        return worldCustomSpawn.clone();
    }

    /**
     * Gets the world name.
     * 
     * @return the worldName
     */
    public String getWorldName() {
        return worldName;
    }

    /**
     * Gets the world seed.
     * 
     * @return the world seed
     */
    public long getWorldSeed() {
        return worldSeed;
    }

    /**
     * Gets the world custom spawn.
     * 
     * @return the worldCustomSpawn
     */
    public Location getWorldSpawn() {
        return worldSpawn;
    }
    
    /**
     * Get selected WoldEnvironment
     * 
     * @return null if nothing was set; this should lead into not loading the world via worldmanager
     */
    public Environment getWorldEnvironment() {
        if (this.isWorldTypeNormal())
            return Environment.NORMAL;
        
        if (this.isWorldTypeNether())
            return Environment.NETHER;
        
        if (this.isWorldTypeSkylands())
            return Environment.SKYLANDS;
        
        
        
        return null;
    }
    
    /**
     * Get WorldType as string
     * 
     * @return 
     */
    public String getWorldType() {
        return this.getWorldEnvironment().toString();
    }

    /**
     * Gets the world spawn to int[].
     * 
     * @return the world spawn to int[]
     */
    public int[] getWorldSpawnToInt() {
        return new int[]{
                    (int) worldSpawn.getX(), (int) worldSpawn.getY(), (int) worldSpawn.getZ()
                };
    }

    public ConcurrentHashMap<Chunk, ConcurrentHashMap<String, Integer>> getWorldStickyChunks() {
        return worldStickyChunks;
    }

    /**
     * Gets the time lock type.
     * 
     * @return the time lock type
     */
    public TimeLockType getWorldTimeLockType() {
        return worldTimeLockType;
    }

    /**
     * Gets the weather lock type.
     * 
     * @return the weather lock type
     */
    public WeatherLockType getWorldWeatherLockType() {
        return worldWeatherLockType;
    }

    /**
     * Checks if is allow player contact damage.
     * 
     * @return true, if is allow player contact damage
     */
    public boolean isPlayerAllowContactDamage() {
        return playerAllowContactDamage;
    }

    /**
     * Checks if is allow player damage.
     * 
     * @return true, if is allow player damage
     */
    public boolean isPlayerAllowDamage() {
        return playerAllowDamage;
    }

    /**
     * Checks if is allow player drown.
     * 
     * @return true, if is allow player drown
     */
    public boolean isPlayerAllowDrown() {
        return playerAllowDrown;
    }

    /**
     * Checks if is allow player explosion damage.
     * 
     * @return true, if is allow player explosion damage
     */
    public boolean isPlayerAllowExplosionDamage() {
        return playerAllowExplosionDamage;
    }

    /**
     * Checks if is allow player fall damage.
     * 
     * @return true, if is allow player fall damage
     */
    public boolean isPlayerAllowFallDamage() {
        return playerAllowFallDamage;
    }

    /**
     * Checks if is allow player fire damage.
     * 
     * @return the allow player fire damage
     */
    public boolean isPlayerAllowFireDamage() {
        return playerAllowFireDamage;
    }

    /**
     * Checks if is allow player lava damage.
     * 
     * @return true, if is allow player lava damage
     */
    public boolean isPlayerAllowLavaDamage() {
        return playerAllowLavaDamage;
    }

    /**
     * Checks if is allow player lightning damage.
     * 
     * @return the allow player lightning damage
     */
    public boolean isPlayerAllowLightningDamage() {
        return playerAllowLightningDamage;
    }

    /**
     * Checks if is allow player suffocation.
     * 
     * @return true, if is allow player suffocation
     */
    public boolean isPlayerAllowSuffocation() {
        return playerAllowSuffocation;
    }

    /**
     * Checks if is allow player void damage.
     * 
     * @return true, if is allow player void damage
     */
    public boolean isPlayerAllowVoidDamage() {
        return playerAllowVoidDamage;
    }

    /**
     * Checks if is the world allow fire.
     * 
     * @return the world allow fire
     */
    public boolean isWorldAllowFire() {
        return worldAllowFire;
    }

    /**
     * Checks if is allow fire spread.
     * 
     * @return true, if is allow fire spread
     */
    public boolean isWorldAllowFireSpread() {
        return worldAllowFireSpread;
    }

    /**
     * Checks if is allow lava fire.
     * 
     * @return true, if is allow lava fire
     */
    public boolean isWorldAllowLavaFire() {
        return worldAllowLavaFire;
    }

    /**
     * Checks if is allow lava spread.
     * 
     * @return true, if is allow lava spread
     */
    public boolean isWorldAllowLavaSpread() {
        return worldAllowLavaSpread;
    }

    /**
     * Checks if is allow lightning fire.
     * 
     * @return the allow lightning fire
     */
    public boolean isWorldAllowLightningFire() {
        return worldAllowLightningFire;
    }

    /**
     * Checks if is allow players to start fires.
     * 
     * @return the allow players to start fires
     */
    public boolean isWorldAllowPlayerStartFire() {
        return worldAllowPlayerStartFire;
    }

    /**
     * Checks if PvP is allowed on this world.
     * 
     * @return true, if PvP is allowed.
     */
    public boolean isWorldAllowPvP() {
        return worldAllowPvP;
    }

    /**
     * Checks if is allow hostiles.
     * 
     * @return the allowHostiles
     */
    public boolean isWorldAllowSpawnHostiles() {
        return worldAllowSpawnHostiles;
    }

    /**
     * Checks if is the allow neutrals.
     * 
     * @return the allowNeutrals
     */
    public boolean isWorldAllowSpawnNeutrals() {
        return worldAllowSpawnNeutrals;
    }

    /**
     * Checks if is allow water spread.
     * 
     * @return true, if is allow water spread
     */
    public boolean isWorldAllowWaterSpread() {
        return worldAllowWaterSpread;
    }

    /**
     * Checks if is allow thunder.
     * 
     * @return true, if is allow thunder
     */
    public boolean isWorldAllowWeatherThunder() {
        return worldAllowWeatherThunder;
    }

    /**
     * Checks if is autoconnect world.
     * 
     * @return true, if is autoconnect world
     */
    public boolean isWorldAutoload() {
        return worldAutoload;
    }

    /**
     * Checks if is world loaded.
     * 
     * @return true, if is world loaded
     */
    public boolean isWorldLoaded() {
        return worldLoaded;
    }

    /**
     * Checks if is world sticky chunk.
     * 
     * @param stickyChunk
     *            the sticky chunk
     * @return true, if is world sticky chunk
     */
    public boolean isWorldStickyChunk(final Chunk stickyChunk) {
        return getWorldStickyChunks().containsKey(stickyChunk);
    }

    /**
     * Checks if is time lock.
     * 
     * @return true, if is time lock
     */
    public boolean isWorldTimeLock() {
        return worldTimeLock;
    }

    /**
     * Checks if this world is a NORMAL env.
     * 
     * @return true, if this is a normal world
     */
    public boolean isWorldTypeNormal() {
        return worldTypeNormal;
    }    
    
    /**
     * Checks if this world is a NETHER env.
     * 
     * @return true, if this is a nether world
     */
    public boolean isWorldTypeNether() {
        return worldTypeNether;
    }
    
    /**
     * Checks if this world is a SKYLANDS env.
     * 
     * @return true, if this is a skylands world
     */
    public boolean isWorldTypeSkylands() {
        return worldTypeSkylands;
    }    

    /**
     * Checks if is weather lock.
     * 
     * @return true, if is weather lock
     */
    public boolean isWorldWeatherLock() {
        return worldWeatherLock;
    }

    /**
     * Removes the world sticky chunk.
     * 
     * @param stickyChunk
     *            the sticky chunk
     * @param ownerPlugin
     *            the owner plugin
     * @return true, if successful
     */
    public boolean removeWorldStickyChunk(final Chunk stickyChunk, final String ownerPlugin) {
        if ((stickyChunk != null) && (ownerPlugin != null)) {
            if (getWorldStickyChunks().containsKey(stickyChunk)) {
                if (getWorldStickyChunks().get(stickyChunk).containsKey(ownerPlugin)) {
                    if (getWorldStickyChunks().get(stickyChunk).get(ownerPlugin) > 1) {
                        getWorldStickyChunks().get(stickyChunk).put(ownerPlugin, getWorldStickyChunks().get(stickyChunk).get(ownerPlugin) - 1);
                    } else if (getWorldStickyChunks().get(stickyChunk).size() > 1) {
                        getWorldStickyChunks().get(stickyChunk).remove(ownerPlugin);
                    } else {
                        getWorldStickyChunks().remove(stickyChunk);
                    }
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Sets the allow player contact damage.
     * 
     * @param PlayerAllowContactDamage
     *            the new allow player contact damage
     */
    public void setPlayerAllowContactDamage(final boolean PlayerAllowContactDamage) {
        playerAllowContactDamage = PlayerAllowContactDamage;
    }

    /**
     * Sets the allow player damage.
     * 
     * @param playerAllowDamage
     *            the new allow player damage
     */
    public void setPlayerAllowDamage(final boolean playerAllowDamage) {
        this.playerAllowDamage = playerAllowDamage;
    }

    /**
     * Sets the allow player drown.
     * 
     * @param playerAllowDrown
     *            the new allow player drown
     */
    public void setPlayerAllowDrown(final boolean playerAllowDrown) {
        this.playerAllowDrown = playerAllowDrown;
    }

    /**
     * Sets the allow player explosion damage.
     * 
     * @param playerAllowExplosionDamage
     *            the new allow player explosion damage
     */
    public void setPlayerAllowExplosionDamage(final boolean playerAllowExplosionDamage) {
        this.playerAllowExplosionDamage = playerAllowExplosionDamage;
    }

    /**
     * Sets the allow player fall damage.
     * 
     * @param playerAllowFallDamage
     *            the new allow player fall damage
     */
    public void setPlayerAllowFallDamage(final boolean playerAllowFallDamage) {
        this.playerAllowFallDamage = playerAllowFallDamage;
    }

    /**
     * Sets the allow player fire damage.
     * 
     * @param playerAllowFireDamage
     *            the new allow player fire damage
     */
    public void setPlayerAllowFireDamage(final boolean playerAllowFireDamage) {
        this.playerAllowFireDamage = playerAllowFireDamage;
    }

    /**
     * Sets the allow player lava damage.
     * 
     * @param playerAllowLavaDamage
     *            the new allow player lava damage
     */
    public void setPlayerAllowLavaDamage(final boolean playerAllowLavaDamage) {
        this.playerAllowLavaDamage = playerAllowLavaDamage;
    }

    /**
     * Sets the allow player lightning damage.
     * 
     * @param playerAllowLightningDamage
     *            the new allow player lightning damage
     */
    public void setPlayerAllowLightningDamage(final boolean playerAllowLightningDamage) {
        this.playerAllowLightningDamage = playerAllowLightningDamage;
    }

    /**
     * Sets the allow player suffocation.
     * 
     * @param playerAllowSuffocation
     *            the new allow player suffocation
     */
    public void setPlayerAllowSuffocation(final boolean playerAllowSuffocation) {
        this.playerAllowSuffocation = playerAllowSuffocation;
    }

    /**
     * Sets the allow player void damage.
     * 
     * @param playerAllowVoidDamage
     *            the new allow player void damage
     */
    public void setPlayerAllowVoidDamage(final boolean playerAllowVoidDamage) {
        this.playerAllowVoidDamage = playerAllowVoidDamage;
    }

    /**
     * Sets the this world.
     * 
     * @param world the world to set
     */
    public void setWorld(final World world) {
        this.world = world;
    }

    /**
     * Sets the world allow fire.
     * 
     * @param worldAllowFire
     *            the new world allow fire
     */
    public void setWorldAllowFire(final boolean worldAllowFire) {
        this.worldAllowFire = worldAllowFire;
    }

    /**
     * Sets the allow fire spread.
     * 
     * @param allowFireSpread
     *            the new allow fire spread
     */
    public void setWorldAllowFireSpread(final boolean worldAllowFireSpread) {
        this.worldAllowFireSpread = worldAllowFireSpread;
    }

    /**
     * Sets the allow lava fire.
     * 
     * @param allowWorldLavaFire
     *            the new allow lava fire
     */
    public void setWorldAllowLavaFire(final boolean worldAllowLavaFire) {
        this.worldAllowLavaFire = worldAllowLavaFire;
    }

    /**
     * Sets the allow lava spread.
     * 
     * @param allowWorldLavaSpread
     *            the new allow lava spread
     */
    public void setWorldAllowLavaSpread(final boolean worldAllowLavaSpread) {
        this.worldAllowLavaSpread = worldAllowLavaSpread;
    }

    /**
     * Sets the allow lightning fire.
     * 
     * @param allowWorldLightningFire
     *            the new allow lightning fire
     */
    public void setWorldAllowLightningFire(final boolean worldAllowLightningFire) {
        this.worldAllowLightningFire = worldAllowLightningFire;
    }

    /**
     * Sets the allow players to start fires.
     * 
     * @param worldAllowPlayerStartFire
     *            the new allow players to start fires
     */
    public void setWorldAllowPlayerStartFire(final boolean worldAllowPlayerStartFire) {
        this.worldAllowPlayerStartFire = worldAllowPlayerStartFire;
    }

    /**
     * Sets the allow pv p.
     * 
     * @param worldAllowPvP
     *            the new allow pv p
     */
    public void setWorldAllowPvP(final boolean worldAllowPvP) {
        this.worldAllowPvP = worldAllowPvP;
    }

    /**
     * Sets the allow hostiles.
     * 
     * @param allowWorldSpawnHostiles
     *            the allowHostiles to set
     */
    public void setWorldAllowSpawnHostiles(final boolean worldAllowSpawnHostiles) {
        this.worldAllowSpawnHostiles = worldAllowSpawnHostiles;
    }

    /**
     * Sets the allow neutrals.
     * 
     * @param allowWorldSpawnNeutrals
     *            the allowNeutrals to set
     */
    public void setWorldAllowSpawnNeutrals(final boolean worldAllowSpawnNeutrals) {
        this.worldAllowSpawnNeutrals = worldAllowSpawnNeutrals;
    }

    /**
     * Sets the allow water spread.
     * 
     * @param allowWaterSpread
     *            the new allow water spread
     */
    public void setWorldAllowWaterSpread(final boolean worldAllowWaterSpread) {
        this.worldAllowWaterSpread = worldAllowWaterSpread;
    }

    /**
     * Sets the allow thunder.
     * 
     * @param allowThunder
     *            the new allow thunder
     */
    public void setWorldAllowWeatherThunder(final boolean worldAllowWeatherThunder) {
        this.worldAllowWeatherThunder = worldAllowWeatherThunder;
    }

    /**
     * Sets the autoconnect world.
     * 
     * @param autoconnectWorld
     *            the new autoconnect world
     */
    public void setWorldAutoload(final boolean worldAutoload) {
        this.worldAutoload = worldAutoload;
    }

    /**
     * Sets the world custom spawn.
     * 
     * @param worldCustomSpawn
     *            the new world custom spawn
     */
    public void setWorldCustomSpawn(final int[] worldCustomSpawn) {
        this.worldCustomSpawn = worldCustomSpawn.clone();
    }

    /**
     * Sets the world loaded.
     * 
     * @param loaded
     *            the new world loaded
     */
    void setWorldLoaded(final boolean worldLoaded) {
        this.worldLoaded = worldLoaded;
    }

    /**
     * Sets the world name.
     * 
     * @param worldName
     *            the worldName to set
     */
    public void setWorldName(final String worldName) {
        this.worldName = worldName;
    }

    /**
     * Sets the world seed.
     * 
     * @param worldSeed
     *            the new world seed
     */
    public void setWorldSeed(final long worldSeed) {
        this.worldSeed = worldSeed;
    }

    /**
     * Sets the world custom spawn.
     * 
     * @param worldSpawn
     *            the new world custom spawn
     */
    public void setWorldSpawn(final Location worldSpawn) {
        this.worldSpawn = worldSpawn;
    }

    /**
     * Sets the time lock type.
     * 
     * @param timeLockType
     *            the new time lock type
     */
    public void setWorldTimeLockType(final TimeLockType worldTimeLockType) {
        switch (worldTimeLockType) {
            case DAY:
            case NIGHT:
                worldTimeLock = true;
                this.worldTimeLockType = worldTimeLockType;
                break;
            case NONE:
                worldTimeLock = false;
                this.worldTimeLockType = worldTimeLockType;
                break;
            default:
                break;
        }
    }

    /**
     * Sets the normal world.
     * 
     * @param worldTypeNormal true or false
     */
    public void setWorldTypeNormal(final boolean worldTypeNormal) {
        this.worldTypeNormal = worldTypeNormal;
    }    
    
    /**
     * Sets the nether world.
     * 
     * @param worldTypeNether true or false
     */
    public void setWorldTypeNether(final boolean worldTypeNether) {
        this.worldTypeNether = worldTypeNether;
    }
    
    /**
     * Sets the skyland world.
     * 
     * @param worldTypeSkylands true or false
     */
    public void setWorldTypeSkylands(boolean worldTypeSkylands) {
        this.worldTypeSkylands = worldTypeSkylands;
    }

    /**
     * Sets the weather lock.
     * 
     * @param worldWeatherLock
     *            the new weather lock
     */
    public void setWorldWeatherLock(final boolean worldWeatherLock) {
        this.worldWeatherLock = worldWeatherLock;
    }

    /**
     * Sets the weather lock type.
     * 
     * @param worldWeatherLockType
     *            the new weather lock type
     */
    public void setWorldWeatherLockType(final WeatherLockType worldWeatherLockType) {
        switch (worldWeatherLockType) {
            case CLEAR:
            case RAIN:
                worldWeatherLock = true;
                worldAllowWeatherThunder = false;
                this.worldWeatherLockType = worldWeatherLockType;
                break;
            case STORM:
                worldWeatherLock = true;
                worldAllowWeatherThunder = true;
                this.worldWeatherLockType = worldWeatherLockType;
                break;
            case NONE:
                worldWeatherLock = false;
                worldAllowWeatherThunder = true;
                this.worldWeatherLockType = worldWeatherLockType;
                break;
            default:
                break;
        }
    }
}
