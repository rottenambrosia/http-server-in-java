package gg.rottenambrosia.server;

import gg.rottenambrosia.server.config.Configuration;
import gg.rottenambrosia.server.config.ConfigurationManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Main {
	public static void main (String[] args) throws IOException {
		System.out.println("HTTP Server Starting Up like the Rolling Stones ... ");
		ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");
		Configuration configuration = ConfigurationManager.getInstance().getCurrentConfiguration();
		System.out.println("HTTP Server Started on port: "
				+ configuration.getPort() + " with webroot: "
				+ configuration.getWebroot());

		try {
            ServerSocket serverSocket = new ServerSocket(configuration.getPort());
            Socket socket = serverSocket.accept();

            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            // Read request from the input stream
//			String html = "<html><body><h1>Hello, World!</h1></body></html>";

			String html;
			try (InputStream is = Main.class.getResourceAsStream("/static/index.html")) {
				if (is == null) {
					throw new IOException("index.html not found in resources");
				}
				html = new String(is.readAllBytes(), StandardCharsets.UTF_8);
			}


			final String CRLF = "\r\n";

            String response = "HTTP/1.1 200 OK" + CRLF +
                    "Content-Type: text/html" + CRLF +
                    "Content-Length: " + html.length() + CRLF +
                    CRLF +
                    html;
            // Write response to the output stream
			outputStream.write(response.getBytes(StandardCharsets.UTF_8));
            inputStream.close();
            outputStream.close();
            socket.close();
            serverSocket.close();
        } catch (IOException e) {
			e.printStackTrace();
		}

	}
}

