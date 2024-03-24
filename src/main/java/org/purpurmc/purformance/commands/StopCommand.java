package org.purpurmc.purformance.commands;

import net.minestom.server.MinecraftServer;
import net.minestom.server.command.builder.Command;
import org.purpurmc.purformance.Main;

public class StopCommand extends Command {

    public StopCommand() {
        super("stop", "end");

        setDefaultExecutor((sender, context) -> {
            Main.logger.info("Stopping server.");
            MinecraftServer.stopCleanly();
            System.exit(0);
        });
    }
}
