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

import de.luricos.bukkit.WormholeXTreme.Worlds.config.ConfigManager.ServerOptionKeys;
import de.luricos.bukkit.WormholeXTreme.Worlds.exceptions.WorldsConfigurationException;
import de.luricos.bukkit.WormholeXTreme.Worlds.utils.WXLogger;
import de.luricos.bukkit.WormholeXTreme.Worlds.world.TimeLockType;
import de.luricos.bukkit.WormholeXTreme.Worlds.world.WeatherLockType;
import de.luricos.bukkit.WormholeXTreme.Worlds.world.WorldManager;
import de.luricos.bukkit.WormholeXTreme.Worlds.world.WormholeWorld;

import org.bukkit.plugin.PluginDescriptionFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.logging.Level;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import org.bukkit.entity.Wolf;

/**
 * The Class WormholeXTtremeWorldsConfig.
 * 
 * @author alron
 */
public class XMLConfig {

    /** The config file. */
    private static File configFile = null;
    /** The config directory. */
    private static File configDirectory = null;
    /** The world config directory. */
    private static File worldConfigDirectory = null;

    /**
     * Creates the node.
     * 
     * @param eventWriter
     *            the event writer
     * @param name
     *            the name
     * @param type
     *            the type
     * @param value
     *            the value
     * @param description
     *            the description
     * @throws XMLStreamException
     *             the xML stream exception
     */
    private static void createConfigNode(final XMLEventWriter eventWriter, final String name, final String type, final String value, final String description) throws XMLStreamException {
        final XMLEventFactory eventFactory = XMLEventFactory.newInstance();
        final XMLEvent end = eventFactory.createDTD("\n");
        final XMLEvent tab = eventFactory.createDTD("\t");

        eventWriter.add(tab);
        eventWriter.add(eventFactory.createComment(description));
        eventWriter.add(end);
        eventWriter.add(tab);
        eventWriter.add(eventFactory.createStartElement("", "", name));
        eventWriter.add(end);
        eventWriter.add(tab);
        eventWriter.add(tab);
        eventWriter.add(eventFactory.createStartElement("", "", "type"));
        eventWriter.add(eventFactory.createCharacters(type));
        eventWriter.add(eventFactory.createEndElement("", "", "type"));
        eventWriter.add(end);
        eventWriter.add(tab);
        eventWriter.add(tab);
        eventWriter.add(eventFactory.createStartElement("", "", "value"));
        eventWriter.add(eventFactory.createCharacters(value));
        eventWriter.add(eventFactory.createEndElement("", "", "value"));
        eventWriter.add(end);
        eventWriter.add(tab);
        eventWriter.add(eventFactory.createEndElement("", "", name));
        eventWriter.add(end);
        eventWriter.add(end);
    }

    private static void createHeaderFooter(final XMLEventWriter eventWriter, final boolean header) throws XMLStreamException {
        final XMLEventFactory eventFactory = XMLEventFactory.newFactory();
        final XMLEvent end = eventFactory.createDTD("\n");
        if (header) {
            eventWriter.add(eventFactory.createStartDocument());
            eventWriter.add(end);
            eventWriter.add(eventFactory.createStartElement("", "", "WormholeXTremeWorlds"));
            eventWriter.add(end);
        } else {
            eventWriter.add(eventFactory.createEndElement("", "", "WormholeXTremeWorlds"));
            eventWriter.add(end);
            eventWriter.add(eventFactory.createEndDocument());

        }
    }

    /**
     * @param worldName
     */
    public static void deleteXmlWorldConfig(final String worldName) {
        final File deleteWorld = new File(getWorldConfigDirectory() + File.separator + worldName + ".xml");
        if (deleteWorld.exists()) {
            if (!deleteWorld.delete()) {
                WXLogger.prettyLog(Level.WARNING, false, "Unable to delete config file for world: " + worldName);
            }
        }
    }

