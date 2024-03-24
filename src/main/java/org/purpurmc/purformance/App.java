package org.purpurmc.purformance;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws IOException {
        App app = new App();
        app.start();
        app.shutdown();
    }

    private final int port;
    private ServerSocket server;

    public App() {
        // todo: figure out init fake eula & server.properties
        // todo: figure out logging to make it look like minecraft

        this.port = 25565;
    }

    public void start() throws IOException {
        try {
            this.server = new ServerSocket(this.port);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to bind to port " + this.port);
            return;
        }

        while (true) {
            new Connection(this.server.accept()).start();
        }
    }

    public void shutdown() throws IOException {
        this.server.close();
    }
}
