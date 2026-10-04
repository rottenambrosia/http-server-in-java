package gg.rottenambrosia.server;

import gg.rottenambrosia.server.config.Configuration;
import gg.rottenambrosia.server.config.ConfigurationManager;
import gg.rottenambrosia.server.core.ServerListenerThread;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.logging.Logger;

public class Main {
	private final static Logger LOGGER = Logger.getLogger(Main.class.getName());

	public static void main (String[] args) throws IOException {
		LOGGER.info("HTTP Server Starting Up like the Rolling Stones ... ");

		// System.out.println("HTTP Server Starting Up like the Rolling Stones ... ");
		ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");
		Configuration configuration = ConfigurationManager.getInstance().getCurrentConfiguration();
//		System.out.println("HTTP Server Started on port: "
//				+ configuration.getPort() + " with webroot: "
//				+ configuration.getWebroot());
		LOGGER.info("HTTP Server Started on port: "
				+ configuration.getPort() + " with webroot: "
				+ configuration.getWebroot());

		ServerListenerThread serverListenerThread = new ServerListenerThread(configuration.getPort(), configuration.getWebroot());
		serverListenerThread.start();

	}
}

