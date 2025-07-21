package com.gj.miclrn.utility;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Utility {

	private static final Logger logger = LoggerFactory.getLogger(Utility.class);
	private static final ObjectMapper mapper = new ObjectMapper();

	public static void createFolder(String folderPath) {
		Path path = Paths.get(folderPath);
		try {
			if (Files.notExists(path)) {
				Files.createDirectories(path);
				logger.info("Created directory: {}", folderPath);
			}
		} catch (IOException e) {
			logger.error("Failed to create directory: {}", folderPath, e);
			throw new RuntimeException("Could not initialize folder: " + folderPath, e);
		}
	}

	public static void sleep() {
		sleep(null);
	}

	public static void sleep(Long ms) {
		if (ms == null) {
			ms = ThreadLocalRandom.current().nextLong(2000, 4000);
		}
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt(); // restore interrupt status
			logger.warn("Thread was interrupted during sleep", e);
		}
	}

	public static JsonNode getJsonNodeFromString(String jsonString) {
		try {
			return mapper.readTree(jsonString);
		} catch (IOException e) {
			logger.error("Invalid JSON string provided", e);
			throw new IllegalArgumentException("Failed to parse JSON", e);
		}
	}
}
