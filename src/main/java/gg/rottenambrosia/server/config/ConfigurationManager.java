package gg.rottenambrosia.server.config;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import gg.rottenambrosia.server.util.Json;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
public class ConfigurationManager {
	
	private static ConfigurationManager myConfigurationManager;
	private static Configuration myCurrentConfiguration;

	private ConfigurationManager () {

	}

	public static ConfigurationManager getInstance () throws IOException {
		if (myConfigurationManager == null) {
			myConfigurationManager = new ConfigurationManager();
		}
		return myConfigurationManager;
	}

	/*
	 * loads the config file by the provided path
	 */
	public void loadConfigurationFile (String filePath) {
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(filePath);
        } catch (FileNotFoundException e) {
            throw new HttpConfigurationException("File not found: " + filePath, e);
        }
        StringBuffer stringBuffer = new StringBuffer();
		int i;
		while (true) {
            try {
                if (!((i = fileReader.read()) != -1)) break;
            } catch (IOException e) {
                throw new HttpConfigurationException("Error reading the config file: " + e.getMessage(), e);
            }
            stringBuffer.append((char) i);
		}
        JsonNode config = null;
        try {
            config = Json.parse(stringBuffer.toString());
        } catch (JsonProcessingException e) {
            throw new HttpConfigurationException("Error parsing the config file: " + e.getMessage(), e);
        }
        try {
            myCurrentConfiguration = Json.fromJson(config, Configuration.class);
        } catch (JsonProcessingException e) {
            throw new HttpConfigurationException("Error converting JSON to Configuration: " + e.getMessage(), e);
        }
    }
	
	/*
	 * returns the current loaded configuration
	 */
	public Configuration getCurrentConfiguration () {
		if  (myCurrentConfiguration == null) {
			myCurrentConfiguration = new Configuration();
		}
		return myCurrentConfiguration;
	}
}
