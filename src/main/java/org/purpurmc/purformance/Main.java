package org.purpurmc.purformance;

import net.minestom.server.MinecraftServer;
import net.minestom.server.command.CommandManager;
import net.minestom.server.command.builder.CommandData;
import net.minestom.server.command.builder.CommandResult;
import org.purpurmc.purformance.commands.CommandResponse;
import org.purpurmc.purformance.config.Eula;

import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    public static final Logger logger = LoggerFactory.getLogger(Main.class);
    private static final Server server = new Server();

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Thread.currentThread().setName("ServerMain");

        if (Eula.checkEula()) {
            logger.error("You need to accept the eula!");
            System.exit(1);
        }

        server.start();
        server.initialized.get();

        CommandManager commandManager = MinecraftServer.getCommandManager();
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()) {
            String command = scanner.nextLine();
            if (command.isBlank() || command.isEmpty()) {
                continue;
            }

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
