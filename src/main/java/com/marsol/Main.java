package com.marsol;

import com.marsol.application.LabelController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:application.properties")
public class Main implements CommandLineRunner {

    Logger logger = LoggerFactory.getLogger(Main.class);
    @Autowired
    private LabelController labelController;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        labelController.loadLabels();
        logger.info("Etiquetas cargadas.");
    }
}