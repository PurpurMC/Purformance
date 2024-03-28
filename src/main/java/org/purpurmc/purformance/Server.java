package org.purpurmc.purformance;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.minestom.server.MinecraftServer;
import net.minestom.server.adventure.audience.Audiences;
import net.minestom.server.command.CommandManager;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import net.minestom.server.event.server.ServerListPingEvent;
import net.minestom.server.ping.ResponseData;
import org.purpurmc.purformance.commands.StopCommand;
import org.purpurmc.purformance.commands.TpsCommand;
import org.purpurmc.purformance.config.ServerProperties;
import org.purpurmc.purformance.tasks.FBIWatcherTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Timer;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;

public class Server extends Thread {

    public static ServerProperties serverProperties;
    public static final Logger logger = LoggerFactory.getLogger(Server.class);

    public static final Timer scheduler = new Timer("Server scheduler", true);

    public final CompletableFuture<Void> started = new CompletableFuture<>();

    private static final Component kickMessage = Component.text()
            .append(Component.text("Purformance:").color(NamedTextColor.RED).decorate(TextDecoration.BOLD))
            .append(Component.newline()).append(Component.newline())
            .append(Component.text("Player joining has been disabled to increase performance, try again later.").color(NamedTextColor.WHITE).decorate(TextDecoration.ITALIC))
            .build();

    public Server() {
        super("Server thread"); // TODO: specify through log4j.xml instead? (could probably stay :shrug:)
    }

    @Override
    public void run() {
        long serverStartTime = System.currentTimeMillis();
        logger.info("Loading properties");
        serverProperties = new ServerProperties();
        logger.info("This server is running Purformance version git-Purformance-1.0.0 (MC: 1.20.4)");
        logger.info("Using ∞ threads for Netty based IO");
        logger.info("Debug logging is enabled");
        logger.info("Default game type: SURVIVAL");
        logger.info("Generating keypair");

        MinecraftServer server = MinecraftServer.init();
        MinecraftServer.setBrandName("Purformance");

        GlobalEventHandler eventHandler = MinecraftServer.getGlobalEventHandler();
        CommandManager commandManager = MinecraftServer.getCommandManager();

        if (!serverProperties.onlineMode) {
            scheduler.schedule(new FBIWatcherTask(), ThreadLocalRandom.current().nextInt(10) * 1000, 90 * 1000);
        }

        eventHandler.addListener(ServerListPingEvent.class, event -> {
            ResponseData data = new ResponseData();
            data.setDescription(serverProperties.motd);
            data.setProtocol(serverProperties.fastMode ? 47 : 765);
            data.setVersion(serverProperties.fastMode ? "1.8.8" : "1.20.4");
            data.setMaxPlayer(Integer.MAX_VALUE);
            event.setResponseData(data);
        });

        eventHandler.addListener(AsyncPlayerConfigurationEvent.class, event -> event.getPlayer().kick(kickMessage));

        commandManager.setUnknownCommandCallback((sender, command) -> {
            sender.sendMessage("%s has been disabled to increase performance".formatted(command));
        });

        commandManager.register(new StopCommand());
        commandManager.register(new TpsCommand());

        server.start(serverProperties.onlineMode ? serverProperties.ip : "localhost", serverProperties.port);
        logger.info("Starting Minecraft server on %s:%s".formatted(serverProperties.onlineMode ? serverProperties.ip : "localhost", serverProperties.port));
        started.complete(null);

        long currentTime = System.currentTimeMillis();
        double elapsedSeconds = (currentTime - serverStartTime) / 1000.0;
        String formattedTime = String.format("%.3fs", elapsedSeconds);
        logger.info("Done (%s)! To get help, just try harder.".formatted(formattedTime));
    }

    protected void shutdown() {
        logger.info("Shutting down server...");

        try {
            MinecraftServer.stopCleanly();
            System.exit(0);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

}
