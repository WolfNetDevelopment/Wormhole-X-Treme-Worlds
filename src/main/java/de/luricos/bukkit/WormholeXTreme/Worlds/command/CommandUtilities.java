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
package de.luricos.bukkit.WormholeXTreme.Worlds.command;

import de.luricos.bukkit.WormholeXTreme.Worlds.WormholeXTremeWorlds;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * The Class CommandUtilities.
 * 
 * @author alron
 */
public class CommandUtilities {

    /** The Constant thisPlugin. */
    private final static WormholeXTremeWorlds thisPlugin = WormholeXTremeWorlds.getThisPlugin();

    /**
     * Parses the command options and returns a cleaned up array of commands.<br>
     * Quoted commands are joined in the same element.<br>
     * Commands starting with '-' are added the same element as their arguments, with a '|' spacer.<br>
     * Commands starting with '-' but having no arguments are in their own element.<br>
     * 
     * @param args
     *            the args
     * @return the string[]
     */
    static String[] commandCleaner(final String[] args) {
        final String[] escapedArgs = commandEscaper(args);
        StringBuilder tempString = new StringBuilder();
        boolean commandFound = false;
        boolean commandFoundNoArgs = false;
        boolean commandHasArgs = false;
        final ArrayList<String> argsPartsList = new ArrayList<String>();
        for (final String argPart : escapedArgs) {
            // No commands or args found yet, must primary, append
            if (!commandFound && !commandHasArgs && !argPart.startsWith("-")) {
                argsPartsList.add(argPart);
            } // Found command, if command found previously make note of it.
            else if (argPart.startsWith("-")) {
                if (commandFound) {
                    try {
                        Long.valueOf(argPart);
                        commandHasArgs = true;
                        tempString.append("|");
                    } catch (final NumberFormatException e) {
                        commandFoundNoArgs = true;
                    }
                }
                commandFound = true;
            } // Previously a command was found, so we must be an argument to that command.
            else if (commandFound) {
                commandHasArgs = true;
                tempString.append("|");
            }

            // Command was found
            if (commandFound) {
                // Previous run found a command too, lets consider the previous command argument free.
                // Push previous command into argument Array and wipe the tempString.
                if (commandFoundNoArgs) {
                    argsPartsList.add(tempString.toString());
                    tempString = new StringBuilder();
                    commandFoundNoArgs = false;
                }
                // Must be either a command or a argument to it, either way it needs to be part of the tempString.
                tempString.append(argPart);
                if (commandHasArgs) {
                    //We are an argument at this point, this means we need to add the command and argument pair to the 
                    // Array and wipe the tempString.
                    argsPartsList.add(tempString.toString());
                    tempString = new StringBuilder();
                    commandHasArgs = false;
                    commandFound = false;
                }
            }
        }
        // Looks like we finished on an argument less command, add it before we return the array.
        if ((tempString != null) && (tempString.length() > 0)) {
            argsPartsList.add(tempString.toString());
        }
        return argsPartsList.toArray(new String[argsPartsList.size()]);
    }

    /**
     * Command escaper.
     * Checks for " and escapes it.
     * 
     * @param args
     *            The String[] argument list to escape quotes on.
     * @return String[] with properly escaped quotes.
     */
    private static String[] commandEscaper(final String[] args) {
        StringBuilder tempString = new StringBuilder();
        boolean startQuoteFound = false;
        boolean endQuoteFound = false;

        final ArrayList<String> argsPartsList = new ArrayList<String>();

        for (final String part : args) {
            // First check to see if we have a starting or stopping quote
            if (part.contains("\"") && !startQuoteFound) {
                // Two quotes in same string = no spaces in quoted text;
                if (!part.replaceFirst("\"", "").contains("\"")) {
                    startQuoteFound = true;
                }
            } else if (part.contains("\"") && startQuoteFound) {
                endQuoteFound = true;
            }

            // If no quotes yet, we just append to list
            if (!startQuoteFound) {
                argsPartsList.add(part);
            }

            // If we have quotes we should make sure to append the values
            // if we found the last quote we should stop adding.
            if (startQuoteFound) {
                tempString.append(part.replace("\"", ""));
                if (endQuoteFound) {
                    argsPartsList.add(tempString.toString());
                    startQuoteFound = false;
                    endQuoteFound = false;
                    tempString = new StringBuilder();
                } else {
                    tempString.append(" ");
                }
            }
        }
        return argsPartsList.toArray(new String[argsPartsList.size()]);
    }

    /**
     * Command remover.
     * Removes the first argument from the array.
     * 
     * @param args
     *            the args
     * @return the string[]
     */
    static String[] commandRemover(final String[] args) {
        String[] tempString = new String[]{""};
        final ArrayList<String> argsMinusCommand = new ArrayList<String>();
        if (args.length > 1) {
            argsMinusCommand.addAll(Arrays.asList(args).subList(1, args.length));
            tempString = argsMinusCommand.toArray(new String[argsMinusCommand.size()]);
        }
        return tempString;
    }

    /**
     * Player check.
     * 
     * @param sender
     *            the sender
     * @return true, if successful
     */
    static boolean playerCheck(final CommandSender sender) {
        return (sender instanceof Player);
    }

    /**
     * Register commands.
     */
    public static void registerCommands(Plugin plugin) {
        ((WormholeXTremeWorlds) plugin).getCommand("wxw").setExecutor(new Wxw());
    }
}
