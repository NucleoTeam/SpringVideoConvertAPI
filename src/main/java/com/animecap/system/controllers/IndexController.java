package com.animecap.system.controllers;

import com.animecap.system.models.VideoModel;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Created by Nathaniel on 11/26/2016.
 */
@RestController
@EnableAutoConfiguration
public class IndexController {
    @PostMapping("/video/upload")
    public String videoNotFound(@RequestParam("file") MultipartFile file,
                                RedirectAttributes redirectAttributes) {

        return file.getName();
    }
    @RequestMapping("/")
    public VideoModel index(@RequestHeader("auth") String authed) {
        return new VideoModel("woop"+authed);
    }
}
