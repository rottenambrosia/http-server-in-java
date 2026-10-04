package gg.rottenambrosia.server.core;

import gg.rottenambrosia.server.Main;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

public class ServerListenerThread extends Thread {
    private int port;
    private String webroot;
    private ServerSocket serverSocket;

    private final static Logger LOGGER = Logger.getLogger(ServerListenerThread.class.getName());

    public ServerListenerThread(int port, String webroot) throws IOException {
        this.port = port;
        this.webroot = webroot;
        this.serverSocket = new ServerSocket(port);
    }

    @Override
    public void run() {
        try {

            while (serverSocket.isBound() && !serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();

                LOGGER.info("Accepted connection from " + socket.getInetAddress().getHostName());

                HttpConnectionWorkerThread workerThread = new HttpConnectionWorkerThread(socket);
                workerThread.start();

            }
//            serverSocket.close();
        } catch (IOException e) {
            LOGGER.severe("Error occurred while handling client request: " + e.getMessage());
//            e.printStackTrace();
        } finally {
            if (serverSocket != null && !serverSocket.isClosed()) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    LOGGER.info("Error occurred while closing server socket: " + e.getMessage());
                }
            }
        }
    }
}
