package ua.com.vit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        final Logger logger = LoggerFactory.getLogger(Application.class);
        try {
            SpringApplication.run(Application.class, args);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        logger.info("The application started");
    }
}





















