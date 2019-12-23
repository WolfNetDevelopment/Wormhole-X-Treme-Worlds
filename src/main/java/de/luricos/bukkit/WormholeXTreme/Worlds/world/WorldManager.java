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

import de.luricos.bukkit.WormholeXTreme.Worlds.WormholeXTremeWorlds;
import de.luricos.bukkit.WormholeXTreme.Worlds.config.ConfigManager;
import de.luricos.bukkit.WormholeXTreme.Worlds.exceptions.WorldsAutoloadException;
import de.luricos.bukkit.WormholeXTreme.Worlds.utils.WXLogger;
import de.luricos.bukkit.WormholeXTreme.Worlds.config.XMLConfig;
import de.luricos.bukkit.WormholeXTreme.Worlds.exceptions.WorldsDuplicateUUIDException;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.WorldCreator;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Animals;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.WaterMob;
import org.bukkit.entity.Flying;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;

/**
 * The Class WorldManager.
 * 
 * @author alron
 */
public class WorldManager {

    /** The world list. */
    private final static ConcurrentHashMap<String, WormholeWorld> worldList = new ConcurrentHashMap<String, WormholeWorld>();
    /** The Constant thisPlugin. */
    private final static WormholeXTremeWorlds thisPlugin = WormholeXTremeWorlds.getThisPlugin();

    /**
     * Adds the world.
     * 
     * @param wormholeWorld
     *            the world
     * @return true, if successful
     */
    public static boolean addWorld(final WormholeWorld wormholeWorld) {
        if (wormholeWorld != null) {
            try {
                worldList.put(wormholeWorld.getWorldName(), wormholeWorld);
                return true;
            } catch (final NullPointerException e) {
                return false;
            }
        }
        return false;
    }

    /**
     * Check non safe type id.
     * 
     * @param material
     *            the type id
     * @return true, if successful
     */
    private static boolean checkNonSafeMaterial(final Material material) {
        if ((material == Material.AIR) || (material == Material.LAVA)){
            return false;
        }
        WXLogger.prettyLog(Level.FINE, false, "Did not find material to be unsafe:" + material);
        return true;
    }

    /**
     * Check safe block.
     * 
     * @param safeBlock
     *            the safe block
     * @return true, if successful
     */
    private static boolean checkSafeBlock(final Block safeBlock) {
        if (safeBlock != null) {
            Material material = safeBlock.getType();
            return checkSafeMaterial(material);
        }
        return false;
    }

    /**
     * Check safe block above.
     * 
     * @param safeBlock
     *            the safe block
     * @return true, if successful
     */
    private static boolean checkSafeBlockAbove(final Block safeBlock) {
        if (safeBlock != null) {
            Material material = safeBlock.getRelative(BlockFace.UP).getType();
            return checkSafeMaterial(material);
        }
        return false;
    }

    /**
     * Check safe block below.
     * 
     * @param safeBlock
     *            the safe block
     * @return true, if successful
     */
    private static boolean checkSafeBlockBelow(final Block safeBlock) {
        if (safeBlock != null) {
//            final int typeId = safeBlock.getRelative(BlockFace.DOWN).getTypeId();
            return checkNonSafeMaterial(safeBlock.getType());
        }
        return false;
    }

