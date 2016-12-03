package com.animecap.system;

import com.animecap.system.models.ConvertVideoTask;
import com.animecap.system.models.Task;
import com.animecap.system.models.VideoModel;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.probe.FFmpegFormat;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;

import java.util.Stack;

/**
 * Created by Nathaniel on 12/2/2016.
 */
public class VCThread implements Runnable{
    Stack<Task> queue = new Stack<Task>();
    @Override
    public void run() {
        while(true){
            if(!queue.empty()){
                Task t = queue.peek();
                boolean executed = true;
                if(ConvertVideoTask.class.isInstance(t)){
                    ConvertVideoTask cvt = (ConvertVideoTask) t;
                    try {
                        FFmpeg ffmpeg = new FFmpeg("ffmpeg");
                        FFprobe ffprobe = new FFprobe("ffprobe");
                        FFmpegBuilder builder = new FFmpegBuilder()
                            .setInput(cvt.getMedia().getOriginal())
                            .overrideOutputFiles(true)
                            .addOutput(cvt.getVideoFile())
                            .setFormat(cvt.getFormat())
                            .setTargetSize(cvt.getTargetSize())
                            .setVideoBitRate(cvt.getVideoBitRate())
                            .setVideoQuality(cvt.getQuality())
                            .disableSubtitle()
                            .setAudioChannels(cvt.getAudioChannels())
                            .setAudioCodec(cvt.getAudioCodec())
                            .setAudioSampleRate(cvt.getAudioSampleRate())
                            .setAudioBitRate(cvt.getAudioBitrate())
                            .setVideoCodec(cvt.getVideoCodec())
                            .setVideoFrameRate(cvt.getMedia().getFramerate())
                            .setVideoResolution(cvt.getVideoResolution()[0],cvt.getVideoResolution()[1])
                            .setStrict(FFmpegBuilder.Strict.EXPERIMENTAL)
                            .done();
                        FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
                        executor.createJob(builder).run();
                        executor.createTwoPassJob(builder).run();
                    }catch (Exception e){
                        executed=false;
                    }
                }
                if(executed){
                    queue.pop();
                }
            }
            try{
                Thread.sleep(1L);
            }catch (Exception e){
            }
        }
    }
}
