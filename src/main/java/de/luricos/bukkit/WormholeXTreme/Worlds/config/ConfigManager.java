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
package de.luricos.bukkit.WormholeXTreme.Worlds.config;

import java.util.concurrent.ConcurrentHashMap;

/**
 * The Class ConfigManager.
 * 
 * @author alron
 */
public class ConfigManager {

    /**
     * The Enum OptionKeys.
     */
    enum ServerOptionKeys {

        /** The server option help. */
        serverOptionHelp,

        /** The server option ops bypass permissions. */
        serverOptionOpsBypassPermissions,

        /** The server option permissions. */
        serverOptionPermissions,

        /** The server option timelock. */
        serverOptionTimelock

    }

    /** The Constant options. */
    protected static final ConcurrentHashMap<ServerOptionKeys, ServerOption> serverOptions = new ConcurrentHashMap<ServerOptionKeys, ServerOption>();

    /**
     * Gets the server option.
     * 
     * @param serverOptionKey
     *            the server option key
     * @return the server option
     */
    private static ServerOption getServerOption(final ServerOptionKeys serverOptionKey) {
        return serverOptions.get(serverOptionKey);
    }

    /**
     * Gets the server option help.
     * 
     * @return the server option help
     */
    public static boolean getServerOptionHelp() {
        final ServerOption o = getServerOption(ServerOptionKeys.serverOptionHelp);
        if (o != null) {
            return Boolean.valueOf(o.getOptionValue().toString());
        }
        else {
            return true;
        }
    }

    /**
     * Gets the server option ops bypass permissions.
     * 
     * @return the server option ops bypass permissions
     */
    public static boolean getServerOptionOpsBypassPermissions() {
        final ServerOption o = getServerOption(ServerOptionKeys.serverOptionOpsBypassPermissions);
        if (o != null) {
            return Boolean.valueOf(o.getOptionValue().toString());
        }
        else {
            return true;
        }
    }

    /**
     * Gets the server option permissions.
     * 
     * @return the server option permissions
     */
    public static boolean getServerOptionPermissions() {
        final ServerOption o = getServerOption(ServerOptionKeys.serverOptionPermissions);
        if (o != null) {
            return Boolean.valueOf(o.getOptionValue().toString());
        }
        else {
            return true;
        }
    }

    /**
     * Gets the server option timelock.
     * 
     * @return the server option timelock
     */
    public static boolean getServerOptionTimelock() {
        final ServerOption o = getServerOption(ServerOptionKeys.serverOptionTimelock);
        if (o != null) {
            return Boolean.valueOf(o.getOptionValue().toString());
        }
        else {
            return false;
        }
    }

    /**
     * Sets the option value.
     * 
     * @param optionKey
     *            the option key
     * @param optionValue
     *            the option value
     */
    private static void setOptionValue(final ServerOptionKeys optionKey, final Object optionValue) {
        final ServerOption o = serverOptions.get(optionKey);
        if ((o != null) && (optionValue != null)) {
            o.setOptionValue(optionValue);
        }
    }

    /**
     * Sets the server option help.
     * 
     * @param b
     *            the new server option help
     */
    public static void setServerOptionHelp(final boolean b) { // NO_UCD
        ConfigManager.setOptionValue(ServerOptionKeys.serverOptionHelp, b);
    }

    /**
     * Sets the server option ops bypass permissions.
     * 
     * @param b
     *            the new server option ops bypass permissions
     */
    public static void setServerOptionOpsBypassPermissions(final boolean b) { // NO_UCD
        ConfigManager.setOptionValue(ServerOptionKeys.serverOptionOpsBypassPermissions, b);
    }

    /**
     * Sets the server option permissions.
     * 
     * @param b
     *            the new server option permissions
     */
    public static void setServerOptionPermissions(final boolean b) { // NO_UCD
        ConfigManager.setOptionValue(ServerOptionKeys.serverOptionPermissions, b);
    }

    /**
     * Sets the server option timelock.
     * 
     * @param b
     *            the new server option timelock
     */
    public static void setServerOptionTimelock(final boolean b) { // NO_UCD
        ConfigManager.setOptionValue(ServerOptionKeys.serverOptionTimelock, b);
    }
}
