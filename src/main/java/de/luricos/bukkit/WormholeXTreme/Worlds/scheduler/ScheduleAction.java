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
package de.luricos.bukkit.WormholeXTreme.Worlds.scheduler;

import de.luricos.bukkit.WormholeXTreme.Worlds.utils.WXLogger;
import de.luricos.bukkit.WormholeXTreme.Worlds.world.WorldManager;
import de.luricos.bukkit.WormholeXTreme.Worlds.world.WormholeWorld;

import java.util.logging.Level;

/**
 * The Class ScheduleAction.
 * 
 * @author alron
 */
public class ScheduleAction implements Runnable {

    /**
     * The Enum ActionType.
     */
    public enum ActionType {

        /** The Time lock noon. */
        TimeLock,
        /** The Clear entities. */
        ClearEntities,
        /** The Set weather. */
        SetWeather
    }
    /** The wormhole world. */
    private WormholeWorld wormholeWorld = null;
    /** The action type. */
    private ActionType actionType = null;

    /**
     * Instantiates a new schedule action.
     * 
     * @param actionType
     *            the action type
     */
    public ScheduleAction(final ActionType actionType) {
        setActionType(actionType);
    }

    /**
     * Instantiates a new schedule action.
     * 
     * @param wormholeWorld
     *            the wormhole world
     * @param actionType
     *            the action type
     */
    public ScheduleAction(final WormholeWorld wormholeWorld, final ActionType actionType) {
        setActionType(actionType);
        setWormholeWorld(wormholeWorld);
    }

    /**
     * Gets the action type.
     * 
     * @return the action type
     */
    private ActionType getActionType() {
        return actionType;
    }

    /**
     * Gets the wormhole world.
     * 
     * @return the wormhole world
     */
    private WormholeWorld getWormholeWorld() {
        return wormholeWorld;
    }

    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        if (getActionType() != null) {
            switch (getActionType()) {
                case ClearEntities:
                    if (getWormholeWorld() != null) {
                        WXLogger.prettyLog(Level.FINE, false, "Schedule Action \"" + getActionType().toString() + "\" WormholeWorld \"" + getWormholeWorld().getWorldName() + "\"");
                        WorldManager.clearWorldCreatures(getWormholeWorld());
                    }
                    break;
                case SetWeather:
                    if (getWormholeWorld() != null) {
                        WXLogger.prettyLog(Level.FINE, false, "Schedule Action \"" + getActionType().toString() + "\" WormholeWorld \"" + getWormholeWorld().getWorldName() + "\"");
                        WorldManager.setWorldWeather(getWormholeWorld());
                    }
                    break;
                case TimeLock:
                    WXLogger.prettyLog(Level.FINE, false, "Schedule Action \"" + getActionType().toString() + "\"");
                    WorldManager.checkTimelockWorlds();
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Sets the action type.
     * 
     * @param actionType
     *            the new action type
     */
    private void setActionType(final ActionType actionType) {
        this.actionType = actionType;
    }

    /**
     * Sets the wormhole world.
     * 
     * @param wormholeWorld
     *            the new wormhole world
     */
    private void setWormholeWorld(final WormholeWorld wormholeWorld) {
        this.wormholeWorld = wormholeWorld;
    }
}
