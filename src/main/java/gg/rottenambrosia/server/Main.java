package gg.rottenambrosia.server;

import gg.rottenambrosia.server.config.Configuration;
import gg.rottenambrosia.server.config.ConfigurationManager;

import java.io.IOException;

public class Main {
	public static void main (String[] args) throws IOException {
		System.out.println("HTTP Server Starting Up like the Rolling Stones ... ");
		ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");
		Configuration configuration = ConfigurationManager.getInstance().getCurrentConfiguration();
		System.out.println("HTTP Server Started on port: " + configuration.getPort() + " with webroot: " + configuration.getWebroot());
		// Here you would start your HTTP server using the loaded configuration, but for now, we just print the configuration.
	}
}