    /**
     * Gets the config directory.
     * 
     * @return the config directory
     */
    private static File getConfigDirectory() {
        return configDirectory;
    }

    /**
     * Gets the config file.
     * 
     * @return the config file
     */
    private static File getConfigFile() {
        return configFile;
    }

    /**
     * Gets the world config directory.
     * 
     * @return the worldConfigDirectory
     */
    private static File getWorldConfigDirectory() {
        return worldConfigDirectory;
    }

    /**
     * Load xml config.
     * 
     * @param desc
     *            the {@link PluginDescriptionFile}
     */
    public static void loadXmlConfig(final PluginDescriptionFile desc) {
        try {
            readXmlConfig(desc);
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Prepare config directories.
     * 
     * @param desc
     *            the desc
     * @return true, if successful
     * @throws FileNotFoundException
     *             the file not found exception
     * @throws XMLStreamException
     *             the xML stream exception
     */
    private static boolean prepareConfig(final PluginDescriptionFile desc) throws FileNotFoundException, XMLStreamException {
        setConfigDirectory(new File("plugins" + File.separator + desc.getName() + File.separator));
        setWorldConfigDirectory(new File(getConfigDirectory().getPath() + File.separator + "worlds" + File.separator));
        setConfigFile(new File(getConfigDirectory().getPath() + File.separator + "config.xml"));

        if (!getConfigDirectory().exists()) {
            if (getConfigDirectory().mkdir()) {
                WXLogger.prettyLog(Level.CONFIG, false, "Created config directory: " + getConfigDirectory().toString());
            } else {
                WXLogger.prettyLog(Level.SEVERE, true, "Uable to create config directory: " + getConfigDirectory().toString());
                return false;
            }
        }
        if (!getWorldConfigDirectory().exists()) {
            if (getWorldConfigDirectory().mkdir()) {
                WXLogger.prettyLog(Level.CONFIG, false, "Created world config directory: " + getWorldConfigDirectory().toString());
            } else {
                WXLogger.prettyLog(Level.SEVERE, true, "Uable to create world config directory: " + getWorldConfigDirectory().toString());
                return false;
            }
        }
        if (!getConfigFile().exists()) {
            WXLogger.prettyLog(Level.WARNING, false, "No configuration file found, generating fresh.");
            FileOutputStream fileOutputStream = null;
            try {
                fileOutputStream = new FileOutputStream(getConfigFile());
                saveServerConfigFile(fileOutputStream, DefaultOptions.defaultServerOptions);
            } finally {
                try {
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                    }
                } catch (final IOException e) {
                }
            }
        }
        return true;
    }

    /**
     * Read config.
     * 
     * @param fileInputStream
     *            the file input stream
     * @throws XMLStreamException
     *             the xML stream exception
     * @throws FactoryConfigurationError
     *             the factory configuration error
     */
    private static void readConfigFile(final FileInputStream fileInputStream) throws XMLStreamException, FactoryConfigurationError {
        final XMLEventReader eventReader = XMLInputFactory.newInstance().createXMLEventReader(fileInputStream);
        XMLEvent event;

        while (eventReader.hasNext()) {
            ServerOptionKeys optionName = null;
            String optionType = null;
            Object optionValue = null;
            String optionDescription = null;
            boolean v = false;
            boolean t = false;
            int i = 0;
            while ((i < 2) && eventReader.hasNext()) {
                event = eventReader.nextEvent();
                if (event.isStartElement()) {
                    final String elementType = event.asStartElement().getName().toString();
                    if (elementType.equals("type")) {
                        t = true;
                    } else if (elementType.equals("value")) {
                        v = true;
                    } else if (elementType.startsWith("serverOption")) {
                        try {
                            optionName = ServerOptionKeys.valueOf(elementType);
                        } catch (final IllegalArgumentException e) {
                            optionName = null;
                        }
                    }
                } else if (event.isCharacters() && t) {
                    t = false;
                    i++;
                    optionType = event.asCharacters().getData();
                } else if (event.isCharacters() && v) {
                    v = false;
                    i++;
                    optionValue = event.asCharacters().getData();
                }
            }

            if (optionName != null) {
                final ServerOption[] defaultOptions = DefaultOptions.defaultServerOptions.clone();
                for (final ServerOption defaultOption : defaultOptions) {
                    if (defaultOption.getOptionKey() == optionName) {
                        if ((optionType == null) || (!optionType.equals(defaultOption.getOptionType()))) {
                            optionType = defaultOption.getOptionType();
                        }
                        if ((optionValue == null) || !verifyOptionValue(optionType, optionValue)) {
                            optionValue = defaultOption.getOptionValue().toString();
                        }
                        optionDescription = defaultOption.getOptionDescription();
                        break;
                    }
                }

                WXLogger.prettyLog(Level.CONFIG, false, "Got from XML read: " + optionName + ", " + optionDescription + ", " + optionType + ", " + optionValue);
                ConfigManager.serverOptions.put(optionName, new ServerOption(optionName, optionDescription, optionType, optionValue));
            }
        }
        for (final ServerOption defaultOption : DefaultOptions.defaultServerOptions) {
            if (!ConfigManager.serverOptions.containsKey(defaultOption.getOptionKey())) {
                ConfigManager.serverOptions.put(defaultOption.getOptionKey(), defaultOption);
                WXLogger.prettyLog(Level.CONFIG, false, "Added default config for missing ServerOption: " + defaultOption.getOptionKey().toString());
            }
        }
    }

    /**
     * Read world config file.
     * 
     * @param fileInputStream
     *            the file input stream
     * @throws XMLStreamException
     *             the xML stream exception
     * @throws FactoryConfigurationError
     *             the factory configuration error
     */
    private static void readWorldConfigFile(final FileInputStream fileInputStream) throws XMLStreamException, FactoryConfigurationError {
        final XMLEventReader eventReader = XMLInputFactory.newInstance().createXMLEventReader(fileInputStream);
        XMLEvent event;
        final WormholeWorld wormholeWorld = new WormholeWorld();
        while (eventReader.hasNext()) {
            String optionName = null;
            String optionType = null;
            Object optionValue = null;
            boolean v = false;
            boolean t = false;
            int i = 0;
            while ((i < 2) && eventReader.hasNext()) {
                event = eventReader.nextEvent();
                if (event.isStartElement()) {
                    final String elementType = event.asStartElement().getName().toString();
                    if (elementType.equals("type")) {
                        t = true;
                    } else if (elementType.equals("value")) {
                        v = true;
                    } else {
                        optionName = elementType;
                    }
                } else if (event.isCharacters() && t) {
                    t = false;
                    i++;
                    optionType = event.asCharacters().getData();
                } else if (event.isCharacters() && v) {
                    v = false;
                    i++;
                    optionValue = event.asCharacters().getData();
                }
            }
            if ((optionName != null) && (optionValue != null)) {
                WXLogger.prettyLog(Level.CONFIG, false, "Got from World XML read: " + optionName + ", " + optionType + ", " + optionValue);
                if (optionName.equals("playerAllowContactDamage")) {
                    wormholeWorld.setPlayerAllowContactDamage(Boolean.valueOf(optionValue.toString().trim().toLowerCase()));
                } else if (optionName.equals("allowPlayerDamage") || optionName.equals("playerAllowDamage")) {
                    wormholeWorld.setPlayerAllowDamage(Boolean.valueOf(optionValue.toString().trim().toLowerCase()));
                } else if (optionName.equals("allowPlayerDrown") || optionName.equals("playerAllowDrown")) {
                    wormholeWorld.setPlayerAllowDrown(Boolean.valueOf(optionValue.toString().trim().toLowerCase()));
                } else if (optionName.equals("playerAllowExplosionDamage")) {
                    wormholeWorld.setPlayerAllowExplosionDamage(Boolean.valueOf(optionValue.toString().trim().toLowerCase()));
                } else if (optionName.equals("allowPlayerFallDamage") || optionName.equals("playerAllowFallDamage")) {
                    wormholeWorld.setPlayerAllowFallDamage(Boolean.valueOf(optionValue.toString().trim().toLowerCase()));
                } else if (optionName.equals("allowPlayerFireDamage") || optionName.equals("playerAllowFireDamage")) {
                    wormholeWorld.setPlayerAllowFireDamage(Boolean.valueOf(optionValue.toString().trim().toLowerCase()));
                } else if (optionName.equals("allowPlayerLavaDamage") || optionName.equals("playerAllowLavaDamage")) {
                    wormholeWorld.setPlayerAllowLavaDamage(Boolean.valueOf(optionValue.toString().trim().toLowerCase()));
                } else if (optionName.equals("allowPlayerLightningDamage") || optionName.equals("playerAllowLightningDamage")) {
                    wormholeWorld.setPlayerAllowLightningDamage(Boolean.valueOf(optionValue.toString().trim().toLowerCase()));
                } else if (optionName.equals("playerAllowSuffocation")) {
                    wormholeWorld.setPlayerAllowSuffocation(Boolean.valueOf(optionValue.toString().trim().toLowerCase()));
                } else if (optionName.equals("playerAllowVoidDamage")) {
                    wormholeWorld.setPlayerAllowVoidDamage(Boolean.valueOf(optionValue.toString().trim().toLowerCase()));
                } else if (optionName.equals("worldAllowFire")) {
                    wormholeWorld.setWorldAllowFire(Boolean.valueOf(optionValue.toString().trim().toLowerCase()));
                } else if (optionName.equals("allowFireSpread") || optionName.equals("worldAllowFireSpread")) {
                    wormholeWorld.setWorldAllowFireSpread(Boolean.valueOf(optionValue.toString().trim().toLowerCase()));
                } else if (optionName.equals("allowLavaFire") || optionName.equals("worldAllowLavaFire")) {
                    wormholeWorld.setWorldAllowLavaFire(Boolean.valueOf(optionValue.toString().trim().toLowerCase()));
                } else if (optionName.equals("allowLavaSpread") || optionName.equals("worldAllowLavaSpread")) {
                    wormholeWorld.setWorldAllowLavaSpread(Boolean.valueOf(optionValue.toString().trim().toLowerCase()));
                } else if (optionName.equals("allowLightningFire") || optionName.equals("worldAllowLightningFire")) {
                    wormholeWorld.setWorldAllowLightningFire(Boolean.valueOf(optionValue.toString().trim().toLowerCase()));
                } else if (optionName.equals("worldAllowPlayerStartFire")) {
                    wormholeWorld.setWorldAllowPlayerStartFire(Boolean.valueOf(optionValue.toString().trim().toLowerCase()));
                } else if (optionName.equals("allowPvP") || optionName.equals("worldAllowPvP")) {
                    wormholeWorld.setWorldAllowPvP(Boolean.valueOf(optionValue.toString().trim().toLowerCase()));
                } else if (optionName.equals("allowHostiles") || optionName.equals("worldAllowSpawnHostiles")) {
                    wormholeWorld.setWorldAllowSpawnHostiles(Boolean.valueOf(optionValue.toString().trim().toLowerCase()));
                } else if (optionName.equals("allowNeutrals") || optionName.equals("worldAllowSpawnNeutrals")) {
                    wormholeWorld.setWorldAllowSpawnNeutrals(Boolean.valueOf(optionValue.toString().trim().toLowerCase()));
                } else if (optionName.equals("allowWaterSpread") || optionName.equals("worldAllowWaterSpread")) {
                    wormholeWorld.setWorldAllowWaterSpread(Boolean.valueOf(optionValue.toString().trim().toLowerCase()));
                } else if (optionName.equals("autoconnectWorld") || optionName.equals("worldAutoload")) {
                    wormholeWorld.setWorldAutoload(Boolean.valueOf(optionValue.toString().trim().toLowerCase()));
                } else if (optionName.equals("worldCustomSpawn")) {
                    final int[] wcs = {
                        Integer.valueOf(String.valueOf(optionValue).trim().split("\\|")[0]),
                        Integer.valueOf(String.valueOf(optionValue).trim().split("\\|")[1]),
                        Integer.valueOf(String.valueOf(optionValue).trim().split("\\|")[2])
                    };
                    wormholeWorld.setWorldCustomSpawn(wcs);
                } else if (optionName.equals("worldName")) {
                    wormholeWorld.setWorldName(String.valueOf(optionValue).trim());
                } else if (optionName.equals("worldSeed")) {
                    try {
                        wormholeWorld.setWorldSeed(Long.valueOf(optionValue.toString().trim()));
                    } catch (final NumberFormatException e) {
                        wormholeWorld.setWorldSeed(Long.valueOf(optionValue.toString().trim().hashCode()));
                    }
                } else if (optionName.equals("timeLockType") || optionName.equals("worldTimeLockType")) {
                    wormholeWorld.setWorldTimeLockType(TimeLockType.getTimeType(String.valueOf(optionValue).trim().toUpperCase()));
                } else if (optionName.equals("normalWorld") || optionName.equals("worldTypeNormal")) {
                    wormholeWorld.setWorldTypeNormal(Boolean.valueOf(optionValue.toString().trim().toLowerCase()));
                } else if (optionName.equals("netherWorld") || optionName.equals("worldTypeNether")) {
                    wormholeWorld.setWorldTypeNether(Boolean.valueOf(optionValue.toString().trim().toLowerCase()));
                } else if (optionName.equals("skylandsWorld") || optionName.equals("worldTypeSkylands")) {
                    wormholeWorld.setWorldTypeSkylands(Boolean.valueOf(optionValue.toString().trim().toLowerCase()));
                } else if (optionName.equals("weatherLockType") || optionName.equals("worldWeatherLockType")) {
                    wormholeWorld.setWorldWeatherLockType(WeatherLockType.getWeatherType(String.valueOf(optionValue).trim().toUpperCase()));
                }
            }
        }
        
        if ((wormholeWorld.getWorldName() != null) && (wormholeWorld.getWorldCustomSpawn() != null)) {
            WorldManager.addWorld(wormholeWorld);
        }
    }

    /**
     * Read xml config.
     * 
     * @param desc
     *            the {@link PluginDescriptionFile}
     * @throws FileNotFoundException
     *             the file not found exception
     * @throws XMLStreamException
     *             the xML stream exception
     */
    private static void readXmlConfig(final PluginDescriptionFile desc) throws FileNotFoundException, XMLStreamException {
        if (!prepareConfig(desc)) {
            return;
        }
        if (getConfigFile().exists()) {
            FileInputStream fileInputStream = null;
            try {
                fileInputStream = new FileInputStream(getConfigFile());
                readConfigFile(fileInputStream);
                WXLogger.prettyLog(Level.INFO, false, "Config Loaded");
            } finally {
                try {
                    if (fileInputStream != null) {
                        fileInputStream.close();
                    }
                } catch (final IOException e) {
                }
            }
        }
        if (getWorldConfigDirectory().exists()) {
            for (final File worldFile : getWorldConfigDirectory().listFiles()) {
                if (!worldFile.isDirectory() && !worldFile.isHidden() && worldFile.getPath().endsWith("xml")) {
                    FileInputStream fileInputStream = null;
                    try {
                        fileInputStream = new FileInputStream(worldFile);
                        readWorldConfigFile(fileInputStream);
                        WXLogger.prettyLog(Level.INFO, false, "World Config Loaded: " + worldFile.getName());
                    } finally {
                        try {
                            if (fileInputStream != null) {
                                fileInputStream.close();
                            }
                        } catch (final IOException e) {
                        }
                    }
                }
            }
        }
    }

    private static void saveServerConfigFile(final FileOutputStream fileOutputStream, final ServerOption[] options) throws XMLStreamException {
        final XMLEventWriter eventWriter = XMLOutputFactory.newInstance().createXMLEventWriter(fileOutputStream);
        createHeaderFooter(eventWriter, true);
        for (final ServerOption element : options) {
            createConfigNode(eventWriter, element.getOptionKey().toString(), element.getOptionType(), element.getOptionValue().toString(), element.getOptionDescription());
        }
        createHeaderFooter(eventWriter, false);
        eventWriter.close();
    }

    private static void saveWorldConfigFile(final FileOutputStream fileOutputStream, final WormholeWorld world) throws XMLStreamException {
        final XMLEventWriter eventWriter = XMLOutputFactory.newInstance().createXMLEventWriter(fileOutputStream);
        createHeaderFooter(eventWriter, true);
        createConfigNode(eventWriter, "worldName", "String", world.getWorldName(), "The name of this world. Do not change unless you have renamed the world on disk.");
        createConfigNode(eventWriter, "worldTypeNormal", "boolean", Boolean.valueOf(world.isWorldTypeNormal()).toString(), "Is this a normal world? BE SURE TO HAVE THIS RIGHT! Set only ONE type to true others to false!");
        createConfigNode(eventWriter, "worldTypeNether", "boolean", Boolean.valueOf(world.isWorldTypeNether()).toString(), "Is this a nether world? BE SURE TO HAVE THIS RIGHT! Set only ONE type to true others to false!");
        createConfigNode(eventWriter, "worldTypeSkylands", "boolean", Boolean.valueOf(world.isWorldTypeSkylands()).toString(), "Is this a skylands world? BE SURE TO HAVE THIS RIGHT! Set only ONE type to true others to false!");
        createConfigNode(eventWriter, "worldAutoload", "boolean", Boolean.valueOf(world.isWorldAutoload()).toString(), "Does this world automatically get loaded at server start? Non connected worlds can be loaded in game as needed.");
        createConfigNode(eventWriter, "worldAllowFire", "boolean", Boolean.valueOf(world.isWorldAllowFire()).toString(), "Is fire allowed on this world?");
        createConfigNode(eventWriter, "worldAllowFireSpread", "boolean", Boolean.valueOf(world.isWorldAllowFireSpread()).toString(), "Is fire spread allowed on this world?");
        createConfigNode(eventWriter, "worldAllowLavaFire", "boolean", Boolean.valueOf(world.isWorldAllowLavaFire()).toString(), "Is lava fire allowed on this world?");
        createConfigNode(eventWriter, "worldAllowLavaSpread", "boolean", Boolean.valueOf(world.isWorldAllowLavaSpread()).toString(), "Is lava spread allowed on this world?");
        createConfigNode(eventWriter, "worldAllowLightningFire", "boolean", Boolean.valueOf(world.isWorldAllowLightningFire()).toString(), "Is lightning fire allowed on this world?");
        createConfigNode(eventWriter, "worldAllowPlayerStartFire", "boolean", Boolean.valueOf(world.isWorldAllowPlayerStartFire()).toString(), "Can players start fires on this world?");
        createConfigNode(eventWriter, "worldAllowSpawnHostiles", "boolean", Boolean.valueOf(world.isWorldAllowSpawnHostiles()).toString(), "Are hostiles allowed on this world?");
        createConfigNode(eventWriter, "worldAllowSpawnNeutrals", "boolean", Boolean.valueOf(world.isWorldAllowSpawnNeutrals()).toString(), "Are neutrals allowed on this world?");
        createConfigNode(eventWriter, "worldAllowWaterSpread", "boolean", Boolean.valueOf(world.isWorldAllowWaterSpread()).toString(), "Does water spread happen on this world?");
        createConfigNode(eventWriter, "playerAllowContactDamage", "boolean", Boolean.valueOf(world.isPlayerAllowContactDamage()).toString(), "Can players take contact damage on this world?");
        createConfigNode(eventWriter, "playerAllowDamage", "boolean", Boolean.valueOf(world.isPlayerAllowDamage()).toString(), "Can players take damage on this world?");
        createConfigNode(eventWriter, "playerAllowDrown", "boolean", Boolean.valueOf(world.isPlayerAllowDrown()).toString(), "Can players drown on this world?");
        createConfigNode(eventWriter, "playerAllowExplosionDamage", "boolean", Boolean.valueOf(world.isPlayerAllowExplosionDamage()).toString(), "Can players take explosion damage on this world?");
        createConfigNode(eventWriter, "playerAllowFallDamage", "boolean", Boolean.valueOf(world.isPlayerAllowFallDamage()).toString(), "Do players take fall damage on this world?");
        createConfigNode(eventWriter, "playerAllowFireDamage", "boolean", Boolean.valueOf(world.isPlayerAllowFireDamage()).toString(), "Do players take fire damage on this world?");
        createConfigNode(eventWriter, "playerAllowLavaDamage", "boolean", Boolean.valueOf(world.isPlayerAllowLavaDamage()).toString(), "Do players take lava damage on this world?");
        createConfigNode(eventWriter, "playerAllowLightningDamage", "boolean", Boolean.valueOf(world.isPlayerAllowLightningDamage()).toString(), "Do players take damage from lightning on this world?");
        createConfigNode(eventWriter, "playerAllowSuffocation", "boolean", Boolean.valueOf(world.isPlayerAllowSuffocation()).toString(), "Can players suffocate on this world?");
        createConfigNode(eventWriter, "playerAllowVoidDamage", "boolean", Boolean.valueOf(world.isPlayerAllowVoidDamage()).toString(), "Can players take void damage on this world?");
        createConfigNode(eventWriter, "worldAllowPvP", "boolean", Boolean.valueOf(world.isWorldAllowPvP()).toString(), "Does this world allow PvP?");
        createConfigNode(eventWriter, "worldTimeLockType", "TimeType", world.getWorldTimeLockType().toString(), "What time lock type this world has enabled. DAY, NIGHT, NONE. Anything else becomes NONE.");
        createConfigNode(eventWriter, "worldWeatherLockType", "WeatherType", world.getWorldWeatherLockType().toString(), "What weather lock type this world has enabled. CLEAR, RAIN, STORM, NONE. Anything else becomes NONE.");
        createConfigNode(eventWriter, "worldCustomSpawn", "int[]", world.getWorldCustomSpawn()[0] + "|" + world.getWorldCustomSpawn()[1] + "|" + world.getWorldCustomSpawn()[2], "World custom spawn location in X|Y|Z ints.");
        createConfigNode(eventWriter, "worldSeed", "long", Long.valueOf(world.getWorldSeed()).toString(), "The seed used when this world was generated. Can be used to generate a new world with the exact same terrain.");
        createHeaderFooter(eventWriter, false);
        eventWriter.close();
    }

    /**
     * Save xml config.
     * 
     * @param desc
     *            the {@link PluginDescriptionFile}
     */
    public static void saveXmlConfig(final PluginDescriptionFile desc) {
        try {
            writeXmlConfig(desc);
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the config directory.
     * 
     * @param configDirectory
     *            the new config directory
     */
    private static void setConfigDirectory(final File configDirectory) {
        XMLConfig.configDirectory = configDirectory;
    }

    /**
     * Sets the config file.
     * 
     * @param configFile
     *            the new config file
     */
    private static void setConfigFile(final File configFile) {
        XMLConfig.configFile = configFile;
    }

    /**
     * Sets the world config directory.
     * 
     * @param worldConfigDirectory
     *            the worldConfigDirectory to set
     */
    private static void setWorldConfigDirectory(final File worldConfigDirectory) {
        XMLConfig.worldConfigDirectory = worldConfigDirectory;
    }

    /**
     * Verify option value.
     * 
     * @param optionType
     *            the option type
     * @param optionValue
     *            the option value
     * @return true, if successful
     */
    private static boolean verifyOptionValue(final String optionType, final Object optionValue) {
        if (optionType.trim().equals("boolean")) {
            final String oV = (String) optionValue;
            if (oV.contains("true") || oV.contains("false")) {
                return true;
            }
        } else if (optionType.trim().equals("double")) {
            try {
                final double test = Double.valueOf(((String) optionValue).trim()).doubleValue();
                if (test >= 0.0) {
                    return true;
                }
            } catch (final NumberFormatException nfe) {
                return false;
            }
        }
        return false;
    }

    /**
     * Write xml config.
     * 
     * @param desc
     *            the {@link PluginDescriptionFile}
     * @throws FileNotFoundException
     *             the file not found exception
     * @throws XMLStreamException
     *             the xML stream exception
     */
    private static void writeXmlConfig(final PluginDescriptionFile desc) throws FileNotFoundException, XMLStreamException {
        if (!prepareConfig(desc)) {
            return;
        }

        if (getConfigDirectory().exists()) {
            final Set<ServerOptionKeys> keys = ConfigManager.serverOptions.keySet();
            final ArrayList<ServerOptionKeys> list = new ArrayList<ServerOptionKeys>(keys);
            Collections.sort(list);
            final ServerOption[] optionArray = new ServerOption[list.size()];
            int i = 0;
            for (final ServerOptionKeys key : list) {
                final ServerOption o = ConfigManager.serverOptions.get(key);
                if ((o != null) && (o.getOptionKey() != null) && (o.getOptionDescription() != null) && (o.getOptionType() != null) && (o.getOptionValue() != null)) {
                    optionArray[i] = new ServerOption(o.getOptionKey(), o.getOptionDescription(), o.getOptionType(), o.getOptionValue());
                    i++;
                }
            }

            FileOutputStream fileOutputStream = null;
            try {
                fileOutputStream = new FileOutputStream(getConfigFile());
                saveServerConfigFile(fileOutputStream, optionArray);
                WXLogger.prettyLog(Level.INFO, false, "Configuration saved.");

            } finally {
                try {
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                    }
                } catch (final IOException e) {
                }
            }
        }
        if (getWorldConfigDirectory().exists()) {
            final WormholeWorld[] wormholeWorlds = WorldManager.getAllWorlds();
            for (final WormholeWorld wormholeWorld : wormholeWorlds) {
                if (wormholeWorld != null) {
                    final String worldName = wormholeWorld.getWorldName();
                    FileOutputStream fileOutputStream = null;
                    try {
                        // skip empty worldnames and throw WorldsConfigurationException
                        if ((worldName == null) || ("".equals(worldName))) {
                            throw new WorldsConfigurationException("Empty worldname found!. Please check your worlds configuration files.");
                        }
                        
                        fileOutputStream = new FileOutputStream(getWorldConfigDirectory() + File.separator + worldName + ".xml");
                        saveWorldConfigFile(fileOutputStream, wormholeWorld);
                        
                        WXLogger.prettyLog(Level.INFO, false, "World Configuration saved: " + worldName);
                    } catch (WorldsConfigurationException e) {
                        WXLogger.prettyLog(Level.SEVERE, true, e.getMessage());
                    } finally {
                        try {
                            if (fileOutputStream != null) {
                                fileOutputStream.close();
                            }
                        } catch (final IOException e) {
                            
                        }
                    }
                }
            }
        }
    }
}
