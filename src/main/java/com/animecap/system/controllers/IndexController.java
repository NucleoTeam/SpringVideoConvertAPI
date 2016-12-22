package com.animecap.system.controllers;

import com.animecap.system.models.repos.StreamRepository;
import com.animecap.system.models.repos.TaskRepository;
import com.animecap.system.models.repos.VideoPresetRepository;
import com.animecap.system.models.repos.VideoRepository;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Nathaniel on 12/18/2016.
 */
@RestController
@EnableAutoConfiguration
@RequestMapping("/")
public class IndexController {
    @RequestMapping("/")
    public byte[] index() {
        try {
            byte[] out = IOUtils.toByteArray(IndexController.class.getClassLoader().getResourceAsStream("www/index.html"));
            return out;
        }catch(Exception e){

        }
        return null;
    }
}
