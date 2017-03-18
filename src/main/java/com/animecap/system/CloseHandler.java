package com.animecap.system;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.logging.Logger;

/**
 * Created by Nathaniel on 3/2/2017.
 */
@Component
public class CloseHandler implements Runnable {
    @Bean
    public boolean createThread(){
        new Thread(new CloseHandler()).start();
        return true;
    }

    protected Logger logger = Logger.getLogger(CloseHandler.class.getName());

    public void run() {
        try {
            File file = new File(CloseHandler.class.getProtectionDomain().getCodeSource().getLocation().toString().split("!")[0].split("jar:file:")[1]);
            while (true) {
                if(!file.exists()){
                    logger.info("Jar was deleted, closing down server!");
                    logger.info("Closing in 30 seconds!");
                    System.exit(0);
                }
                try {
                    Thread.sleep(200);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
