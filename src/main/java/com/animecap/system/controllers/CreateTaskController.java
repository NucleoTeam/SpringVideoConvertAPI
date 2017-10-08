package com.animecap.system.controllers;

import com.animecap.system.components.Status;
import com.animecap.system.models.Stream;
import com.animecap.system.models.Task;
import com.animecap.system.models.Video;
import com.animecap.system.models.VideoPreset;
import com.animecap.system.models.repos.StreamRepository;
import com.animecap.system.models.repos.TaskRepository;
import com.animecap.system.models.repos.VideoPresetRepository;
import com.animecap.system.models.repos.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * Created by Nathaniel on 12/18/2016.
 */
@RestController
@EnableAutoConfiguration
@RequestMapping("/task")
public class CreateTaskController {
    @Autowired public VideoRepository videoRepo;
    @Autowired public StreamRepository streamRepo;
    @Autowired public TaskRepository taskRepo;
    @Autowired public VideoPresetRepository videoPresetRepo;

    @RequestMapping("/create")
    public Status createVideoTask(@RequestParam("uuid") String vUUID, @RequestParam("preset") String preset) {
        Video v = videoRepo.findByOriginal(vUUID);
        if(v==null){
            return new Status(0, "Could not find original video!");
        }
        Video newVideo = new Video(UUID.randomUUID().toString());
        VideoPreset videoPreset = videoPresetRepo.findByName(preset);
        if(videoPreset==null) {
            return new Status(0, "Could not find video preset!");
        }
        Task task = new Task();
        task.newVideos.add(newVideo);
        task.videos.add(v);
        task.preset.add(videoPreset);
        return new Status(1, taskRepo.save(task));
    }
}