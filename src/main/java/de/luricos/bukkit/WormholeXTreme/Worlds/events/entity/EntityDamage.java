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
package de.luricos.bukkit.WormholeXTreme.Worlds.events.entity;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import de.luricos.bukkit.WormholeXTreme.Worlds.config.ResponseType;
import de.luricos.bukkit.WormholeXTreme.Worlds.world.WorldManager;
import de.luricos.bukkit.WormholeXTreme.Worlds.world.WormholeWorld;

/**
 * The Class EntityDamage.
 * 
 * @author alron
 */
public class EntityDamage {

    /**
     * Handle entity damage.
     * 
     * @param player
     *            the player
     * @param cause
     *            the cause
     * @param damager
     *            the damager
     * @return true, if successful
     */
    public static boolean handleEntityDamage(final Player player, final DamageCause cause, final Entity damager) {
        final WormholeWorld wormholeWorld = WorldManager.getWorldFromPlayer(player);
        if ((wormholeWorld != null)) {
            if (!wormholeWorld.isPlayerAllowDamage()) {
                playerStopFire(player);
                playerStopDrown(player);
                return true;
            } else if (cause != null) {
                switch (cause) {
                    case CONTACT:
                        return wormholeWorld.isPlayerAllowContactDamage() ? false : true;
                    case ENTITY_ATTACK:
                        return wormholeWorld.isWorldAllowPvP() ? false : playerStopPvP(damager);
                    case SUFFOCATION:
                        return wormholeWorld.isPlayerAllowSuffocation() ? false : true;
                    case FALL:
                        return wormholeWorld.isPlayerAllowFallDamage() ? false : true;
                    case FIRE:
                    case FIRE_TICK:
                        return wormholeWorld.isPlayerAllowFireDamage() ? false : playerStopFire(player);
                    case LAVA:
                        return wormholeWorld.isPlayerAllowLavaDamage() ? false : playerStopFire(player);
                    case DROWNING:
                        return wormholeWorld.isPlayerAllowDrown() ? false : playerStopDrown(player);
                    case BLOCK_EXPLOSION:
                    case ENTITY_EXPLOSION:
                        return wormholeWorld.isPlayerAllowExplosionDamage() ? false : true;
                    case VOID:
                        return wormholeWorld.isPlayerAllowVoidDamage() ? false : true;
                    case LIGHTNING:
                        return wormholeWorld.isPlayerAllowLightningDamage() ? false : playerStopFire(player);
                    default:
                        break;
                }
            }
        }
        return false;
    }

    /**
     * Player drown stop.
     * 
     * @param player
     *            the player
     */
    private static boolean playerStopDrown(final Player player) {
        if (player.getRemainingAir() == 0) {
            player.setRemainingAir(100);
        }
        return true;
    }

    /**
     * Player fire stop.
     * 
     * @param player
     *            the player
     */
    private static boolean playerStopFire(final Player player) {
        if (player.getFireTicks() > 0) {
            player.setFireTicks(0);
        }
        return true;
    }

    /**
     * Player stop pv p.
     * 
     * @param damager
     *            the damager
     * @return true, if successful
     */
    private static boolean playerStopPvP(final Entity damager) {
        if ((damager != null) && (damager instanceof Player)) {
            ((Player) damager).sendMessage(ResponseType.ERROR_PVP_NOT_ALLOWED.toString());
            return true;
        }
        return false;
    }
}
