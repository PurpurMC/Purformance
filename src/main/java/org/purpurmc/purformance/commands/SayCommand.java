package org.purpurmc.purformance.commands;

import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentString;
import net.minestom.server.command.builder.arguments.ArgumentType;
import org.purpurmc.purformance.Server;

public class SayCommand extends Command {

    public SayCommand() {
        super("say");
        ArgumentString messageArgument = ArgumentType.String("message");
        setDefaultExecutor(((commandSender, commandContext) -> Server.logger.info("Usage: say <message>")));
        addSyntax((sender, context) -> {
            String message = context.get(messageArgument);
            Server.logger.info("[Server] %s".formatted(message));
        }, messageArgument);
    }
}
