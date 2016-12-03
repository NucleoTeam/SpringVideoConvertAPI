package com.animecap.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.util.Stack;

/**
 * Created by Nathaniel on 11/26/2016.
 */

@SpringBootApplication
public class AnimecapConnection {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(AnimecapConnection.class);
        app.run(args);
    }
}