package org.purpurmc.purformance.config;

import net.kyori.adventure.text.Component;
import org.purpurmc.purformance.Main;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Properties;

public class ServerProperties {

    private static final String PROPERTIES_FILE_NAME = "server.properties";

    public final String ip;
    public final Component motd;
    public final int port;
    public final boolean fastMode;

    public ServerProperties() throws RuntimeException {
        initProperties();
        String jarPath = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getParent();
        Path propertiesFilePath = Paths.get(jarPath, PROPERTIES_FILE_NAME);
        Properties serverProperties = new Properties();
        try {
            serverProperties.load(new FileInputStream(String.valueOf(propertiesFilePath)));
            this.ip = serverProperties.getProperty("ip", "0.0.0.0");
            this.port = Integer.parseInt(serverProperties.getProperty("port", String.valueOf(25565)));
            this.motd = Component.text(serverProperties.getProperty("motd", "Purformance server"));
            this.fastMode = Boolean.parseBoolean(serverProperties.getProperty("fast-mode", "false"));
        } catch (IOException | NumberFormatException e) {
            throw new RuntimeException("Failed to read server.properties", e);
        }
    }

    private void initProperties() {
        String jarPath = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getParent();
        Path propertiesFilePath = Paths.get(jarPath, PROPERTIES_FILE_NAME);
        if (!Files.exists(propertiesFilePath)) {
            try (InputStream propertiesStream = Main.class.getResourceAsStream("/"+PROPERTIES_FILE_NAME)) {
                if (propertiesStream != null) {
                    Files.copy(propertiesStream, propertiesFilePath, StandardCopyOption.REPLACE_EXISTING);
                }
            } catch (IOException e) {
                Main.logger.error("Could not create server.properties file. Exiting.", e);
                System.exit(1);
            }
        }
    }

}
