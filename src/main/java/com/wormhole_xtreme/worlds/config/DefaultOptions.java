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
package com.wormhole_xtreme.worlds.config;

import com.wormhole_xtreme.worlds.config.ConfigManager.ServerOptionKeys;

/**
 * @author alron
 * 
 */
public class DefaultOptions {

    /** The Constant defaultOptions. */
    final static ServerOption[] defaultServerOptions = {
        new ServerOption(ServerOptionKeys.serverOptionPermissions, "Option: Enable or disable Permissions plugin support. Default: true", "boolean", true, "WormholeXTremeWorlds"),
        new ServerOption(ServerOptionKeys.serverOptionOpsBypassPermissions, "Option: Ops bypass Permissions plugin access checks. Default: true", "boolean", true, "WormholeXTremeWorlds"),
        new ServerOption(ServerOptionKeys.serverOptionHelp, "Option: Enable or disable Help plugin support. Default: true", "boolean", true, "WormholeXTremeWorlds"),
    //new ServerOption(ServerOptionKeys.serverOptionIconomy, "Option: Enable or disable iConomy plugin support. Default: true", "boolean", true, "WormholeXTremeWorlds"),
    //new ServerOption(ServerOptionKeys.serverOptionOpsBypassIconomy, "Option: Ops bypass iConomy plugin charges. Default: true", "boolean", true, "WormholeXTremeWorlds"),
    //new ServerOption(ServerOptionKeys.serverOptionIconomyCostForNormalWorldGeneration, "Option: iConomy cost to generate a normal world. Default: 1000.0", "double", 1000.0, "WormholeXTremeWorlds"),
    //new ServerOption(ServerOptionKeys.serverOptionIconomyCostForNetherWorldGeneration, "Option: iConomy cost to generate a nether world. Default: 2000.0", "double", 2000.0, "WormholeXTremeWorlds"),
    };
}
