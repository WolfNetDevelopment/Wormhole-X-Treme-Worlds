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

import java.util.logging.Level;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageByProjectileEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityListener;

import de.luricos.bukkit.WormholeXTreme.Worlds.WormholeXTremeWorlds;

/**
 * The Class EntityEventHandler.
 * 
 * @author alron
 */
public class EntityEventHandler extends EntityListener {
    /** The this plugin. */
    private final static WormholeXTremeWorlds thisPlugin = WormholeXTremeWorlds.getThisPlugin();

    /* (non-Javadoc)
     * @see org.bukkit.event.entity.EntityListener#onCreatureSpawn(org.bukkit.event.entity.CreatureSpawnEvent)
     */
    @Override
    public void onCreatureSpawn(final CreatureSpawnEvent event) {
        if ( !event.isCancelled() && (event.getEntity() != null) && CreatureSpawn.handleCreatureSpawn(event.getEntity())) {
            event.setCancelled(true);
            thisPlugin.prettyLog(Level.FINEST, false, "Denied creature spawn on world: " + event.getLocation().getWorld().getName() + " creature type: " + event.getCreatureType().toString());
        }
    }

    /* (non-Javadoc)
     * @see org.bukkit.event.entity.EntityListener#onEntityDamage(org.bukkit.event.entity.EntityDamageEvent)
     */
    @Override
    public void onEntityDamage(final EntityDamageEvent event) {
        if ( !event.isCancelled() && (event.getEntity() instanceof Player)) {
            final Entity damager = event instanceof EntityDamageByEntityEvent
                ? ((EntityDamageByEntityEvent) event).getDamager() : event instanceof EntityDamageByProjectileEvent
                    ? ((EntityDamageByProjectileEvent) event).getDamager() : null;
            if (EntityDamage.handleEntityDamage((Player) event.getEntity(), event.getCause(), damager)) {
                event.setCancelled(true);
                thisPlugin.prettyLog(Level.FINEST, false, "Player damage event cancelled on " + ((Player) event.getEntity()).getName() + " on world " + event.getEntity().getWorld().getName());
            }
        }
    }
}
