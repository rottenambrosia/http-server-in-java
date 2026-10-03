package gg.rottenambrosia.server.config;

public class Configuration {
	private int port;
	private String webroot;

	// getters
	public int getPort () {
		return port;
	}

	public String getWebroot () {
		return webroot;
	}

	// setters
	public void setPort (int port) {
		this.port = port;
	}

	public void setWebroot (String webroot) {
		this.webroot = webroot;
	}
}
