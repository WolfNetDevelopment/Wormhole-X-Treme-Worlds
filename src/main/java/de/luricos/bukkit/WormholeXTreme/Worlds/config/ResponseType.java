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
package de.luricos.bukkit.WormholeXTreme.Worlds.config;

/**
 * The Enum ResponseType for sending consistent messages.
 */
public enum ResponseType {

    /** HEADER. */
    HEADER("\u00A73:: "),
    /** FOOTER. */
    FOOTER("\u00A77"),
    /** The NORMAL HEADER for normal messages. */
    NORMAL_HEADER(HEADER.toString() + FOOTER.toString()),
    /** The ERROR HEADER for error messages. */
    ERROR_HEADER(HEADER.toString() + "\u00A75error " + NORMAL_HEADER.toString()),
    /** The NO PERMISSION ERROR message. */
    ERROR_PERMISSION_NO(ERROR_HEADER.toString() + "You lack the permission to do this."),
    /** The IN GAME ONLY ERROR message. */
    ERROR_IN_GAME_ONLY(ERROR_HEADER.toString() + "This command requires a player to call it, not a console."),
    /** The WORLD DOES NOT EXIST ERROR message. */
    ERROR_WORLD_NOT_EXIST(ERROR_HEADER.toString() + "World does not exist or is not loaded: "),
    /** The WORLD ALLREADY EXISTS ERROR message. */
    ERROR_WORLD_ALLREADY_EXISTS(ERROR_HEADER.toString() + "World allready exists: "),
    /** The WORLD MAY BE ON DISK ERROR message. */
    ERROR_WORLD_MAY_BE_ON_DISK(ERROR_HEADER.toString() + "World may exist on disk, but has not been registered with Wormhole X-Treme Worlds."),
    /** The NO ARGS GIVEN ERROR message. */
    ERROR_COMMAND_REQUIRES_ARGS(ERROR_HEADER.toString() + "Command specified requires arguments: "),
    NORMAL_MODIFY_COMMAND_ARGS1(NORMAL_HEADER.toString() + "\u00A75Req\u00A77: -na(me) <world> \u00A75Opt\u00A77: -owner <player>,"),
    NORMAL_MODIFY_COMMAND_ARGS2(NORMAL_HEADER.toString() + "-ti(me) [day|night|\u00A76none\u00A77], -(no)\u00A76autoload\u00A77, -(no)\u00A76lavaspread\u00A77,"),
    NORMAL_MODIFY_COMMAND_ARGS3(NORMAL_HEADER.toString() + "-(no)\u00A76neutrals\u00A77, -(no)\u00A76firespread\u00A77, -(no)\u00A76lavafire\u00A77, -(no)\u00A76hostiles\u00A77,"),
    NORMAL_MODIFY_COMMAND_ARGS4(NORMAL_HEADER.toString() + "-(no)\u00A76waterspread\u00A77, -(no)\u00A76lightningfire\u00A77, -(no)\u00A76lightningdamage\u00A77,"),
    NORMAL_MODIFY_COMMAND_ARGS5(NORMAL_HEADER.toString() + "-(no)\u00A76damage\u00A77, -(no)\u00A76drown\u00A77, -(no)\u00A76pvp\u00A77, -(no)\u00A76lavadamage\u00A77,"),
    NORMAL_MODIFY_COMMAND_ARGS6(NORMAL_HEADER.toString() + "-(no)\u00A76falldamage\u00A77, -(no)\u00A76firedamage\u00A77, -we(ather) (clear|rain|storm|\u00A76none\u00A77)"),
    NORMAL_CREATE_COMMAND_ARGS1(NORMAL_HEADER.toString() + "\u00A75Req\u00A77: -na(me) <world> \u00A75Opt\u00A77: -se(ed) <num|string>,"),
    NORMAL_CREATE_COMMAND_ARGS2(NORMAL_HEADER.toString() + "-pl(ayer) all,contact,drown,explosion,fall,fire,lava,lightning, "),
    NORMAL_CREATE_COMMAND_ARGS3(NORMAL_HEADER.toString() + "   suffocation,void"),
    NORMAL_CREATE_COMMAND_ARGS4(NORMAL_HEADER.toString() + "-wo(rld) fire,firespread,lavafire,lightningfire,pvp,waterspread,"),
    NORMAL_CREATE_COMMAND_ARGS5(NORMAL_HEADER.toString() + "   firestart,hostiles,neutrals,autoload,\u00A76normal\u00A77,nether,the_end"),
    NORMAL_CREATE_COMMAND_ARGS6(NORMAL_HEADER.toString() + "-ti(me) (day|night) -we(ather) (clear|rain|storm)"),
    /** The COMMAND REQUIRES A WORLD NAME ERROR message. */
    ERROR_COMMAND_REQUIRES_WORLDNAME(ERROR_HEADER.toString() + "Command requires world name: "),
    /** The COMMAND REQUIRES NUMBER VALUE ERROR message. */
    ERROR_COMMAND_REQUIRES_NUMBER(ERROR_HEADER.toString() + "Command requires numeric value: "),
    /** The COMMAND ONLY AVAILABLE ON MANAGED WORLDS message. */
    ERROR_COMMAND_ONLY_MANAGED_WORLD(ERROR_HEADER.toString() + "Command only available on managed worlds."),
    ERROR_OPTION_REQUIRES_ARGS(ERROR_HEADER.toString() + "Option requires arguments: "),
    ERROR_OPTION_REQUIRES_ONE_ARG(ERROR_HEADER.toString() + "Option only takes one argument: "),
    ERROR_ARG_NOT_VALID(ERROR_HEADER.toString() + "Argument is not valid: "),
    ERROR_PVP_NOT_ALLOWED(ERROR_HEADER.toString() + "PvP \u00A74NOT" + FOOTER.toString() + " allowed on this world: ");
    /** The response string. */
    private final String responseString;

    /**
     * Instantiates a new response type.
     * 
     * @param responseString
     *            the response string
     */
    private ResponseType(final String responseString) {
        this.responseString = responseString;
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return responseString;
    }
}
