package com.animecap.system.models;

import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.probe.FFmpegFormat;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;

/**
 * Created by Nathaniel on 11/26/2016.
 */
public class VideoModel implements Media {
    public VideoModel(String original){
        try {
            FFprobe ffprobe = new FFprobe("ffprobe");
            FFmpegProbeResult probeResult = ffprobe.probe(original);
            FFmpegFormat format = probeResult.getFormat();
        }catch (Exception e){

        }
        this.original = original;
    }
    private String original;
    private double framerate;

    public String getOriginal() {
        return original;
    }
    public double getFramerate() {
        return framerate;
    }
}
