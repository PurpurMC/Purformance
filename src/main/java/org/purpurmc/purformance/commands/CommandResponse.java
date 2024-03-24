package org.purpurmc.purformance.commands;

import net.minestom.server.command.builder.CommandData;

public class CommandResponse {

    public static CommandData message(String message) {
        return new CommandData().set("message", message);
    }
}
