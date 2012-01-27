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
package de.luricos.bukkit.WormholeXTreme.Worlds.block;

import de.luricos.bukkit.WormholeXTreme.Worlds.utils.WXLogger;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockIgniteEvent;

import java.util.logging.Level;

/**
 * The Class BlockEventHandler.
 * 
 * @author alron
 */
public class BlockEventHandler implements Listener {

    /* (non-Javadoc)
     * @see org.bukkit.event.block.BlockListener#onBlockBurn(org.bukkit.event.block.BlockBurnEvent)
     */
    @EventHandler(priority = EventPriority.LOWEST)
    public void onBlockBurn(final BlockBurnEvent event) {
        if (!event.isCancelled() && (event.getBlock() != null) && BlockBurn.handleBlockBurn(event.getBlock())) {
            event.setCancelled(true);
            WXLogger.prettyLog(Level.FINE, false, "Cancelled BlockBurnEvent on " + event.getBlock().getWorld().getName());
        }
    }

    /* (non-Javadoc)
     * @see org.bukkit.event.block.BlockListener#onBlockFromTo(org.bukkit.event.block.BlockFromToEvent)
     */
    @EventHandler(priority = EventPriority.LOWEST)
    public void onBlockFromTo(final BlockFromToEvent event) {
        if (!event.isCancelled() && (event.getBlock() != null) && BlockFromTo.handleBlockFromTo(event.getBlock())) {
            event.setCancelled(true);
            WXLogger.prettyLog(Level.FINE, false, "Cancelled BlockFromTo Event on " + event.getBlock().getWorld().getName());
        }
    }

    /* (non-Javadoc)
     * @see org.bukkit.event.block.BlockListener#onBlockIgnite(org.bukkit.event.block.BlockIgniteEvent)
     */
    @EventHandler(priority = EventPriority.LOWEST)
    public void onBlockIgnite(final BlockIgniteEvent event) {
        if (!event.isCancelled() && (event.getBlock() != null) && BlockIgnite.handleBlockIgnite(event.getBlock(), event.getCause())) {
            event.setCancelled(true);
            WXLogger.prettyLog(Level.FINE, false, "Cancelled BlockIgniteEvent on " + event.getBlock().getWorld().getName());
        }
    }
}
