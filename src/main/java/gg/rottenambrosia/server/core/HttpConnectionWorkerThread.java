package gg.rottenambrosia.server.core;


import gg.rottenambrosia.server.Main;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

public class HttpConnectionWorkerThread extends Thread {

    private final Logger LOGGER = Logger.getLogger(HttpConnectionWorkerThread.class.getName());

    Socket socket;
    HttpConnectionWorkerThread(Socket socket) {
        this.socket = socket;
    }


    @Override
    public void run() {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();

            int _byte;
            while ((_byte = inputStream.read()) != -1) {
                // Process the byte read from the input stream
                System.out.print((char) _byte);
            }


            // Read request from the input stream
//			String html = "<html><body><h1>Hello, World!</h1></body></html>";

            String html;
            try (InputStream is = Main.class.getResourceAsStream("/static/index.html")) {
                if (is == null) {
                    throw new IOException("index.html not found in resources");
                }
                html = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


            final String CRLF = "\r\n";

            String response = "HTTP/1.1 200 OK" + CRLF +
                    "Content-Type: text/html" + CRLF +
                    "Content-Length: " + html.length() + CRLF +
                    CRLF +
                    html;
            // Write response to the output stream
            outputStream.write(response.getBytes(StandardCharsets.UTF_8));

//            try {
//                sleep(5000);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }

            LOGGER.info("Connection Processing Done.");
        } catch (Exception e) {
            LOGGER.severe("Error occurred while processing connection: " + e.getMessage());
        } finally {
            try {
                if (inputStream != null) inputStream.close();
                if (outputStream != null) outputStream.close();
                if (socket != null && !socket.isClosed()) socket.close();
            } catch (IOException e) {
                LOGGER.severe("Error occurred while closing resources: " + e.getMessage());
            }
        }
    }
}
