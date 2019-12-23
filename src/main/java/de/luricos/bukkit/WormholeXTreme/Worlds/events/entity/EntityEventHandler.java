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

import de.luricos.bukkit.WormholeXTreme.Worlds.utils.WXLogger;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.logging.Level;

/**
 * The Class EntityEventHandler.
 * 
 * @author alron
 */
public class EntityEventHandler implements Listener {

    /* (non-Javadoc)
     * @see org.bukkit.event.entity.EntityListener#onCreatureSpawn(org.bukkit.event.entity.CreatureSpawnEvent)
     */
    @EventHandler(priority = EventPriority.LOWEST)
    public void onCreatureSpawn(final CreatureSpawnEvent event) {
        if (!event.isCancelled() && (event.getEntity() != null) && CreatureSpawn.handleCreatureSpawn(event.getEntity())) {
            event.setCancelled(true);
            WXLogger.prettyLog(Level.FINEST, false, "Denied creature spawn on world: " + event.getLocation().getWorld().getName() + " entity type: " + event.getEntityType().toString());
        }
    }

    /* (non-Javadoc)
     * @see org.bukkit.event.entity.EntityListener#onEntityDamage(org.bukkit.event.entity.EntityDamageEvent)
     */
    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.isCancelled())
            return;

        if (event.getEntity() instanceof Player) {
            Entity damager = (event instanceof EntityDamageByEntityEvent) ? ((EntityDamageByEntityEvent) event).getDamager() : null;

            if (EntityDamage.handleEntityDamage((Player) event.getEntity(), event.getCause(), damager)) {
                event.setCancelled(true);
                WXLogger.prettyLog(Level.FINEST, false, "Player damage event cancelled on " + ((Player) event.getEntity()).getName() + " on world " + event.getEntity().getWorld().getName());
            }
        }
    }
}
