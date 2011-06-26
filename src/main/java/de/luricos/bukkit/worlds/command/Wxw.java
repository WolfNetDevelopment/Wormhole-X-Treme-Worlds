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
package de.luricos.bukkit.worlds.command;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.luricos.bukkit.worlds.WormholeXTremeWorlds;
import de.luricos.bukkit.worlds.config.ResponseType;
import de.luricos.bukkit.worlds.permissions.PermissionType;
import de.luricos.bukkit.worlds.scheduler.ScheduleAction;
import de.luricos.bukkit.worlds.scheduler.ScheduleAction.ActionType;
import de.luricos.bukkit.worlds.world.TimeLockType;
import de.luricos.bukkit.worlds.world.WeatherLockType;
import de.luricos.bukkit.worlds.world.WorldManager;
import de.luricos.bukkit.worlds.world.WormholeWorld;

/**
 * The wxw command class.
 * 
 * @author alron
 */
class Wxw implements CommandExecutor {

    /** The Constant thisPlugin. */
    private static final WormholeXTremeWorlds thisPlugin = WormholeXTremeWorlds.getThisPlugin();

    /**
     * Colorize boolean.
     * 
     * @param b
     *            the b
     * @return the string
     */
    private static String colorizeBoolean(final boolean b) {
        if (b) {
            return "\u00A72true";
        }
        else {
            return "\u00A74false";
        }
    }

