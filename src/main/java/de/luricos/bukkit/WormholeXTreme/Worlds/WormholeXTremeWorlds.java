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
package de.luricos.bukkit.WormholeXTreme.Worlds;

import de.luricos.bukkit.WormholeXTreme.Worlds.command.CommandUtilities;
import de.luricos.bukkit.WormholeXTreme.Worlds.config.ConfigManager;
import de.luricos.bukkit.WormholeXTreme.Worlds.config.XMLConfig;
import de.luricos.bukkit.WormholeXTreme.Worlds.events.EventUtilities;
import de.luricos.bukkit.WormholeXTreme.Worlds.handler.WorldHandler;
import de.luricos.bukkit.WormholeXTreme.Worlds.plugin.PluginSupport;
import de.luricos.bukkit.WormholeXTreme.Worlds.scheduler.ScheduleAction;
import de.luricos.bukkit.WormholeXTreme.Worlds.scheduler.ScheduleAction.ActionType;
import de.luricos.bukkit.WormholeXTreme.Worlds.utils.WXLogger;
import de.luricos.bukkit.WormholeXTreme.Worlds.world.WorldManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.logging.Level;

/**
 * The Class WormholeXTremeWorlds.
 * 
 * @author alron
 */
public class WormholeXTremeWorlds extends JavaPlugin {

    /** The scheduler. */
    private static BukkitScheduler scheduler = null;
    /** The time schedule id. */
    private static int timeScheduleId = -1;
    /** The world handler. */
    private static WorldHandler worldHandler = null;

    /* (non-Javadoc)
     * @see org.bukkit.plugin.java.JavaPlugin#onLoad()
     */
    @Override
    public void onLoad() {
        WXLogger.initLogger("WormholeXTremeWorlds", this.getDescription().getVersion(), Level.INFO);
        
        setScheduler(this.getServer().getScheduler());
        
        WXLogger.prettyLog(Level.INFO, true, "Startup in progress ...");
        
        XMLConfig.loadXmlConfig(this.getDescription());
        
        WXLogger.prettyLog(Level.INFO, true, "Loading completed");
    }    
    
    /* (non-Javadoc)
     * @see org.bukkit.plugin.Plugin#onEnable()
     */
    @Override
    public void onEnable() {
        WXLogger.prettyLog(Level.INFO, true, "Enabling plugin ...");
        EventUtilities.registerEvents();
        
        PluginSupport.enableSupportedPlugins();

        CommandUtilities.registerCommands(this);

        final int loaded = WorldManager.loadAutoloadWorlds();
        if (loaded > 0) {
            WXLogger.prettyLog(Level.INFO, false, "Auto-loaded " + loaded + (loaded > 1 ? " worlds." : " world."));
        }
        setWorldHandler(new WorldHandler());        
        
        if (ConfigManager.getServerOptionTimelock()) {
            setTimeScheduleId(scheduler.scheduleSyncRepeatingTask(this, new ScheduleAction(ActionType.TimeLock), 0, 200));
        } else {
            WXLogger.prettyLog(Level.INFO, false, "Time-lock scheduler disabled, per config.xml directive.");
        }
        
        WXLogger.prettyLog(Level.INFO, true, "Startup finished");
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
        
        WXLogger.prettyLog(Level.INFO, true, "Successfully shut down itself and unlinked supported plugins.");
    }    
    
    /**
     * Gets the scheduler.
     * 
     * @return the scheduler
     */
    public static BukkitScheduler getScheduler() {
        return scheduler;
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
}
