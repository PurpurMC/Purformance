package org.purpurmc.purformance;

public class Eula {
    private static boolean initialized = false;

    public static boolean checkEula() {
        if (!initialized) {
            initEula();
        }

        return true; // todo
    }

    private static void initEula() {
        initialized = true;
    }
}