    /**
     * Do create world.
     * 
     * @param sender
     *            the sender
     * @param args
     *            the args
     * @return true, if successful
     */
    private static boolean doCreateWorld(final CommandSender sender, final String[] args) {
        if ( !CommandUtilities.playerCheck(sender) || PermissionType.CREATE.checkPermission((Player) sender)) {
            if ((args != null) && (args.length >= 1)) {
                final WormholeWorld wormholeWorld = new WormholeWorld();
                for (final String arg : args) {
                    final String atlc = arg.toLowerCase();
                    if (atlc.startsWith("-na")) {
                        if (atlc.contains("|")) {
                            wormholeWorld.setWorldName(arg.split("\\|")[1].trim());
                        }
                        else {
                            sender.sendMessage(ResponseType.ERROR_COMMAND_REQUIRES_WORLDNAME.toString() + "-name");
                            return true;
                        }
                    }
                    else if (atlc.startsWith("-pl")) {
                        if (atlc.contains("|")) {
                            for (final String playerOption : arg.split("\\|")[1].contains(",")
                                ? arg.split("\\|")[1].split(",") : new String[]{
                                    arg.split("\\|")[1]
                                }) {
                                if (playerOption.equalsIgnoreCase("contact")) {
                                    wormholeWorld.setPlayerAllowContactDamage(false);
                                }
                                else if (playerOption.equalsIgnoreCase("all")) {
                                    wormholeWorld.setPlayerAllowDamage(false);
                                }
                                else if (playerOption.equalsIgnoreCase("drown")) {
                                    wormholeWorld.setPlayerAllowDrown(false);
                                }
                                else if (playerOption.equalsIgnoreCase("explosion")) {
                                    wormholeWorld.setPlayerAllowExplosionDamage(false);
                                }
                                else if (playerOption.equalsIgnoreCase("fall")) {
                                    wormholeWorld.setPlayerAllowFallDamage(false);
                                }
                                else if (playerOption.equalsIgnoreCase("fire")) {
                                    wormholeWorld.setPlayerAllowFireDamage(false);
                                }
                                else if (playerOption.equalsIgnoreCase("lava")) {
                                    wormholeWorld.setPlayerAllowLavaDamage(false);
                                }
                                else if (playerOption.equalsIgnoreCase("lightning")) {
                                    wormholeWorld.setPlayerAllowLightningDamage(false);
                                }
                                else if (playerOption.equalsIgnoreCase("suffocation")) {
                                    wormholeWorld.setPlayerAllowSuffocation(false);
                                }
                                else if (playerOption.equalsIgnoreCase("void")) {
                                    wormholeWorld.setPlayerAllowVoidDamage(false);
                                }
                                else {
                                    sender.sendMessage(ResponseType.ERROR_ARG_NOT_VALID.toString() + "-player");
                                    return true;
                                }
                            }
                        }
                        else {
                            sender.sendMessage(ResponseType.ERROR_OPTION_REQUIRES_ARGS.toString() + "-player");
                            return true;
                        }
                    }
                    else if (atlc.startsWith("-wo")) {
                        if (atlc.contains("|")) {
                            for (final String worldOption : arg.split("\\|")[1].contains(",")
                                ? arg.split("\\|")[1].split(",") : new String[]{
                                    arg.split("\\|")[1]
                                }) {
                                if (worldOption.equalsIgnoreCase("fire")) {
                                    wormholeWorld.setWorldAllowFire(false);
                                }
                                else if (worldOption.equalsIgnoreCase("firespread")) {
                                    wormholeWorld.setWorldAllowFireSpread(false);
                                }
                                else if (worldOption.equalsIgnoreCase("lavafire")) {
                                    wormholeWorld.setWorldAllowLavaFire(false);
                                }
                                else if (worldOption.equalsIgnoreCase("lavaspread")) {
                                    wormholeWorld.setWorldAllowLavaSpread(false);
                                }
                                else if (worldOption.equalsIgnoreCase("lightningfire")) {
                                    wormholeWorld.setWorldAllowLightningFire(false);
                                }
                                else if (worldOption.equalsIgnoreCase("pvp")) {
                                    wormholeWorld.setWorldAllowPvP(false);
                                }
                                else if (worldOption.equalsIgnoreCase("firestart")) {
                                    wormholeWorld.setWorldAllowPlayerStartFire(false);
                                }
                                else if (worldOption.equalsIgnoreCase("hostiles")) {
                                    wormholeWorld.setWorldAllowSpawnHostiles(false);
                                }
                                else if (worldOption.equalsIgnoreCase("neutrals")) {
                                    wormholeWorld.setWorldAllowSpawnNeutrals(false);
                                }
                                else if (worldOption.equalsIgnoreCase("waterspread")) {
                                    wormholeWorld.setWorldAllowWaterSpread(false);
                                }
                                else if (worldOption.equalsIgnoreCase("autoload")) {
                                    wormholeWorld.setWorldAutoload(false);
                                }
                                else if (worldOption.equalsIgnoreCase("nether")) {
                                    wormholeWorld.setWorldTypeNether(true);
                                }
                                else {
                                    sender.sendMessage(ResponseType.ERROR_ARG_NOT_VALID.toString() + "-world");
                                    return true;
                                }
                            }
                        }
                        else {
                            sender.sendMessage(ResponseType.ERROR_OPTION_REQUIRES_ARGS.toString() + "-world");
                            return true;
                        }
                    }
                    else if (atlc.startsWith("-ti")) {
                        if (atlc.contains("|")) {
                            if ( !atlc.contains(",")) {
                                if (arg.split("\\|")[1].equalsIgnoreCase("day")) {
                                    wormholeWorld.setWorldTimeLockType(TimeLockType.DAY);
                                }
                                else if (arg.split("\\|")[1].equalsIgnoreCase("night")) {
                                    wormholeWorld.setWorldTimeLockType(TimeLockType.NIGHT);
                                }
                                else {
                                    sender.sendMessage(ResponseType.ERROR_ARG_NOT_VALID.toString() + "-time");
                                    return true;
                                }
                            }
                            else {
                                sender.sendMessage(ResponseType.ERROR_OPTION_REQUIRES_ONE_ARG.toString() + "-time");
                                return true;
                            }
                        }
                        else {
                            sender.sendMessage(ResponseType.ERROR_OPTION_REQUIRES_ARGS.toString() + "-time");
                            return true;
                        }
                    }
                    else if (atlc.startsWith("-we")) {
                        if (atlc.contains("|")) {
                            if ( !atlc.contains(",")) {
                                if (arg.split("\\|")[1].equalsIgnoreCase("clear")) {
                                    wormholeWorld.setWorldWeatherLockType(WeatherLockType.CLEAR);
                                }
                                else if (arg.split("\\|")[1].equalsIgnoreCase("rain")) {
                                    wormholeWorld.setWorldWeatherLockType(WeatherLockType.RAIN);
                                }
                                else if (arg.split("\\|")[1].equalsIgnoreCase("rain")) {
                                    wormholeWorld.setWorldWeatherLockType(WeatherLockType.STORM);
                                }
                                else {
                                    sender.sendMessage(ResponseType.ERROR_ARG_NOT_VALID.toString() + "-weather");
                                    return true;
                                }
                            }
                            else {
                                sender.sendMessage(ResponseType.ERROR_OPTION_REQUIRES_ONE_ARG.toString() + "-weather");
                                return true;
                            }
                        }
                        else {
                            sender.sendMessage(ResponseType.ERROR_OPTION_REQUIRES_ARGS.toString() + "-weather");
                            return true;
                        }
                    }
                    else if (atlc.startsWith("-se")) {
                        if (atlc.contains("|")) {
                            try {
                                wormholeWorld.setWorldSeed(Long.parseLong(arg.split("\\|")[1].trim()));
                            }
                            catch (final NumberFormatException e) {
                                wormholeWorld.setWorldSeed(Long.valueOf(arg.split("\\|")[1].trim().hashCode()));
                            }
                        }
                        else {
                            sender.sendMessage(ResponseType.ERROR_COMMAND_REQUIRES_NUMBER.toString() + "-seed");
                            return true;
                        }
                    }

                }
                if (wormholeWorld.getWorldName() == null) {
                    sender.sendMessage(ResponseType.ERROR_COMMAND_REQUIRES_WORLDNAME.toString() + "create");
                    return true;
                }
                if (WorldManager.createWormholeWorld(wormholeWorld)) {
                    sender.sendMessage(ResponseType.NORMAL_HEADER.toString() + "World: " + wormholeWorld.getWorldName() + " created");
                }
                else {
                    sender.sendMessage(ResponseType.ERROR_WORLD_ALLREADY_EXISTS.toString() + wormholeWorld.getWorldName());
                }
            }
            else {
                sender.sendMessage(ResponseType.ERROR_COMMAND_REQUIRES_ARGS.toString() + "create");
                sender.sendMessage(ResponseType.NORMAL_CREATE_COMMAND_ARGS1.toString());
                sender.sendMessage(ResponseType.NORMAL_CREATE_COMMAND_ARGS2.toString());
                sender.sendMessage(ResponseType.NORMAL_CREATE_COMMAND_ARGS3.toString());
                sender.sendMessage(ResponseType.NORMAL_CREATE_COMMAND_ARGS4.toString());
                sender.sendMessage(ResponseType.NORMAL_CREATE_COMMAND_ARGS5.toString());
                sender.sendMessage(ResponseType.NORMAL_CREATE_COMMAND_ARGS6.toString());
            }
        }
        else {
            sender.sendMessage(ResponseType.ERROR_PERMISSION_NO.toString());
        }
        return true;
    }

