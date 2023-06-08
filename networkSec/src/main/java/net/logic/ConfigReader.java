package net.logic;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import com.fasterxml.jackson.databind.ObjectMapper;


@Configuration
public class ConfigReader {

	private Config config;
	private final String CONFIG_JSON = "src/main/resources/config.json";

	public ConfigReader(ResourceLoader resourceLoader) {
		
		ObjectMapper mapper = new ObjectMapper();
		try {	
			InputStream inputStream = new FileInputStream(CONFIG_JSON);
			config = mapper.readValue(inputStream, Config.class);
		} catch (IOException e) {
			throw new RuntimeException("Failed to read config file", e);
		}
	}

	public Config getConfig() {
		return config;
	}

}
