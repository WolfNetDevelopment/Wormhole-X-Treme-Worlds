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
package de.luricos.bukkit.WormholeXTreme.Worlds.config;

import de.luricos.bukkit.WormholeXTreme.Worlds.config.ConfigManager.ServerOptionKeys;

/**
 * The Class Option.
 * 
 * @author alron
 */
public class ServerOption {

    /** The option key. */
    private ServerOptionKeys optionKey;
    /** The option description. */
    private String optionDescription;
    /** The option type. */
    private String optionType;
    /** The option value. */
    private Object optionValue;

    /**
     * Instantiates a new option.
     * 
     * @param optionKey
     *            the option key
     * @param optionDescription
     *            the option description
     * @param optionType
     *            the option type
     * @param optionValue
     *            the option value
     */
    protected ServerOption(final ServerOptionKeys optionKey, final String optionDescription, final String optionType, final Object optionValue) {
        if ((optionKey != null) && (optionDescription != null) && (optionValue != null)) {
            setOptionKey(optionKey);
            setOptionDescription(optionDescription);
            setOptionType(optionType);
            setOptionValue(optionValue);
        }
    }

    /**
     * Gets the option description.
     * 
     * @return the option description
     */
    public String getOptionDescription() {
        return optionDescription;
    }

    /**
     * Gets the option key.
     * 
     * @return the option key
     */
    public ServerOptionKeys getOptionKey() {
        return optionKey;
    }

    /**
     * Gets the option type.
     * 
     * @return the option type
     */
    public String getOptionType() {
        return optionType;
    }

    /**
     * Gets the option value.
     * 
     * @return the option value
     */
    public Object getOptionValue() {
        return optionValue;
    }

    /**
     * Sets the option description.
     * 
     * @param optionDescription
     *            the new option description
     */
    private void setOptionDescription(final String optionDescription) {
        this.optionDescription = optionDescription;
    }

    /**
     * Sets the option key.
     * 
     * @param optionKey
     *            the new option key
     */
    private void setOptionKey(final ServerOptionKeys optionKey) {
        this.optionKey = optionKey;
    }

    /**
     * Sets the option type.
     * 
     * @param optionType
     *            the new option type
     */
    private void setOptionType(final String optionType) {
        this.optionType = optionType;
    }

    /**
     * Sets the option value.
     * 
     * @param optionValue
     *            the new option value
     */
    public void setOptionValue(final Object optionValue) {
        this.optionValue = optionValue;
    }
}