    /**
     * Do go world.
     * 
     * @param player
     *            the player
     * @param args
     *            the args
     * @return true, if successful
     */
    private static boolean doGoWorld(final Player player, final String[] args) {
        if (PermissionType.GO.checkPermission(player)) {
            if ((args != null) && (args.length == 1)) {
                final WormholeWorld wormholeWorld = WorldManager.getWormholeWorld(args[0]);
                if ((wormholeWorld != null) && (wormholeWorld.isWorldLoaded())) {
                    player.teleport(WorldManager.getSafeSpawnLocation(wormholeWorld, player));
                }
                else {
                    player.sendMessage(ResponseType.ERROR_WORLD_NOT_EXIST.toString() + args[0]);
                }
            }
            else {
                player.sendMessage(ResponseType.ERROR_COMMAND_REQUIRES_WORLDNAME.toString() + "go");
            }

        }
        else {
            player.sendMessage(ResponseType.ERROR_PERMISSION_NO.toString());
        }
        return true;
    }

    /**
     * Do info world.
     * 
     * @param sender
     *            the sender
     * @param args
     *            the args
     * @return true, if successful
     */
    private static boolean doInfoWorld(final CommandSender sender, final String[] args) {
        boolean allowed = false;
        if (CommandUtilities.playerCheck(sender)) {
            allowed = PermissionType.INFO.checkPermission((Player) sender);
        }
        else {
            allowed = true;
        }
        if (allowed) {
            if (CommandUtilities.playerCheck(sender) || ((args != null) && (args.length == 1))) {
                final WormholeWorld world = args.length == 0 ? WorldManager.getWorldFromPlayer((Player) sender)
                    : WorldManager.getWormholeWorld(args[0]);
                if (world != null) {
                    sender.sendMessage(ResponseType.NORMAL_HEADER.toString() + "\u00A76=======================\u00A7fINFO\u00A76=======================");
                    sender.sendMessage(ResponseType.NORMAL_HEADER.toString() + "\u00A7fWorld:\u00A7b" + world.getWorldName() + " \u00A7fNether:" + colorizeBoolean(world.isWorldTypeNether()) + " \u00A7fTime:\u00A7b" + world.getWorldTimeLockType().toString());
                    sender.sendMessage(ResponseType.NORMAL_HEADER.toString() + "\u00A7fAutoload:" + colorizeBoolean(world.isWorldAutoload()) + " \u00A7fSeed:\u00A7b" + world.getWorldSeed() + " \u00A7fWeather:\u00A7b" + world.getWorldWeatherLockType().toString());
                    sender.sendMessage(ResponseType.NORMAL_HEADER.toString() + "\u00A76=================\u00A7fWORLD PROTECTION\u00A76=================");
                    sender.sendMessage(ResponseType.NORMAL_HEADER.toString() + "\u00A7fHostiles:" + colorizeBoolean(world.isWorldAllowSpawnHostiles()) + " \u00A7fNeutrals:" + colorizeBoolean(world.isWorldAllowSpawnNeutrals()) + " \u00A7fFireSPRD:" + colorizeBoolean(world.isWorldAllowFireSpread()) + " \u00A7fLavaFIRE:" + colorizeBoolean(world.isWorldAllowLavaFire()));
                    sender.sendMessage(ResponseType.NORMAL_HEADER.toString() + "\u00A7fWaterSPRD:" + colorizeBoolean(world.isWorldAllowWaterSpread()) + " \u00A7fLightningFIRE:" + colorizeBoolean(world.isWorldAllowLightningFire()) + " \u00A7fLavaSPRD:" + colorizeBoolean(world.isWorldAllowLavaSpread()));
                    sender.sendMessage(ResponseType.NORMAL_HEADER.toString() + "\u00A76=================\u00A7fPLAYER PROTECTION\u00A76================");
                    sender.sendMessage(ResponseType.NORMAL_HEADER.toString() + "\u00A7fDrown:" + colorizeBoolean(world.isPlayerAllowDrown()) + " \u00A7fLavaDMG:" + colorizeBoolean(world.isPlayerAllowLavaDamage()) + " \u00A7fFallDMG:" + colorizeBoolean(world.isPlayerAllowFallDamage()) + " \u00A7fLgtngDMG:" + colorizeBoolean(world.isPlayerAllowLightningDamage()));
                    sender.sendMessage(ResponseType.NORMAL_HEADER.toString() + "\u00A7fFireDMG:" + colorizeBoolean(world.isPlayerAllowFireDamage()) + " \u00A7fPvPDMG:" + colorizeBoolean(world.isWorldAllowPvP()) + " \u00A7fAllDMG:" + colorizeBoolean(world.isPlayerAllowDamage()));

                }
                else {
                    sender.sendMessage(ResponseType.ERROR_WORLD_NOT_EXIST.toString() + args[0]);
                    sender.sendMessage(ResponseType.ERROR_WORLD_MAY_BE_ON_DISK.toString());
                }
            }
            else {
                sender.sendMessage(ResponseType.ERROR_COMMAND_REQUIRES_WORLDNAME.toString() + "info");
            }
        }
        else {
            sender.sendMessage(ResponseType.ERROR_PERMISSION_NO.toString());
        }
        return true;
    }

