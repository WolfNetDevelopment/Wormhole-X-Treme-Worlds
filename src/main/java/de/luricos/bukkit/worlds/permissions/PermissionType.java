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
package de.luricos.bukkit.worlds.permissions;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import org.bukkit.entity.Player;

import de.luricos.bukkit.worlds.WormholeXTremeWorlds;
import de.luricos.bukkit.worlds.config.ConfigManager;
import de.luricos.bukkit.worlds.plugin.PluginSupport;

/**
 * The Enum PermissionType.
 * 
 * @author alron
 */
public enum PermissionType {
    /** The go permission. */
    GO("wxw.admin.go"),

    /** The list permission. */
    LIST("wxw.admin.list"),

    /** The remove permission. */
    REMOVE("wxw.admin.remove"),

    /** The connect permission. */
    LOAD("wxw.admin.load"),

    /** The modify permission. */
    MODIFY("wxw.admin.modify"),

    /** The info permission. */
    INFO("wxw.admin.info"),

    /** The set spawn permission. */
    SET_SPAWN("wxw.admin.setspawn"),

    /** The create permission. */
    CREATE("wxw.admin.create"),

    /** The spawn permission. */
    SPAWN("wxw.spawn");

    /** The permission node. */
    private final String permissionNode;

    /** The Constant permissionMap. */
    private static final Map<String, PermissionType> permissionMap = new HashMap<String, PermissionType>();

    /** The Constant thisPlugin. */
    private static final WormholeXTremeWorlds thisPlugin = WormholeXTremeWorlds.getThisPlugin();

    static {
        for (final PermissionType type : EnumSet.allOf(PermissionType.class)) {
            permissionMap.put(type.permissionNode, type);
        }
    }

    /**
     * From permission node.
     * 
     * @param permissionNode
     *            the permission node
     * @return the permission type
     */
    public static PermissionType fromPermissionNode(final String permissionNode) { // NO_UCD
        return permissionMap.get(permissionNode);
    }

    /**
     * Instantiates a new permission type.
     * 
     * @param name
     *            the name
     */
    private PermissionType(final String name) {
        permissionNode = name;
    }

    /**
     * Check player access permissions.<br>
     * 
     * Check to see if player isOp and we have no permissions plugin enabled or if isOp can bypass permissions checks.<br>
     * Otherwise check player's permission via the Permissions plugin. Fall through to blanket deny otherwise.<br>
     * 
     * @param player
     *            the player
     * @return true, if successful
     */
    public boolean checkPermission(final Player player) {
        if (player != null) {
            boolean allowed = false;
            if ((player.isOp() && !ConfigManager.getServerOptionPermissions()) || (player.isOp() && ConfigManager.getServerOptionOpsBypassPermissions())) {
                allowed = true;
            }
            else if ((PluginSupport.getPermissionHandler() != null) && PluginSupport.getPermissionHandler().has(player, permissionNode)) {
                allowed = true;
            }
            if (allowed) {
                thisPlugin.prettyLog(Level.FINE, false, "Player: \"" + player.getName() + "\" granted \"" + toString() + "\" permissions.");
                return true;
            }
            thisPlugin.prettyLog(Level.FINE, false, "Player: \"" + player.getName() + "\" denied \"" + toString() + "\" permissions.");
        }
        return false;
    }

    /**
     * Gets the permission.
     * 
     * @return the permission
     */
    public String getPermission() {
        return permissionNode;
    }
}
