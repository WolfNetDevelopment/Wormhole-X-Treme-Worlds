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
package de.luricos.bukkit.WormholeXTreme.Worlds.events.entity;

import de.luricos.bukkit.WormholeXTreme.Worlds.config.ResponseType;
import de.luricos.bukkit.WormholeXTreme.Worlds.world.WormholeWorld;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import de.luricos.bukkit.WormholeXTreme.Worlds.world.WorldManager;

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

        if (!wormholeWorld.isPlayerAllowDamage()) {
            playerStopFire(player);
            playerStopDrown(player);
            return true;
        }

        if ((wormholeWorld == null) || (cause == null))
            return false;

        switch (cause) {
            case CONTACT:
                return !wormholeWorld.isPlayerAllowContactDamage();
            case ENTITY_ATTACK:
                return !wormholeWorld.isWorldAllowPvP() && playerStopPvP(damager);
            case SUFFOCATION:
                return !wormholeWorld.isPlayerAllowSuffocation();
            case FALL:
                return !wormholeWorld.isPlayerAllowFallDamage();
            case FIRE:
            case FIRE_TICK:
                return !wormholeWorld.isPlayerAllowFireDamage() && playerStopFire(player);
            case LAVA:
                return !wormholeWorld.isPlayerAllowLavaDamage() && playerStopFire(player);
            case DROWNING:
                return !wormholeWorld.isPlayerAllowDrown() && playerStopDrown(player);
            case BLOCK_EXPLOSION:
            case ENTITY_EXPLOSION:
                return !wormholeWorld.isPlayerAllowExplosionDamage();
            case VOID:
                return !wormholeWorld.isPlayerAllowVoidDamage();
            case LIGHTNING:
                return !wormholeWorld.isPlayerAllowLightningDamage() && playerStopFire(player);
            case STARVATION:
                break;
            default:
                break;
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