    /**
     * Do list worlds.
     * 
     * @param sender
     *            the sender
     * @return true, if successful
     */
    private static boolean doListWorlds(final CommandSender sender) {
        boolean allowed = false;
        if (CommandUtilities.playerCheck(sender)) {
            allowed = PermissionType.LIST.checkPermission((Player) sender);
        }
        else {
            allowed = true;
        }
        if (allowed) {
            final List<World> worldLoadedList = WormholeXTremeWorlds.getThisPlugin().getServer().getWorlds();
            final String[] wormholeWorldNames = WorldManager.getAllWorldNames();
            if (worldLoadedList != null) {
                final StringBuilder s = new StringBuilder();
                int i = 0;
                for (final World world : worldLoadedList) {
                    i++;
                    s.append(world.getName());
                    if (i < worldLoadedList.size()) {
                        s.append(", ");
                    }
                }
                sender.sendMessage(ResponseType.NORMAL_HEADER.toString() + "Loaded worlds: " + s.toString());
            }
            if (wormholeWorldNames != null) {
                final StringBuilder s = new StringBuilder();
                int i = 0;
                for (final String worldName : wormholeWorldNames) {
                    i++;
                    s.append(worldName);
                    if (i < wormholeWorldNames.length) {
                        s.append(", ");
                    }
                }
                sender.sendMessage(ResponseType.NORMAL_HEADER.toString() + "Configured worlds: " + s.toString());
            }
        }
        else {
            sender.sendMessage(ResponseType.ERROR_PERMISSION_NO.toString());
        }
        return true;
    }

    /**
     * Do connect world.
     * 
     * @param sender
     *            the sender
     * @param args
     *            the args
     * @param commandName
     *            the command name
     * @return true, if successful
     */
    private static boolean doLoadWorld(final CommandSender sender, final String[] args, final String commandName) {
        boolean allowed = false;
        if (CommandUtilities.playerCheck(sender)) {
            allowed = PermissionType.LOAD.checkPermission((Player) sender);
        }
        else {
            allowed = true;
        }
        if (allowed) {
            if ((args != null) && (args.length == 1)) {
                final WormholeWorld wormholeWorld = WorldManager.getWormholeWorld(args[0]);
                if (wormholeWorld != null) {
                    WorldManager.loadWorld(wormholeWorld);
                    sender.sendMessage(ResponseType.NORMAL_HEADER + "Connected world: " + args[0]);
                }
                else {
                    sender.sendMessage(ResponseType.ERROR_COMMAND_ONLY_MANAGED_WORLD.toString() + commandName);
                }
            }
            else {
                sender.sendMessage(ResponseType.ERROR_COMMAND_REQUIRES_WORLDNAME.toString() + commandName);
            }
        }
        else {
            sender.sendMessage(ResponseType.ERROR_PERMISSION_NO.toString());
        }
        return true;
    }

