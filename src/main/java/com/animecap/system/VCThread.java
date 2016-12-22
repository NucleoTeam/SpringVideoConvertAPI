package com.animecap.system;

import com.animecap.system.models.Stream;
import com.animecap.system.models.Task;
import com.animecap.system.models.repos.StreamRepository;
import com.animecap.system.models.repos.TaskRepository;
import com.animecap.system.models.repos.VideoPresetRepository;
import com.animecap.system.models.repos.VideoRepository;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import net.bramp.ffmpeg.probe.FFmpegStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Nathaniel on 12/2/2016.
 */
@Component
@EnableAutoConfiguration
public class VCThread{
    @Autowired public VideoRepository videoRepo;
    @Autowired public StreamRepository streamRepo;
    @Autowired public TaskRepository taskRepo;
    @Autowired public VideoPresetRepository videoPresetRepo;

    public static boolean running = false;

    @Scheduled(fixedRate = 15000)
    public void convertVideo() {
        System.out.println("Starting Video Converting");
        if(!running) {
            running=true;
            Task task = taskRepo.findByCompleted(0);
            System.out.print(task);
            if (task != null && task.getVideo().size() > 0 && task.getNewVideo().size() > 0 && task.getPreset().size() > 0) {
                System.out.println("in loop");
                boolean executed = true;
                try {

                    System.out.println("Task detected");
                    String originalUUID = task.getVideo().get(0).getOriginal();
                    List<Stream> vStreams = streamRepo.findByUUIDAndType(originalUUID, "VIDEO");
                    if (vStreams.size() > 0) {
                        System.out.println("conversion started, found video stream");
                        Stream videoStream = vStreams.get(task.videoStreamIndex);
                        FFmpeg ffmpeg = new FFmpeg("ffmpeg");
                        FFprobe ffprobe = new FFprobe("ffprobe");
                        FFmpegBuilder builder = new FFmpegBuilder()
                            .setInput(VideoConverter.sourceDirectory + task.getVideo().get(0).getOriginal())
                            .overrideOutputFiles(true)
                            .addOutput(VideoConverter.sourceDirectory + task.getNewVideo().get(0).getOriginal())
                                .setFormat(task.getPreset().get(0).getFormat())
                                .setVideoBitRate(task.getPreset().get(0).getVideoBitRate())
                                .setAudioChannels(task.getPreset().get(0).getAudioChannels())
                                .setAudioCodec(task.getPreset().get(0).getAudioCodec())
                                .setAudioSampleRate(task.getPreset().get(0).getAudioSampleRate())
                                .setAudioBitRate(task.getPreset().get(0).getAudioBitrate())
                                .setVideoCodec(task.getPreset().get(0).getVideoCodec())
                                .setVideoFrameRate(videoStream.avg_frame_rate)
                                .setVideoResolution(task.getPreset().get(0).getVideoWidth(), task.getPreset().get(0).getVideoHeight())
                                .setStrict(FFmpegBuilder.Strict.EXPERIMENTAL)
                                .done();
                        System.out.println("executing");
                        FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
                        executor.createJob(builder).run();
                        executor.createTwoPassJob(builder).run();
                        try {
                            FFmpegProbeResult probeResult = ffprobe.probe(VideoConverter.sourceDirectory + task.getNewVideo().get(0).getOriginal());
                            for(FFmpegStream fs :probeResult.getStreams()){
                                Stream stream = new Stream(fs);
                                task.getNewVideo().get(0).streams.add(stream);
                            }
                            videoRepo.save(task.getNewVideo().get(0));
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    } else {
                        executed = true;
                    }
                } catch (Exception e) {
                    executed = false;
                    e.printStackTrace();
                }
                if (executed) {
                    task.completed = 1;
                    taskRepo.save(task);
                    System.out.println("Task done");
                }
            }
            running=false;
        }
    }
}
