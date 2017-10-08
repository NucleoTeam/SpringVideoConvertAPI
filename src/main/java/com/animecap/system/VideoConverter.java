package com.animecap.system;
import com.animecap.system.neo4j.PersistenceContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;
import javax.servlet.MultipartConfigElement;
import java.io.File;
import java.util.HashMap;

/**
 * Created by Nathaniel on 11/26/2016.
 */

@SpringBootApplication
@EnableScheduling
@EnableAutoConfiguration
@Import(PersistenceContext.class)
public class VideoConverter {
    public static String sourceDirectory = "/sources/";
    public static String uploadDirectory = "/uploads/";
    public static void main(String[] args) {
        System.setProperty("spring.config.name", "www-server");

        if(!new File("/uploads/").exists()){
            new File("/uploads/").mkdir();
        }

        int port=2115;

        System.setProperty("server.port", String.valueOf(port));

        HashMap<String, Object> props = new HashMap<>();
        props.put("server.port", port);

        new SpringApplicationBuilder().sources(VideoConverter.class).properties(props).run(args);
    }
    @Bean
    MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize("5120MB");
        factory.setMaxRequestSize("5120MB");
        return factory.createMultipartConfig();
    }
}