    /**
     * Check safe teleport destination.
     * 
     * @param destination
     *            the destination
     * @return true, if successful
     */
    private static boolean checkSafeTeleportDestination(final Location destination) {
        if (destination != null) {
            final Block safeBlock = destination.getBlock();
            if (checkSafeBlock(safeBlock) && checkSafeBlockAbove(safeBlock) && checkSafeBlockBelow(safeBlock)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check safe material.
     * 
     * @param material
     *            the material
     * @return true, if successful
     */
    private static boolean checkSafeMaterial(Material material) {
        //AIR,WATER,STATIONARY_WATER,RAILS
        return (material.equals(Material.AIR)) || (material.equals(Material.WATER)) || (material.equals(Material.RAIL));
    }

    /**
     * Check timelock worlds.
     */
    public static void checkTimelockWorlds() {
        if (ConfigManager.getServerOptionTimelock()) {
            for (final String key : worldList.keySet()) {
                if (key != null) {
                    final WormholeWorld wormholeWorld = worldList.get(key);
                    if ((wormholeWorld != null) && wormholeWorld.isWorldLoaded() && wormholeWorld.isWorldTimeLock()) {
                        final long worldRelativeTime = wormholeWorld.getWorld().getTime() % 24000;
                        if ((worldRelativeTime > 11800) && (wormholeWorld.getWorldTimeLockType() == TimeLockType.DAY)) {
                            WXLogger.prettyLog(Level.FINE, false, "Set world: " + key + " New time: 0" + " Old Time: " + worldRelativeTime);
                            wormholeWorld.getWorld().setTime(0);
                        } else if (((worldRelativeTime < 13500) || (worldRelativeTime > 21800)) && (wormholeWorld.getWorldTimeLockType() == TimeLockType.NIGHT)) {
                            WXLogger.prettyLog(Level.FINE, false, "Set world: " + key + " New time: 13700" + " Old Time: " + worldRelativeTime);
                            wormholeWorld.getWorld().setTime(13700);
                        }
                    }
                }
            }
        }
    }

    /**
     * Clear world creatures.
     * 
     * @param wormholeWorld
     *            the wormhole world
     * @return the int number of creatures cleared
     */
    public static int clearWorldCreatures(final WormholeWorld wormholeWorld) {
        int cleared = 0;
        if (!wormholeWorld.isWorldAllowSpawnHostiles() || !wormholeWorld.isWorldAllowSpawnNeutrals()) {
            final List<LivingEntity> entityList = wormholeWorld.getWorld().getLivingEntities();

            for (final LivingEntity entity : entityList) {
                if ((!wormholeWorld.isWorldAllowSpawnHostiles() && ((entity instanceof Monster) || (entity instanceof Flying))) || (!wormholeWorld.isWorldAllowSpawnNeutrals() && ((entity instanceof Animals) || (entity instanceof WaterMob)))) {
                    WXLogger.prettyLog(Level.FINE, false, "Removed entity: " + entity);
                    entity.remove();
                    cleared++;
                }
            }

        }
        return cleared;
    }

    private static void createDefaultWorld() {
        final WormholeWorld wormholeWorld = new WormholeWorld();
        final World world = WormholeXTremeWorlds.getThisPlugin().getServer().getWorlds().get(0);
        
        wormholeWorld.setWorldName(world.getName());

        // set worldType
        switch (world.getEnvironment()) {
            case NORMAL:
                wormholeWorld.setWorldTypeNormal(true);
                break;
            case NETHER:
                wormholeWorld.setWorldTypeNether(true);
                break;
            case THE_END:
                wormholeWorld.setWorldTypeTheEnd(true);
                break;
        }
        
        createWormholeWorld(wormholeWorld);
        WXLogger.prettyLog(Level.INFO, false, "Added default world as wormhole world.");
    }

    /**
     * Creates the world.
     * 
     * @param wormholeWorld
     *            the wormhole world
     * @return true, if successful
     */
    public static boolean createWormholeWorld(final WormholeWorld wormholeWorld) {
        if ((wormholeWorld == null) && isWormholeWorld(wormholeWorld.getWorldName()))
            return false;
        
        final String worldName = wormholeWorld.getWorldName();
        final Environment worldEnvironment = wormholeWorld.getWorldEnvironment();

        if ("".equals(worldName)) {
            return false;
        }

        try {
            if (thisPlugin.getServer().getWorld(worldName) == null) {
                World world = ((wormholeWorld.getWorldSeed() == 0) 
                        ? thisPlugin.getServer().createWorld(new WorldCreator(wormholeWorld.getWorldName()).environment(worldEnvironment))
                        : thisPlugin.getServer().createWorld(new WorldCreator(wormholeWorld.getWorldName()).environment(worldEnvironment).seed(wormholeWorld.getWorldSeed()))
                );

                if (world == null)
                    throw new WorldsDuplicateUUIDException("World '" + wormholeWorld.getWorldName() + "' is a duplicate of another world and has been prevented from loading. Please delete the uid.dat file from " + wormholeWorld.getWorldName() + "'s world directory if you want to be able to load the duplicate world.");
                
                wormholeWorld.setWorld(world);

                if (wormholeWorld.getWorldSeed() == 0)
                    wormholeWorld.setWorldSeed(wormholeWorld.getWorld().getSeed());

                wormholeWorld.getWorld().save();
            } else {
                wormholeWorld.setWorld(thisPlugin.getServer().getWorld(worldName));
                wormholeWorld.setWorldSeed(wormholeWorld.getWorld().getSeed());
            }

            wormholeWorld.setWorldSpawn(wormholeWorld.isWorldTypeNether() || wormholeWorld.isWorldTypeTheEnd()
                    ? findSafeSpawn(wormholeWorld.getWorld().getSpawnLocation(), 13, 13)
                    : wormholeWorld.getWorld().getSpawnLocation());

            final int tsX = wormholeWorld.getWorldSpawn().getBlockX();
            final int tsY = wormholeWorld.getWorldSpawn().getBlockY();
            final int tsZ = wormholeWorld.getWorldSpawn().getBlockZ();

            if (!wormholeWorld.getWorldSpawn().equals(wormholeWorld.getWorld().getSpawnLocation())) {
                wormholeWorld.getWorld().setSpawnLocation(tsX, tsY, tsZ);
            }
            
            final int[] tempSpawn = {
                tsX, tsY, tsZ
            };
            
            wormholeWorld.setWorldCustomSpawn(tempSpawn);
            clearWorldCreatures(wormholeWorld);
            setWorldWeather(wormholeWorld);
            setWorldPvP(wormholeWorld);

            wormholeWorld.setWorldLoaded(true);
            return addWorld(wormholeWorld);
        } catch (WorldsDuplicateUUIDException e) {
            WXLogger.prettyLog(Level.SEVERE, true, e.getMessage());
        }
        
        return false;
    }

    /**
     * Find safe spawn.
     * 
     * @param spawnLocation
     *            the wormhole world
     * @return the location
     */
    private static Location findSafeSpawn(final Location spawnLocation, final int yDepth, final int xzDepth) {
        if (spawnLocation != null) {
            final int worldSpawnY = spawnLocation.getBlockY();
            final int worldSpawnX = spawnLocation.getBlockX();
            final int worldSpawnZ = spawnLocation.getBlockZ();
            final World world = spawnLocation.getWorld();
            final int xzDepthOdd = oddNumberMaker(xzDepth);
            final int yDepthOdd = oddNumberMaker(yDepth);
            Location safeSpawn = findSafeSpawnFromYXZ(world, worldSpawnY, worldSpawnX, worldSpawnZ, yDepthOdd, xzDepthOdd);
            if (safeSpawn != null) {
                WXLogger.prettyLog(Level.FINE, false, "Found safe spawn in pass 0/0.");
                return safeSpawn;
            } else {
                safeSpawn = findSafeSpawnFromYXZ(world, worldSpawnY, worldSpawnX + xzDepthOdd, worldSpawnZ, yDepthOdd, xzDepthOdd);
                if (safeSpawn != null) {
                    WXLogger.prettyLog(Level.FINE, false, "Found safe spawn in pass +" + xzDepthOdd + "/0.");
                    return safeSpawn;
                } else {
                    safeSpawn = findSafeSpawnFromYXZ(world, worldSpawnY, worldSpawnX + xzDepthOdd, worldSpawnZ + xzDepthOdd, yDepthOdd, xzDepthOdd);
                    if (safeSpawn != null) {
                        WXLogger.prettyLog(Level.FINE, false, "Found safe spawn in pass +" + xzDepthOdd + "/+" + xzDepthOdd + ".");
                        return safeSpawn;
                    } else {
                        safeSpawn = findSafeSpawnFromYXZ(world, worldSpawnY, worldSpawnX, worldSpawnZ + xzDepthOdd, yDepthOdd, xzDepthOdd);
                        if (safeSpawn != null) {
                            WXLogger.prettyLog(Level.FINE, false, "Found safe spawn in pass 0/+" + xzDepthOdd + ".");
                            return safeSpawn;
                        } else {
                            safeSpawn = findSafeSpawnFromYXZ(world, worldSpawnY, worldSpawnX - xzDepthOdd, worldSpawnZ + xzDepthOdd, yDepthOdd, xzDepthOdd);
                            if (safeSpawn != null) {
                                WXLogger.prettyLog(Level.FINE, false, "Found safe spawn in pass -" + xzDepthOdd + "/+" + xzDepthOdd + ".");
                                return safeSpawn;
                            } else {
                                safeSpawn = findSafeSpawnFromYXZ(world, worldSpawnY, worldSpawnX - xzDepthOdd, worldSpawnZ, yDepthOdd, xzDepthOdd);
                                if (safeSpawn != null) {
                                    WXLogger.prettyLog(Level.FINE, false, "Found safe spawn in pass -" + xzDepthOdd + "/0.");
                                    return safeSpawn;
                                } else {
                                    safeSpawn = findSafeSpawnFromYXZ(world, worldSpawnY, worldSpawnX - xzDepthOdd, worldSpawnZ - xzDepthOdd, yDepthOdd, xzDepthOdd);
                                    if (safeSpawn != null) {
                                        WXLogger.prettyLog(Level.FINE, false, "Found safe spawn in pass -" + xzDepthOdd + "/-" + xzDepthOdd + ".");
                                        return safeSpawn;
                                    } else {
                                        safeSpawn = findSafeSpawnFromYXZ(world, worldSpawnY, worldSpawnX, worldSpawnZ - xzDepthOdd, yDepthOdd, xzDepthOdd);
                                        if (safeSpawn != null) {
                                            WXLogger.prettyLog(Level.FINE, false, "Found safe spawn in pass 0/-" + xzDepthOdd + ".");
                                            return safeSpawn;
                                        } else {
                                            safeSpawn = findSafeSpawnFromYXZ(world, worldSpawnY, worldSpawnX + xzDepthOdd, worldSpawnZ - xzDepthOdd, yDepthOdd, xzDepthOdd);
                                            if (safeSpawn != null) {
                                                WXLogger.prettyLog(Level.FINE, false, "Found safe spawn in pass +" + xzDepthOdd + "/-" + xzDepthOdd + ".");
                                                return safeSpawn;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            WXLogger.prettyLog(Level.FINE, false, ((xzDepthOdd * xzDepthOdd) * yDepthOdd) * 9 + " blocks later. No safe spawn in sight.");
            return spawnLocation;
        }
        WXLogger.prettyLog(Level.FINE, false, "Returned null value for safe spawn!");
        return null;
    }

    /**
     * Find safe spawn from yxz.
     * 
     * @param world
     *            the world
     * @param worldSpawnY
     *            the world spawn y
     * @param worldSpawnX
     *            the world spawn x
     * @param worldSpawnZ
     *            the world spawn z
     * @param ySize
     *            the yAxis depth
     * @param xzSize
     *            the xzAxis depth
     * @return the location
     */
    private static Location findSafeSpawnFromYXZ(final World world, final int worldSpawnY, final int worldSpawnX, final int worldSpawnZ, final int ySize, final int xzSize) {
        final int yAxisDepth = oddNumberMaker(ySize);
        final int yAxisSmall = ((yAxisDepth - 1) / 2);
        final int yAxisLarge = yAxisSmall + 1;
        final int xzAxisDepth = oddNumberMaker(xzSize);
        final int xzAxisSmall = ((xzAxisDepth - 1) / 2);
        final int xzAxisLarge = xzAxisSmall + 1;
        final HashMap<Integer, Block[][]> blockYaxisPlane = new HashMap<Integer, Block[][]>(yAxisDepth * 2);
        int iYY = 0;
        if ((worldSpawnY <= 127 - yAxisSmall) && (worldSpawnY >= yAxisLarge)) {
            for (int iY = worldSpawnY - yAxisSmall; iY < worldSpawnY + yAxisLarge; iY++) {
                blockYaxisPlane.put(iYY, populateYaxisPlane(world, worldSpawnX, worldSpawnZ, iY, iYY, xzAxisDepth, xzAxisSmall, xzAxisLarge));
                iYY++;
            }
        } else if (worldSpawnY <= yAxisSmall) {
            for (int iY = worldSpawnY; iY < worldSpawnY + yAxisDepth; iY++) {
                blockYaxisPlane.put(iYY, populateYaxisPlane(world, worldSpawnX, worldSpawnZ, iY, iYY, xzAxisDepth, xzAxisSmall, xzAxisLarge));
                iYY++;
            }
        } else if (worldSpawnY >= 127 - yAxisLarge) {
            for (int iY = worldSpawnY - yAxisDepth; iY < worldSpawnY; iY++) {
                blockYaxisPlane.put(iYY, populateYaxisPlane(world, worldSpawnX, worldSpawnZ, iY, iYY, xzAxisDepth, xzAxisSmall, xzAxisLarge));
                iYY++;
            }
        }
        for (int y = 0; y < yAxisDepth; y++) {
            final Block[][] tmpBlockArr = blockYaxisPlane.get(y);
            for (int x = 0; x < xzAxisDepth; x++) {
                for (int z = 0; z < xzAxisDepth; z++) {
                    final Block tmpBlock = tmpBlockArr[x][z];
                    if (tmpBlock != null) {
                        Material material = tmpBlock.getType();
                        if (checkSafeMaterial(material) && checkSafeBlockAbove(tmpBlock) && checkSafeBlockBelow(tmpBlock)) {
                            blockYaxisPlane.clear();
                            return new Location(tmpBlock.getWorld(), tmpBlock.getX() + 0.5, tmpBlock.getY(), tmpBlock.getZ() + 0.5);
                        }
                    }
                }
            }
        }
        blockYaxisPlane.clear();
        return null;
    }

    /**
     * Gets the all world names.
     * 
     * @return the all world names
     */
    public static String[] getAllWorldNames() {
        return worldList.keySet().toArray(new String[worldList.size()]);
    }

    /**
     * Gets the all worlds.
     * 
     * @return the all worlds
     */
    public static WormholeWorld[] getAllWorlds() {
        return worldList.values().toArray(new WormholeWorld[worldList.size()]);
    }

    /**
     * Gets the safe spawn location.
     * 
     * @param wormholeWorld
     *            the wormhole world
     * @param player
     *            the player
     * @return the safe teleport location
     */
    public static Location getSafeSpawnLocation(final WormholeWorld wormholeWorld, final Player player) {
        return (
                wormholeWorld.isWorldTypeNether() || wormholeWorld.isWorldTypeTheEnd()
                ? (
                    WorldManager.checkSafeTeleportDestination(wormholeWorld.getWorldSpawn())
                    ? new Location(wormholeWorld.getWorld(), wormholeWorld.getWorldSpawn().getBlockX() + 0.5, wormholeWorld.getWorldSpawn().getBlockY(), wormholeWorld.getWorldSpawn().getBlockZ() + 0.5, player.getLocation().getYaw(), player.getLocation().getPitch())
                    : WorldManager.findSafeSpawn(wormholeWorld.getWorldSpawn(), 3, 3)
                )
                : (
                    WorldManager.checkSafeTeleportDestination(wormholeWorld.getWorldSpawn())
                    ? new Location(wormholeWorld.getWorld(), wormholeWorld.getWorldSpawn().getBlockX() + 0.5, wormholeWorld.getWorldSpawn().getBlockY(), wormholeWorld.getWorldSpawn().getBlockZ() + 0.5, player.getLocation().getYaw(), player.getLocation().getPitch())
                    : new Location(wormholeWorld.getWorld(), wormholeWorld.getWorldSpawn().getBlockX() + 0.5, wormholeWorld.getWorld().getHighestBlockYAt(wormholeWorld.getWorldSpawn()), wormholeWorld.getWorldSpawn().getBlockZ() + 0.5, player.getLocation().getYaw(), player.getLocation().getPitch())
                  )
                );
        
    }

    /**
     * Gets the world from player.
     * 
     * @param player
     *            the player
     * @return the world from player
     */
    public static WormholeWorld getWorldFromPlayer(final Player player) {
        if (player != null) {
            return getWormholeWorld(player.getWorld().getName());
        } else {
            return null;
        }
    }

    /**
     * Gets the wormholeWorld.
     * 
     * @param worldName
     *            the world name
     * @return the wormholeWorld
     */
    public static WormholeWorld getWormholeWorld(final String worldName) {
        return worldList.get(worldName);
    }

    /**
     * Gets the wormhole world.
     * 
     * @param world
     *            the world
     * @return the wormhole world
     */
    public static WormholeWorld getWormholeWorld(final World world) {
        return worldList.get(world.getName());
    }

    /**
     * Checks if is wormhole world.
     * 
     * @param worldName
     *            the world name
     * @return true, if is wormhole world
     */
    public static boolean isWormholeWorld(final String worldName) {
        return (worldName != null) && worldList.containsKey(worldName);
    }

    /**
     * Checks if is wormhole world.
     * 
     * @param world
     *            the world
     * @return true, if is wormhole world
     */
    public static boolean isWormholeWorld(final World world) {
        return world != null && worldList.containsKey(world.getName());
    }

    /**
     * Load autoconnect worlds.
     * 
     * @return the int number of worlds loaded.
     */
    public static int loadAutoloadWorlds() {
        int loaded = 0;
        if (!(getAllWorlds().length > 0)) {
            createDefaultWorld();
            loaded++;
        }
        
        for (final WormholeWorld wormholeWorld : getAllWorlds()) {
            if (wormholeWorld.isWorldAutoload() && (loadWorld(wormholeWorld)))
                loaded++;
        }
        
        return loaded;
    }

    /**
     * Connect world.
     * 
     * @param wormholeWorld the wormhole world
     * @return true, if successful
     */
    public static boolean loadWorld(final WormholeWorld wormholeWorld) {
        try {
            if ((wormholeWorld.getWorldName() == null) || ("".equals(wormholeWorld.getWorldName()))) {
                throw new WorldsAutoloadException("Worldname was null or empty.");
            }
            
            if (wormholeWorld.getWorldEnvironment() == null) {
                throw new WorldsAutoloadException("Environment for world '" + wormholeWorld.getWorldName() + "' was undefined. Please check config syntax for this world.");
            }           
            
            World world = ((wormholeWorld.getWorldSeed() == 0)
                ? thisPlugin.getServer().createWorld(new WorldCreator(wormholeWorld.getWorldName()).environment(wormholeWorld.getWorldEnvironment()))
                : thisPlugin.getServer().createWorld(new WorldCreator(wormholeWorld.getWorldName()).environment(wormholeWorld.getWorldEnvironment()).seed(wormholeWorld.getWorldSeed()))
            );
            
            if (world == null)
                throw new WorldsDuplicateUUIDException("World '" + wormholeWorld.getWorldName() + "' is a duplicate of another world and has been prevented from loading. Please delete the uid.dat file from " + wormholeWorld.getWorldName() + "'s world directory if you want to be able to load the duplicate world.");
            
            wormholeWorld.setWorld(world);

            wormholeWorld.getWorld().setSpawnLocation(wormholeWorld.getWorldCustomSpawn()[0], wormholeWorld.getWorldCustomSpawn()[1], wormholeWorld.getWorldCustomSpawn()[2]);
            wormholeWorld.setWorldSpawn(wormholeWorld.getWorld().getSpawnLocation());
            if (wormholeWorld.getWorldSeed() == 0) {
                wormholeWorld.setWorldSeed(wormholeWorld.getWorld().getSeed());
            }

            wormholeWorld.setWorldLoaded(true);

            if (addWorld(wormholeWorld)) {
                final int c = clearWorldCreatures(wormholeWorld);

                if (c > 0) {
                    WXLogger.prettyLog(Level.INFO, false, "Cleared \"" + c + "\" creature entities on world \"" + wormholeWorld.getWorldName() + "\"");
                }

                setWorldWeather(wormholeWorld);
                setWorldPvP(wormholeWorld);
                
                return true;
            }
        } catch (WorldsAutoloadException e) {
            WXLogger.prettyLog(Level.SEVERE, true, "WorldsAutoloadException: " + e.getMessage());
        } catch (WorldsDuplicateUUIDException e) {
            WXLogger.prettyLog(Level.SEVERE, true, "WorldsDuplicateUUIDException: " + e.getMessage());
        }
        
        return false;
    }

    /**
     * Odd number maker. Takes numbers 3 or larger and makes sure they are odd.
     * 
     * @param number the number
     * @return the odd int
     */
    private static int oddNumberMaker(final int number) {
        return ((number % 2 == 0) && (number > 3)) ? number - 1 : (number > 2) ? number : 3;
    }

    /**
     * Populate yaxis plane.
     * 
     * @param world the world
     * @param worldSpawnX the world spawn x
     * @param worldSpawnZ the world spawn z
     * @param iY the i y
     * @param iYY the i yy
     * @param xzAxisDepth the xzAxis depth
     * @param xzAxisSmall the xzAxis small half depth
     * @param xzAxisLarge the xzAxis large half depth
     * @return the block[][]
     */
    private static Block[][] populateYaxisPlane(final World world, final int worldSpawnX, final int worldSpawnZ, final int iY, final int iYY, final int xzAxisDepth, final int xzAxisSmall, final int xzAxisLarge) {
        final Block[][] xzAxis = new Block[xzAxisDepth][xzAxisDepth];
        int iXX = 0;
        for (int iX = worldSpawnX - xzAxisSmall; iX < worldSpawnX + xzAxisLarge; iX++) {
            int iZZ = 0;
            for (int iZ = worldSpawnZ - xzAxisSmall; iZ < worldSpawnZ + xzAxisLarge; iZ++) {
                xzAxis[iXX][iZZ] = world.getBlockAt(iX, iY, iZ);
                WXLogger.prettyLog(Level.FINEST, false, "Y/YY: " + iY + "/" + iYY + " X/XX: " + iX + "/" + iXX + " Z/ZZ: " + iZ + "/" + iZZ + " Block: " + xzAxis[iXX][iZZ].toString());
                iZZ++;
            }
            iXX++;
        }
        return xzAxis;
    }

    /**
     * Removes the world.
     * 
     * @param world the world
     */
    public static void removeWorld(final WormholeWorld world) {
        if (world != null) {
            XMLConfig.deleteXmlWorldConfig(world.getWorldName());
            worldList.remove(world.getWorldName());
        }
    }

    /**
     * Sets the world pv p.
     * 
     * @param wormholeWorld the new world pv p
     */
    public static void setWorldPvP(final WormholeWorld wormholeWorld) {
        if (wormholeWorld.isWorldLoaded()) {
            wormholeWorld.getWorld().setPVP(wormholeWorld.isWorldAllowPvP());
        }
    }

    /**
     * Sets the world weather.
     * 
     * @param wormholeWorld the new world weather
     */
    public static void setWorldWeather(final WormholeWorld wormholeWorld) {
        if (wormholeWorld.isWorldWeatherLock()) {
            final World world = wormholeWorld.getWorld();
            switch (wormholeWorld.getWorldWeatherLockType()) {
                case CLEAR:
                    world.setStorm(false);
                    world.setThundering(false);
                    break;
                case RAIN:
                    world.setStorm(true);
                    world.setThundering(false);
                    break;
                case STORM:
                    world.setStorm(true);
                    world.setThundering(true);
                    break;
                default:
                    break;
            }
        }
    }
}
