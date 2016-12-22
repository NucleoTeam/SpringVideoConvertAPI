package com.animecap.system.controllers;

import com.animecap.system.VideoConverter;
import com.animecap.system.components.Status;
import com.animecap.system.models.Stream;
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

/**
 * Created by Nathaniel on 12/18/2016.
 */
@RestController
@EnableAutoConfiguration
@RequestMapping("/stream")
public class StreamInfoController {
    @Autowired public VideoRepository videoRepo;
    @Autowired public StreamRepository streamRepo;
    @Autowired public TaskRepository taskRepo;
    @Autowired public VideoPresetRepository videoPresetRepo;

    @RequestMapping("/video")
    public Status videoStream(@RequestParam("uuid") String vUUID) {
        List<Stream> streams = streamRepo.findByUUIDAndType(vUUID, "VIDEO");
        return new Status((streams.size()>0)?1:0, streams);
    }
    @RequestMapping("/audio")
    public Status audioStream(@RequestParam("uuid") String vUUID) {
        List<Stream> streams = streamRepo.findByUUIDAndType(vUUID, "AUDIO");
        return new Status((streams.size()>0)?1:0, streams);
    }
    @RequestMapping("/subtitle")
    public Status subtitleStream(@RequestParam("uuid") String vUUID, @RequestParam("type") String type) {
        List<Stream> streams = streamRepo.findByUUIDAndSubtitleType(vUUID, type);
        return new Status((streams.size()>0)?1:0, streams);
    }
}
