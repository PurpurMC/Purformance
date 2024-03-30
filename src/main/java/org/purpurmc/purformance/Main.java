package org.purpurmc.purformance;

import java.awt.GraphicsEnvironment;
import java.util.concurrent.ExecutionException;
import org.purpurmc.purformance.config.Eula;
import org.purpurmc.purformance.gui.Gui;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    public static final Logger logger = LoggerFactory.getLogger(Main.class);
    public static final Server server = new Server();

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Thread.currentThread().setName("ServerMain");

        if (!Eula.checkEula()) {
            logger.error("You need to accept the eula!");
            System.exit(1);
        }

        if (!GraphicsEnvironment.isHeadless()) {
            Gui.createAndShow("Purformance");
        }

        server.start();
        server.started.get();

        new Console().start();
    }
}