    /**
     * Do modify world.
     * 
     * @param sender
     *            the sender
     * @param args
     *            the args
     * @return true, if successful
     */
    private static boolean doModifyWorld(final CommandSender sender, final String[] args) {
        boolean allowed = false;
        if (CommandUtilities.playerCheck(sender)) {
            allowed = PermissionType.MODIFY.checkPermission((Player) sender);
        }
        else {
            allowed = true;
        }
        if (allowed) {
            if ((args != null) && (args.length >= 1)) {
                String worldName = "";
                boolean doHostiles = false, doNeutrals = false, doAutoLoad = false, doPvP = false;
                boolean hostiles = false, neutrals = false, autoLoad = false, pvp = false;
                boolean doLavaSpread = false, doFireSpread = false, doLavaFire = false, doWaterSpread = false, doLightningFire = false;
                boolean lavaSpread = false, fireSpread = false, lavaFire = false, waterSpread = false, lightningFire = false;
                boolean doPlayerLightningDamage = false, doPlayerDamage = false, doPlayerDrown = false, doPlayerLavaDamage = false, doPlayerFallDamage = false, doPlayerFireDamage = false;
                boolean playerLightningDamage = false, playerDamage = false, playerDrown = false, playerLavaDamage = false, playerFallDamage = false, playerFireDamage = false;
                TimeLockType timeLockType = null;
                WeatherLockType weatherLockType = null;
                boolean conflict = false;
                for (final String arg : args) {
                    final String atlc = arg.toLowerCase();
                    if (atlc.startsWith("-")) {
                        if (atlc.startsWith("-name")) {
                            if (worldName.length() > 0) {
                                conflict = true;
                            }
                            else if (atlc.contains("|")) {
                                worldName = arg.split("\\|")[1].trim();
                            }
                            else {
                                sender.sendMessage(ResponseType.ERROR_COMMAND_REQUIRES_WORLDNAME.toString() + "-name");
                                return true;
                            }
                        }
                        else if (atlc.startsWith("-time")) {
                            if (timeLockType != null) {
                                conflict = true;
                            }
                            else if (atlc.contains("|")) {
                                timeLockType = TimeLockType.getTimeType(arg.split("\\|")[1].trim().toUpperCase());
                            }
                            else {
                                sender.sendMessage(ResponseType.ERROR_COMMAND_REQUIRES_WORLDNAME.toString() + "-time");
                            }
                        }
                        else if (atlc.startsWith("-weather")) {
                            if (weatherLockType != null) {
                                conflict = true;
                            }
                            else if (atlc.contains("|")) {
                                weatherLockType = WeatherLockType.getWeatherType(arg.split("\\|")[1].trim().toUpperCase());
                            }
                            else {
                                sender.sendMessage(ResponseType.ERROR_COMMAND_REQUIRES_WORLDNAME.toString() + "-weather");
                            }
                        }
                        else if (atlc.contains("autoload")) {
                            if (doAutoLoad) {
                                conflict = true;
                            }
                            else {
                                doAutoLoad = true;
                                if (atlc.startsWith("-no")) {
                                    autoLoad = false;
                                }
                                else {
                                    autoLoad = true;
                                }
                            }
                        }
                        else if (atlc.contains("hostiles")) {
                            if (doHostiles) {
                                conflict = true;
                            }
                            else {
                                doHostiles = true;
                                if (atlc.startsWith("-no")) {
                                    hostiles = false;
                                }
                                else {
                                    hostiles = true;
                                }
                            }
                        }
                        else if (atlc.contains("neutrals")) {
                            if (doNeutrals) {
                                conflict = true;
                            }
                            else {
                                doNeutrals = true;
                                if (atlc.startsWith("-no")) {
                                    neutrals = false;
                                }
                                else {
                                    neutrals = true;
                                }
                            }
                        }
                        else if (atlc.contains("pvp")) {
                            if (doPvP) {
                                conflict = true;
                            }
                            else {
                                doPvP = true;
                                if (atlc.startsWith("-no")) {
                                    pvp = false;
                                }
                                else {
                                    pvp = true;
                                }
                            }
                        }

                        else if (atlc.contains("lightningdamage")) {
                            if (doPlayerLightningDamage) {
                                conflict = true;
                            }
                            else {
                                doPlayerLightningDamage = true;
                                if (atlc.startsWith("-no")) {
                                    playerLightningDamage = false;
                                }
                                else {
                                    playerLightningDamage = true;
                                }
                            }
                        }
                        else if (atlc.startsWith("-damage") || atlc.startsWith("-nodamage")) {
                            if (doPlayerDamage) {
                                conflict = true;
                            }
                            else {
                                doPlayerDamage = true;
                                if (atlc.startsWith("-no")) {
                                    playerDamage = false;
                                }
                                else {
                                    playerDamage = true;
                                }
                            }
                        }
                        else if (atlc.contains("drown")) {
                            if (doPlayerDrown) {
                                conflict = true;
                            }
                            else {
                                doPlayerDrown = true;
                                if (atlc.startsWith("-no")) {
                                    playerDrown = false;
                                }
                                else {
                                    playerDrown = true;
                                }
                            }
                        }
                        else if (atlc.contains("lavadamage")) {
                            if (doPlayerLavaDamage) {
                                conflict = true;
                            }
                            else {
                                doPlayerLavaDamage = true;
                                if (atlc.startsWith("-no")) {
                                    playerLavaDamage = false;
                                }
                                else {
                                    playerLavaDamage = true;
                                }
                            }
                        }
                        else if (atlc.contains("falldamage")) {
                            if (doPlayerFallDamage) {
                                conflict = true;
                            }
                            else {
                                doPlayerFallDamage = true;
                                if (atlc.startsWith("-no")) {
                                    playerFallDamage = false;
                                }
                                else {
                                    playerFallDamage = true;
                                }
                            }
                        }
                        else if (atlc.contains("firedamage")) {
                            if (doPlayerFireDamage) {
                                conflict = true;
                            }
                            else {
                                doPlayerFireDamage = true;
                                if (atlc.startsWith("-no")) {
                                    playerFireDamage = false;
                                }
                                else {
                                    playerFireDamage = true;
                                }
                            }
                        }
                        else if (atlc.contains("lavaspread")) {
                            if (doLavaSpread) {
                                conflict = true;
                            }
                            else {
                                doLavaSpread = true;
                                if (atlc.startsWith("-no")) {
                                    lavaSpread = false;
                                }
                                else {
                                    lavaSpread = true;
                                }
                            }
                        }
                        else if (atlc.contains("firespread")) {
                            if (doFireSpread) {
                                conflict = true;
                            }
                            else {
                                doFireSpread = true;
                                if (atlc.startsWith("-no")) {
                                    fireSpread = false;
                                }
                                else {
                                    fireSpread = true;
                                }
                            }
                        }
                        else if (atlc.contains("lavafire")) {
                            if (doLavaFire) {
                                conflict = true;
                            }
                            else {
                                doLavaFire = true;
                                if (atlc.startsWith("-no")) {
                                    lavaFire = false;
                                }
                                else {
                                    lavaFire = true;
                                }
                            }
                        }
                        else if (atlc.contains("waterspread")) {
                            if (doWaterSpread) {
                                conflict = true;
                            }
                            else {
                                doWaterSpread = true;
                                if (atlc.startsWith("-no")) {
                                    waterSpread = false;
                                }
                                else {
                                    waterSpread = true;
                                }
                            }
                        }
                        else if (atlc.contains("lightningfire")) {
                            if (doLightningFire) {
                                conflict = true;
                            }
                            else {
                                doLightningFire = true;
                                if (atlc.startsWith("-no")) {
                                    lightningFire = false;
                                }
                                else {
                                    lightningFire = true;
                                }
                            }
                        }
                    }
                }
                if (conflict) {
                    sender.sendMessage(ResponseType.ERROR_HEADER.toString() + "Conflicting or duplicate commands specified.");
                }
                if (worldName.length() > 0) {
                    final WormholeWorld world = WorldManager.getWormholeWorld(worldName);
                    if (world != null) {
                        if (doHostiles || doNeutrals || doAutoLoad || doPvP || (weatherLockType != null) || (timeLockType != null) || doPlayerLightningDamage || doPlayerDamage || doPlayerDrown || doPlayerLavaDamage || doPlayerFallDamage || doPlayerFireDamage || doLavaSpread || doFireSpread || doLavaFire || doWaterSpread || doLightningFire) {
                            if (doHostiles) {
                                world.setWorldAllowSpawnHostiles(hostiles);
                            }
                            if (doNeutrals) {
                                world.setWorldAllowSpawnNeutrals(neutrals);
                            }
                            if (doPvP) {
                                world.setWorldAllowPvP(pvp);
                            }
                            if (doAutoLoad) {
                                world.setWorldAutoload(autoLoad);
                            }
                            if (doPlayerLightningDamage) {
                                world.setPlayerAllowLightningDamage(playerLightningDamage);
                            }
                            if (doPlayerDamage) {
                                world.setPlayerAllowDamage(playerDamage);
                            }
                            if (doPlayerDrown) {
                                world.setPlayerAllowDrown(playerDrown);
                            }
                            if (doPlayerLavaDamage) {
                                world.setPlayerAllowLavaDamage(playerLavaDamage);
                            }
                            if (doPlayerFallDamage) {
                                world.setPlayerAllowFallDamage(playerFallDamage);
                            }
                            if (doPlayerFireDamage) {
                                world.setPlayerAllowFireDamage(playerFireDamage);
                            }
                            if (doLavaSpread) {
                                world.setWorldAllowLavaSpread(lavaSpread);
                            }
                            if (doFireSpread) {
                                world.setWorldAllowFireSpread(fireSpread);
                            }
                            if (doLavaFire) {
                                world.setWorldAllowLavaFire(lavaFire);
                            }
                            if (doWaterSpread) {
                                world.setWorldAllowWaterSpread(waterSpread);
                            }
                            if (doLightningFire) {
                                world.setWorldAllowLightningFire(lightningFire);
                            }
                            if (timeLockType != null) {
                                world.setWorldTimeLockType(timeLockType);
                            }
                            if (weatherLockType != null) {
                                world.setWorldWeatherLockType(weatherLockType);
                            }
                            WorldManager.addWorld(world);
                            if (doAutoLoad) {
                                WorldManager.loadWorld(world);
                            }
                            if (world.isWorldLoaded()) {
                                if (doHostiles || doNeutrals) {
                                    WormholeXTremeWorlds.getScheduler().scheduleSyncDelayedTask(thisPlugin, new ScheduleAction(world, ActionType.ClearEntities));
                                }
                                if (weatherLockType != null) {
                                    WormholeXTremeWorlds.getScheduler().scheduleSyncDelayedTask(thisPlugin, new ScheduleAction(world, ActionType.SetWeather));
                                }
                                if (doPvP) {
                                    WorldManager.setWorldPvP(world);
                                }
                            }
                            final String[] w = new String[1];
                            w[0] = worldName;
                            return doInfoWorld(sender, w);
                        }
                        else {
                            sender.sendMessage(ResponseType.ERROR_COMMAND_REQUIRES_ARGS.toString() + "modify");
                            sender.sendMessage(ResponseType.NORMAL_MODIFY_COMMAND_ARGS1.toString());
                            sender.sendMessage(ResponseType.NORMAL_MODIFY_COMMAND_ARGS2.toString());
                            sender.sendMessage(ResponseType.NORMAL_MODIFY_COMMAND_ARGS3.toString());
                            sender.sendMessage(ResponseType.NORMAL_MODIFY_COMMAND_ARGS4.toString());
                            sender.sendMessage(ResponseType.NORMAL_MODIFY_COMMAND_ARGS5.toString());
                            sender.sendMessage(ResponseType.NORMAL_MODIFY_COMMAND_ARGS6.toString());
                        }
                    }
                    else {
                        sender.sendMessage(ResponseType.ERROR_WORLD_NOT_EXIST.toString() + worldName);
                    }
                }
                else {
                    sender.sendMessage(ResponseType.ERROR_HEADER.toString() + "Must specify a world to modify.");
                }
            }
            else {
                sender.sendMessage(ResponseType.ERROR_COMMAND_REQUIRES_ARGS.toString() + "modify");
                sender.sendMessage(ResponseType.NORMAL_MODIFY_COMMAND_ARGS1.toString());
                sender.sendMessage(ResponseType.NORMAL_MODIFY_COMMAND_ARGS2.toString());
                sender.sendMessage(ResponseType.NORMAL_MODIFY_COMMAND_ARGS3.toString());
                sender.sendMessage(ResponseType.NORMAL_MODIFY_COMMAND_ARGS4.toString());
                sender.sendMessage(ResponseType.NORMAL_MODIFY_COMMAND_ARGS5.toString());
                sender.sendMessage(ResponseType.NORMAL_MODIFY_COMMAND_ARGS6.toString());
            }
        }
        else {
            sender.sendMessage(ResponseType.ERROR_PERMISSION_NO.toString());
        }
        return true;
    }

