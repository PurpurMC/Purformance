package org.purpurmc.purformance;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.minestom.server.MinecraftServer;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import net.minestom.server.event.server.ServerListPingEvent;
import net.minestom.server.ping.ResponseData;

import java.util.logging.Logger;

public class Main {

    public static final Logger logger = Logger.getLogger("Purformance");

    private static final Component kickMessage = Component.text()
            .append(Component.text("Purformance:").color(NamedTextColor.RED).decorate(TextDecoration.BOLD))
            .append(Component.newline()).append(Component.newline())
            .append(Component.text("Player joining has been disabled to increase performance, try again later.").color(NamedTextColor.WHITE).decorate(TextDecoration.ITALIC))
            .build();

    private static Component motd;

    public static void main(String[] args) {

        long serverStartTime = System.currentTimeMillis();

        if (!Eula.checkEula()) {
            logger.severe("You need to accept the eula!");
            System.exit(1);
        }

        motd = Component.text("Purformance Server");
        // todo: eula & server.properties
        // todo: logging like minecraft console

        MinecraftServer server = MinecraftServer.init();

        GlobalEventHandler eventHandler = MinecraftServer.getGlobalEventHandler();
        eventHandler.addListener(ServerListPingEvent.class, event -> {
            ResponseData data = new ResponseData();

            data.setPlayersHidden(true);
            data.setProtocol(765); // 1.20.4 todo: do we want to do like 1.8 or something
            data.setDescription(motd); // todo

            event.setResponseData(data);
        });

        eventHandler.addListener(AsyncPlayerConfigurationEvent.class, event -> {
            event.getPlayer().kick(kickMessage);
        });

        server.start("0.0.0.0", 25565);
        long currentTime = System.currentTimeMillis();
        double elapsedSeconds = (currentTime - serverStartTime) / 1000.0;
        String formattedTime = String.format("%.3fs", elapsedSeconds);
        logger.info("Done (%s)! To get help, just try harder.".formatted(formattedTime));


    }
}
