package org.purpurmc.purformance;

import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

public class Main {

    public static final Logger logger = Logger.getLogger("Purformance");
    private static final Server server = new Server();

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        long serverStartTime = System.currentTimeMillis();

        if (!Eula.checkEula()) {
            logger.severe("You need to accept the eula!");
            System.exit(1);
        }

        server.start();
        server.initialized.get();

        long currentTime = System.currentTimeMillis();
        double elapsedSeconds = (currentTime - serverStartTime) / 1000.0;
        String formattedTime = String.format("%.3fs", elapsedSeconds);
        logger.info("Done (%s)! To get help, just try harder.".formatted(formattedTime));

        while (true) {
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            logger.info("Console executed command \"%s\"".formatted(input));

            if (input.equalsIgnoreCase("stop")) System.exit(0);
            // TODO command handling lol
        }

    }
}
