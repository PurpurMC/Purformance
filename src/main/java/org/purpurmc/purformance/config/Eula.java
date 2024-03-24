package org.purpurmc.purformance.config;

import org.purpurmc.purformance.Main;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

public class Eula {
    private static final String EULA_FILE_NAME = "eula.txt";

    /**
     * Creates eula.txt if it doesn't exist and checksif it contains eula=true
     * @return true if eula is accepted
     */
    public static boolean checkEula() {
        initEula();
        return eulaAccepted();
    }

    private static void initEula() {
        String jarPath = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getParent();
        Path eulaFilePath = Paths.get(jarPath, EULA_FILE_NAME);
        if (!Files.exists(eulaFilePath)) {
            try (InputStream eulaStream = Main.class.getResourceAsStream("/"+EULA_FILE_NAME)) {
                if (eulaStream != null) {
                    Files.copy(eulaStream, eulaFilePath, StandardCopyOption.REPLACE_EXISTING);
                }
            } catch (IOException e) {
                Main.logger.severe("Could not create eula file. Exiting.");
                e.printStackTrace();
                System.exit(1);
            }
        }
    }

    private static boolean eulaAccepted() {
        String jarPath = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getParent();
        Path eulaFilePath = Paths.get(jarPath, EULA_FILE_NAME);
        try (Stream<String> lines = Files.lines(eulaFilePath)) {
            return lines.anyMatch(line -> line.trim().equalsIgnoreCase("eula=true"));
        } catch (IOException e) {
            Main.logger.severe("Could not read eula file. Exiting.");
            e.printStackTrace();
            System.exit(1);
            return false;
        }
    }

}