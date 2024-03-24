package org.purpurmc.purformance;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.minestom.server.MinecraftServer;
import net.minestom.server.command.CommandManager;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import net.minestom.server.event.server.ServerListPingEvent;
import net.minestom.server.ping.ResponseData;
import org.purpurmc.purformance.commands.StopCommand;
import org.purpurmc.purformance.commands.TpsCommand;
import org.purpurmc.purformance.config.ServerProperties;

import java.util.concurrent.CompletableFuture;

public class Server extends Thread {

    public static ServerProperties serverProperties;

    public final CompletableFuture<Void> initialized = new CompletableFuture<>();

    private static final Component kickMessage = Component.text()
            .append(Component.text("Purformance:").color(NamedTextColor.RED).decorate(TextDecoration.BOLD))
            .append(Component.newline()).append(Component.newline())
            .append(Component.text("Player joining has been disabled to increase performance, try again later.").color(NamedTextColor.WHITE).decorate(TextDecoration.ITALIC))
            .build();

    @Override
    public void run() {
        serverProperties = new ServerProperties();

        // todo: logging like minecraft console

        MinecraftServer server = MinecraftServer.init();
        MinecraftServer.setBrandName("Purformance");

        GlobalEventHandler eventHandler = MinecraftServer.getGlobalEventHandler();
        CommandManager commandManager = MinecraftServer.getCommandManager();

        eventHandler.addListener(ServerListPingEvent.class, event -> {
            ResponseData data = new ResponseData();
            data.setDescription(serverProperties.motd);
            data.setProtocol(serverProperties.fastMode ? 47 : 765);
            data.setVersion(serverProperties.fastMode ? "1.8.8" : "1.20.4");
            data.setMaxPlayer(Integer.MAX_VALUE);
            event.setResponseData(data);
        });

        eventHandler.addListener(AsyncPlayerConfigurationEvent.class, event -> {
            event.getPlayer().kick(kickMessage);
        });

        commandManager.register(new StopCommand());
        commandManager.register(new TpsCommand());

        initialized.complete(null);
        server.start(serverProperties.ip, serverProperties.port);
    }

}
