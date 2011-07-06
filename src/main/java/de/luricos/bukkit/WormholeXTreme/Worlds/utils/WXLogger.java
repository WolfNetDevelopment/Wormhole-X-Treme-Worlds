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
package de.luricos.bukkit.WormholeXTreme.Worlds.utils;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

/**
 *
 * @author lycano
 */
public class WXLogger {
    private static Level logLevel = Level.INFO;
    private static Logger logger = null;
    private static String logPluginName = null;
    private static String logPluginVersion = null;
    
    public static void initLogger(String pluginName, String pluginVersion, Level logLevel) {
        if (WXLogger.logger == null) {
            Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin(pluginName);
            if (plugin != null) {
                WXLogger.logger = Logger.getLogger(plugin.getServer().getLogger().getName() + "." + pluginName);
            }
            
            WXLogger.logLevel = logLevel;
            WXLogger.logger.setLevel(logLevel);
            WXLogger.logPluginName = pluginName;
            WXLogger.logPluginVersion = pluginVersion;
        }
    }
    
    public static void setLogLevel(Level logLevel) {
        WXLogger.logLevel = logLevel;
        WXLogger.logger.setLevel(logLevel);
    }
    
    public static void prettyLog(final Level logLevel, final boolean version, final String message) {
        final String prettyName = ("[" + getName() + "]");
        final String prettyVersion = ("[v" + getVersion() + "]");
        String prettyLogLine = prettyName;
        if (version) {
            prettyLogLine += prettyVersion;
        }
        
        logger.log(logLevel, prettyLogLine + " " + message);
    }
    
    public static Level getLogLevel() {
        return logLevel;
    }
    
    public static String getVersion() {
        return logPluginVersion;
    }
    
    public static String getName() {
        return logPluginName;
    }
}
