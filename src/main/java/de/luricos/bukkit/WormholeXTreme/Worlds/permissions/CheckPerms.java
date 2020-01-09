package de.luricos.bukkit.WormholeXTreme.Worlds.permissions;

import org.bukkit.entity.Player;

public class CheckPerms {
    public static boolean hasPermission(Player player, String perm){
        if (player.hasPermission(perm)){
            return true;
        }
        else return false;
    }
}