    /**
     * Do remove world.
     * 
     * @param sender
     *            the sender
     * @param args
     *            the args
     * @return true, if successful
     */
    private static boolean doRemoveWorld(final CommandSender sender, final String[] args) {
        boolean allowed = false;
        if (CommandUtilities.playerCheck(sender)) {
            allowed = PermissionType.REMOVE.checkPermission((Player) sender);
        }
        else {
            allowed = true;
        }
        if (allowed) {
            if ((args != null) && (args.length >= 1)) {
                final WormholeWorld wormholeWorld = WorldManager.getWormholeWorld(args[0]);
                if (wormholeWorld != null) {
                    WorldManager.removeWorld(wormholeWorld);
                    sender.sendMessage(ResponseType.NORMAL_HEADER.toString() + "Removed world: " + args[0] + ". Deleted world config file, world will be unavailable at next server restart.");
                }
                else {
                    sender.sendMessage(ResponseType.ERROR_WORLD_NOT_EXIST.toString() + args[0]);
                }
            }
            else {
                sender.sendMessage(ResponseType.ERROR_COMMAND_REQUIRES_WORLDNAME.toString() + "remove");
            }
        }
        else {
            sender.sendMessage(ResponseType.ERROR_PERMISSION_NO.toString());
        }
        return true;
    }

