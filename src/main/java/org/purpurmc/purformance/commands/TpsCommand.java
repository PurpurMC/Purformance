package org.purpurmc.purformance.commands;

import net.minestom.server.MinecraftServer;
import net.minestom.server.command.builder.Command;

public class TpsCommand extends Command {
    private final String tpsResponse = """
            Ticks-per-second (5ns, 5µs, 5ms, 5s):
            ∞, ∞, ∞, ∞
            
            Milliseconds-per-tick (5ns, 5µs, 5ms, 5s):
            0, 0, 0, 0"""; // todo: figure out color

    public TpsCommand() {
        super("tps", "mspt");
        setDefaultExecutor((sender, context) -> MinecraftServer.LOGGER.info(tpsResponse));
    }
}
