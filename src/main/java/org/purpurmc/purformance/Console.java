package org.purpurmc.purformance;

import net.minecrell.terminalconsole.SimpleTerminalConsole;
import net.minecrell.terminalconsole.TerminalConsoleAppender;
import net.minestom.server.MinecraftServer;

public class Console extends SimpleTerminalConsole {

    public Console() {
        TerminalConsoleAppender.isAnsiSupported();
    }

    @Override
    protected boolean isRunning() {
        return MinecraftServer.isStarted() && !MinecraftServer.isStopping();
    }

    @Override
    protected void runCommand(String command) {
        MinecraftServer.getCommandManager().execute(MinecraftServer.getCommandManager().getConsoleSender(), command);
    }

    @Override
    protected void shutdown() {
        Main.server.shutdown();
    }
}
