package org.purpurmc.purformance;

import net.minecrell.terminalconsole.SimpleTerminalConsole;
import net.minestom.server.MinecraftServer;
import org.slf4j.Logger;

public class Console extends SimpleTerminalConsole {

    @Override
    protected boolean isRunning() {
        return MinecraftServer.isStarted() && !MinecraftServer.isStopping();
    }

    @Override
    protected void runCommand(String command) {
        MinecraftServer.getCommandManager().executeServerCommand(command);
    }

    @Override
    protected void shutdown() {
        Main.server.shutdown();
    }
}
