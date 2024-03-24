package org.purpurmc.purformance;

import net.minestom.server.MinecraftServer;
import net.minestom.server.command.CommandManager;
import net.minestom.server.command.builder.CommandData;
import net.minestom.server.command.builder.CommandResult;
import org.purpurmc.purformance.commands.CommandResponse;
import org.purpurmc.purformance.config.Eula;

import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

public class Main {

    public static final Logger logger = Logger.getLogger("Purformance");
    private static final Server server = new Server();

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        long serverStartTime = System.currentTimeMillis();

        if (!Eula.checkEula()) {
            logger.severe("You need to accept the eula!");
            System.exit(1);
        }

        server.start();
        server.initialized.get();

        long currentTime = System.currentTimeMillis();
        double elapsedSeconds = (currentTime - serverStartTime) / 1000.0;
        String formattedTime = String.format("%.3fs", elapsedSeconds);
        logger.info("Done (%s)! To get help, just try harder.".formatted(formattedTime));

        CommandManager commandManager = MinecraftServer.getCommandManager();
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()) {
            String command = scanner.nextLine();
            CommandResult result = commandManager.executeServerCommand(command);

            switch (result.getType()) {
                case UNKNOWN -> logger.info("%s has been disabled to increase performance".formatted(command));
                case SUCCESS -> {
                    CommandData data = result.getCommandData();

                    if (data != null && data.has("message")) {
                        logger.info((String) data.get("message"));
                    }
                }
            }
        }

    }
}
