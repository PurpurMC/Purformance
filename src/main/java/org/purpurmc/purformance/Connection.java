package org.purpurmc.purformance;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Connection extends Thread {
    private final Socket socket;

    public Connection(Socket socket) {
        this.socket = socket;
    }

    public void run() {

    }
}
