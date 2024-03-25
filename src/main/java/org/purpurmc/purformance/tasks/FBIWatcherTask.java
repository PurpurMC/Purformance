package org.purpurmc.purformance.tasks;

import org.purpurmc.purformance.Server;

import java.util.TimerTask;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;

public class FBIWatcherTask extends TimerTask {

    @Override
    public void run() {
        Server.logger.warn("Offline mode detected. Attempting to contact FBI to report piracy...");
        CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(Math.max(3000, ThreadLocalRandom.current().nextInt(9) * 1000));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Server.logger.warn("Could not contact FBI, no internet connection. Retrying in a few minutes.");
        });
    }
}
