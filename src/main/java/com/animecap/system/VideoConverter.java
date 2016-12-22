package com.animecap.system;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;
import java.io.File;

/**
 * Created by Nathaniel on 11/26/2016.
 */

@SpringBootApplication
@EnableScheduling
public class VideoConverter {
    public static String sourceDirectory = "sources/";
    public static String uploadDirectory = "uploads/";
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(VideoConverter.class);
        if(!new File("uploads/").exists()){
            new File("uploads/").mkdir();
        }
        app.run(args);
    }
}