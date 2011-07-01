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
package de.luricos.bukkit.WormholeXTreme.Worlds;

import de.luricos.bukkit.WormholeXTreme.Worlds.command.CommandUtilities;
import de.luricos.bukkit.WormholeXTreme.Worlds.config.ConfigManager;
import de.luricos.bukkit.WormholeXTreme.Worlds.config.XMLConfig;
import de.luricos.bukkit.WormholeXTreme.Worlds.events.EventUtilities;
import de.luricos.bukkit.WormholeXTreme.Worlds.handler.WorldHandler;
import de.luricos.bukkit.WormholeXTreme.Worlds.plugin.HelpSupport;
import de.luricos.bukkit.WormholeXTreme.Worlds.plugin.PluginSupport;
import de.luricos.bukkit.WormholeXTreme.Worlds.scheduler.ScheduleAction;
import de.luricos.bukkit.WormholeXTreme.Worlds.scheduler.ScheduleAction.ActionType;
import de.luricos.bukkit.WormholeXTreme.Worlds.world.WorldManager;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The Class WormholeXTremeWorlds.
 * 
 * @author alron
 */
public class WormholeXTremeWorlds extends JavaPlugin {

    /** this logger. */
    private static Logger thisLogger = null;
    /** The scheduler. */
    private static BukkitScheduler scheduler = null;
    /** The time schedule id. */
    private static int timeScheduleId = -1;
    /** The world handler. */
    private static WorldHandler worldHandler = null;

    /**
     * Gets the scheduler.
     * 
     * @return the scheduler
     */
    public static BukkitScheduler getScheduler() {
        return scheduler;
    }

    /**
     * Gets this logger.
     * 
     * @return This logger
     */
    private static Logger getThisLogger() {
        return thisLogger;
    }

    /**
     * Gets this plugin.
     * 
     * @return This plugin
     */
    public static WormholeXTremeWorlds getThisPlugin() {
        Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("WormholeXTremeWorlds");
        if (plugin == null || !(plugin instanceof WormholeXTremeWorlds)) {
            throw new RuntimeException("'WormholeXTremeWorlds' not found. 'WormholeXTremeWorlds' plugin disabled?");
        }

        return ((WormholeXTremeWorlds) plugin);
    }

    /**
     * Gets the time schedule id.
     * 
     * @return the time schedule id
     */
    private static int getTimeScheduleId() {
        return timeScheduleId;
    }

    /**
     * Gets the world handler.
     * 
     * @return the world handler
     */
    public static WorldHandler getWorldHandler() {
        return worldHandler;
    }

    /**
     * Sets the scheduler.
     * 
     * @param scheduler
     *            the new scheduler
     */
    private static void setScheduler(final BukkitScheduler scheduler) {
        WormholeXTremeWorlds.scheduler = scheduler;
    }

    /**
     * Sets this logger.
     * 
     * @param thisLogger
     *            The new logger
     */
    private static void setThisLogger(final Logger thisLogger) {
        WormholeXTremeWorlds.thisLogger = thisLogger;
    }

    /**
     * Sets the time schedule id.
     * 
     * @param timeScheduleId
     *            the new time schedule id
     */
    private static void setTimeScheduleId(final int timeScheduleId) {
        WormholeXTremeWorlds.timeScheduleId = timeScheduleId;
    }

    /**
     * Sets the world handler.
     * 
     * @param worldHandler
     *            the new world handler
     */
    private static void setWorldHandler(final WorldHandler worldHandler) {
        WormholeXTremeWorlds.worldHandler = worldHandler;
    }

    /* (non-Javadoc)
     * @see org.bukkit.plugin.java.JavaPlugin#onLoad()
     */
    @Override
    public void onLoad() {
        setThisLogger(this.getServer().getLogger());
        setScheduler(this.getServer().getScheduler());
        prettyLog(Level.INFO, true, "Load Beginning.");
        XMLConfig.loadXmlConfig(this.getDescription());
        prettyLog(Level.INFO, true, "Load Completed.");
    }    
    
    /* (non-Javadoc)
     * @see org.bukkit.plugin.Plugin#onEnable()
     */
    @Override
    public void onEnable() {
        prettyLog(Level.INFO, true, "Enable Beginning.");
        EventUtilities.registerEvents();
        
        PluginSupport.enableSupportedPlugins();
        
        CommandUtilities.registerCommands();
        HelpSupport.registerHelpCommands();
        
        final int loaded = WorldManager.loadAutoloadWorlds();
        if (loaded > 0) {
            prettyLog(Level.INFO, false, "Auto-loaded " + loaded + (loaded > 1 ? " worlds." : " world."));
        }
        setWorldHandler(new WorldHandler());        
        
        if (ConfigManager.getServerOptionTimelock()) {
            setTimeScheduleId(scheduler.scheduleSyncRepeatingTask(this, new ScheduleAction(ActionType.TimeLock), 0, 200));
        } else {
            prettyLog(Level.INFO, false, "Timelock scheduler disabled, per config.xml directive.");
        }
        prettyLog(Level.INFO, true, "Enable Completed.");
    }
    
    /* (non-Javadoc)
     * @see org.bukkit.plugin.Plugin#onDisable()
     */
    @Override
    public void onDisable() {
        if (getTimeScheduleId() > 0) {
            scheduler.cancelTask(getTimeScheduleId());
        }
        XMLConfig.saveXmlConfig(this.getDescription());
        
        PluginSupport.disableSupportedPlugins();
        
        prettyLog(Level.INFO, true, "Successfully shut down iteself and unlinked supported plugins.");
    }    

    /**
     * 
     * prettyLog: A quick and dirty way to make log output clean, unified, and with versioning as needed.
     * 
     * @param severity
     *            Level of severity in the form of INFO, WARNING, SEVERE, etc.
     * @param version
     *            true causes version display in log entries.
     * @param message
     *            to prettyLog.
     * 
     */
    public void prettyLog(final Level severity, final boolean version, final String message) {
        final String prettyName = ("[" + this.getDescription().getName() + "]");
        final String prettyVersion = ("[v" + this.getDescription().getVersion() + "]");
        String prettyLogLine = prettyName;
        if (version) {
            prettyLogLine += prettyVersion;
            getThisLogger().log(severity, prettyLogLine + " " + message);
        } else {
            getThisLogger().log(severity, prettyLogLine + " " + message);
        }
    }
}