    /**
     * Do set spawn world.
     * 
     * @param player
     *            the player
     * @return true, if successful
     */
    private static boolean doSetSpawnWorld(final Player player) {
        if (PermissionType.SET_SPAWN.checkPermission(player)) {
            final Location playerLocation = player.getLocation();
            final World playerWorld = playerLocation.getWorld();
            final WormholeWorld playerWormholeWorld = WorldManager.getWormholeWorld(playerWorld.getName());
            if (playerWormholeWorld != null) {
                if (playerWorld.setSpawnLocation((int) playerLocation.getX(), (int) playerLocation.getY(), (int) playerLocation.getZ())) {
                    playerWormholeWorld.setWorldSpawn(playerLocation);
                    playerWormholeWorld.setWorldCustomSpawn(new int[]{
                        (int) playerLocation.getX(), (int) playerLocation.getY(), (int) playerLocation.getZ()
                    });
                    WorldManager.addWorld(playerWormholeWorld);
                    player.sendMessage(ResponseType.NORMAL_HEADER.toString() + "Spawn for world \"" + playerWorld.getName() + "\" set to current location.");
                }
                else {
                    player.sendMessage(ResponseType.ERROR_HEADER.toString() + "Unable to set spawn for world \"" + playerWorld.getName() + "\"!");
                }
            }
            else {
                player.sendMessage(ResponseType.ERROR_COMMAND_ONLY_MANAGED_WORLD.toString() + "setspawn");
            }
        }
        else {
            player.sendMessage(ResponseType.ERROR_PERMISSION_NO.toString());
        }
        return true;
    }

