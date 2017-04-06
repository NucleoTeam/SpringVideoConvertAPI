package com.animecap.system.controllers;

import com.animecap.system.VideoConverter;
import com.animecap.system.models.Stream;
import com.animecap.system.models.Video;
import com.animecap.system.models.repos.StreamRepository;
import com.animecap.system.models.repos.TaskRepository;
import com.animecap.system.models.repos.VideoPresetRepository;
import com.animecap.system.models.repos.VideoRepository;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import net.bramp.ffmpeg.probe.FFmpegStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

/**
 * Created by Nathaniel on 11/26/2016.
 */
@RestController
@EnableAutoConfiguration
@RequestMapping("/video")
public class VideoController {
    @Autowired public VideoRepository videoRepo;
    @Autowired public StreamRepository streamRepo;
    @Autowired public TaskRepository taskRepo;
    @Autowired public VideoPresetRepository videoPresetRepo;
    @PostMapping("/upload")
    public Video videoUpload(@RequestParam("file") MultipartFile file,
                                @RequestParam("name") String name) throws Exception {
        String vIdentifier = UUID.randomUUID().toString();
        InputStream is = file.getInputStream();
        FileOutputStream fos = new FileOutputStream(new File(VideoConverter.uploadDirectory+vIdentifier));
        IOUtils.copy(is,fos);
        is.close();
        fos.close();
        Video v =  new Video(vIdentifier);
        FileUtils.moveFileToDirectory(new File(VideoConverter.uploadDirectory+vIdentifier),new File(VideoConverter.sourceDirectory), true);
        Runtime rt = Runtime.getRuntime();
        Process pr = rt.exec("ffmpeg -i " +VideoConverter.sourceDirectory+v.getOriginal()+ " -ss 00:03:00.435 -vframes 1 -vf scale=300:168 "+VideoConverter.sourceDirectory+v.getOriginal() + ".png");
        pr.waitFor();
        rt = Runtime.getRuntime();
        pr = rt.exec("ffmpeg -i " +VideoConverter.sourceDirectory+v.getOriginal()+" -map 0:s:0 " +VideoConverter.sourceDirectory+v.getOriginal()+ ".vtt");
        pr.waitFor();

        try {
            FFprobe ffprobe = new FFprobe("ffprobe");
            FFmpegProbeResult probeResult = ffprobe.probe(VideoConverter.sourceDirectory+v.getOriginal());
            for(FFmpegStream fs :probeResult.getStreams()){
                Stream stream = new Stream(fs);
                v.streams.add(stream);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return videoRepo.save(v);
    }
    @RequestMapping("/original")
    public Video video(@RequestParam("uuid") String vUUID) {
        return videoRepo.findOne(videoRepo.findByOriginal(vUUID).id);
    }

    @RequestMapping("/converted")
    public Object converted(@RequestParam("uuid") String vUUID, @RequestParam("preset") String preset) {
        return videoRepo.findAll(videoRepo.findConvertedByOriginalAndPreset(vUUID, preset));
    }


}
