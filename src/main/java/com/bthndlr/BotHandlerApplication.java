package com.bthndlr;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BotHandlerApplication {
    Logger l= LoggerFactory.getLogger(BotHandlerApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(BotHandlerApplication.class, args);
	}

}