    /**
     * Do spawn world.
     * 
     * @param player
     *            the player
     * @return true, if successful
     */
    private static boolean doSpawnWorld(final Player player) {
        if (PermissionType.SPAWN.checkPermission(player)) {
            final WormholeWorld wormholeWorld = WorldManager.getWorldFromPlayer(player);
            if (wormholeWorld != null) {
                player.teleport(WorldManager.getSafeSpawnLocation(wormholeWorld, player));
            }
            else {
                player.sendMessage(ResponseType.ERROR_COMMAND_ONLY_MANAGED_WORLD.toString());
            }
        }
        else {
            player.sendMessage(ResponseType.ERROR_PERMISSION_NO.toString());
        }
        return true;
    }

    /* (non-Javadoc)
     * @see org.bukkit.command.CommandExecutor#onCommand(org.bukkit.command.CommandSender, org.bukkit.command.Command, java.lang.String, java.lang.String[])
     */
    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        final String[] cleanArgs = CommandUtilities.commandCleaner(args);
        if (cleanArgs != null) {
            if (CommandUtilities.playerCheck(sender)) {
                thisPlugin.prettyLog(Level.FINE, false, "Command \"wxw\" args: \"" + Arrays.toString(cleanArgs) + "\"");
            }
            if (cleanArgs.length >= 1) {
                if (cleanArgs[0].equalsIgnoreCase("list")) {
                    return doListWorlds(sender);
                }
                else if (cleanArgs[0].equalsIgnoreCase("create")) {
                    return doCreateWorld(sender, CommandUtilities.commandRemover(cleanArgs));
                }
                else if (cleanArgs[0].equalsIgnoreCase("remove")) {
                    return doRemoveWorld(sender, CommandUtilities.commandRemover(cleanArgs));
                }
                else if (cleanArgs[0].equalsIgnoreCase("connect") || cleanArgs[0].equalsIgnoreCase("load")) {
                    return doLoadWorld(sender, CommandUtilities.commandRemover(cleanArgs), cleanArgs[0]);
                }
                else if (cleanArgs[0].equalsIgnoreCase("modify")) {
                    return doModifyWorld(sender, CommandUtilities.commandRemover(cleanArgs));
                }
                else if (cleanArgs[0].equalsIgnoreCase("info")) {
                    return doInfoWorld(sender, CommandUtilities.commandRemover(cleanArgs));
                }
                else if (cleanArgs[0].equalsIgnoreCase("go")) {
                    if (CommandUtilities.playerCheck(sender)) {
                        return doGoWorld((Player) sender, CommandUtilities.commandRemover(cleanArgs));
                    }
                    else {
                        sender.sendMessage(ResponseType.ERROR_IN_GAME_ONLY.toString());
                        return true;
                    }
                }
                else if (cleanArgs[0].equalsIgnoreCase("setspawn")) {
                    if (CommandUtilities.playerCheck(sender)) {
                        return doSetSpawnWorld((Player) sender);
                    }
                    else {
                        sender.sendMessage(ResponseType.ERROR_IN_GAME_ONLY.toString());
                        return true;
                    }
                }
                else if (cleanArgs[0].equalsIgnoreCase("spawn")) {
                    if (CommandUtilities.playerCheck(sender)) {
                        return doSpawnWorld((Player) sender);
                    }
                }
            }
        }
        return false;
    }
}
