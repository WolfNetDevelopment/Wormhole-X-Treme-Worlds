/**
 *
 */
package de.luricos.bukkit.WormholeXTreme.Worlds.plugin;

import me.taylorkelly.help.Help;

import com.nijiko.permissions.PermissionHandler;

/**
 * @author alron
 * 
 */
public class PluginSupport {

    /** The help. */
    private static Help help = null;

    /** The permission handler. */
    private static PermissionHandler permissionHandler = null;

    /**
     * Gets the help.
     * 
     * @return the help
     */
    protected static Help getHelp() {
        return help;
    }

    /**
     * Gets the permission handler.
     * 
     * @return the permission handler
     */
    public static PermissionHandler getPermissionHandler() {
        return permissionHandler;
    }

    /**
     * Sets the help.
     * 
     * @param help
     *            the new help
     */
    static void setHelp(final Help help) {
        PluginSupport.help = help;
    }

    /**
     * Sets the permission handler.
     * 
     * @param permissionHandler
     *            the new permission handler
     */
    static void setPermissionHandler(final PermissionHandler permissionHandler) {
        PluginSupport.permissionHandler = permissionHandler;
    }
}
