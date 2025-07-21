package com.gj.miclrn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.gj.miclrn")
public class MicroLearnApplication implements CommandLineRunner {
    Logger l = LoggerFactory.getLogger(MicroLearnApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(MicroLearnApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
    }

}
