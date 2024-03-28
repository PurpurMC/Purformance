package org.purpurmc.purformance;

import java.util.concurrent.ExecutionException;

import net.minestom.server.MinecraftServer;
import org.purpurmc.purformance.config.Eula;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    public static final Logger logger = LoggerFactory.getLogger(Main.class);
    protected static final Server server = new Server();

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Thread.currentThread().setName("ServerMain"); // TODO: specify through log4j.xml instead

        if (!Eula.checkEula()) {
            logger.error("You need to accept the eula!");
            System.exit(1);
        }

        server.start();
        server.started.get();

        new Console().start();
    }
}
