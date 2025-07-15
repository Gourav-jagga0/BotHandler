package com.miclrn.utility;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Utility {
	private static final Logger logger = LoggerFactory.getLogger(Utility.class);

	public static void createFolder(String folder) {
		try {
			Path path = Paths.get(folder);
			if (!Files.exists(path)) {
				Files.createDirectories(path);
				logger.info("Created session directory: {}", folder);
			}
		} catch (IOException e) {
			logger.error("Failed to create session directory", e);
			throw new RuntimeException("Could not initialize session storage", e);
		}
	}

	public static void sleep() {
		sleep(null);
	}

	public static void sleep(Long ms) {
		try {
			if (ms == null)
				ms = ThreadLocalRandom.current().nextLong(2000, 4000);
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			logger.error("InterruptedException occured {}", e);
		}
	}

}
