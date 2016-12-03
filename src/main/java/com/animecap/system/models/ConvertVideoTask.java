package com.animecap.system.models;

import java.util.UUID;

/**
 * Created by Nathaniel on 12/3/2016.
 */
public class ConvertVideoTask implements Task {
    private VideoModel media = null;
    private String extension = "mp4";
    private String unique;
    private String format = "mp4";
    private int targetSize = 250_000;
    private int quality = 5;

    private String videoCodec = "libx264";
    private int[] videoResolution = {640, 480};
    private int videoBitRate = 2000;


    private int audioBitrate = 32768;
    private int audioSampleRate = 48_000;
    private String audioCodec = "aac";
    private int audioChannels = 2;
    public ConvertVideoTask(Media m){
        if(VideoModel.class.isInstance(m)){
            VideoModel media = (VideoModel) m;
            unique = UUID.randomUUID().toString();
        }else{
            media=null;
        }
    }

    public VideoModel getMedia() {
        return media;
    }
    public String getVideoFile(){
        return unique+"."+extension;
    }

    public String getExtension() {
        return extension;
    }

    public String getUnique() {
        return unique;
    }

    public String getFormat() {
        return format;
    }

    public String getVideoCodec() {
        return videoCodec;
    }

    public int getAudioBitrate() {
        return audioBitrate;
    }

    public int getAudioSampleRate() {
        return audioSampleRate;
    }

    public String getAudioCodec() {
        return audioCodec;
    }

    public int getAudioChannels() {
        return audioChannels;
    }

    public int[] getVideoResolution() {
        return videoResolution;
    }

    public int getTargetSize() {
        return targetSize;
    }

    public int getVideoBitRate() {
        return videoBitRate;
    }

    public int getQuality() {
        return quality;
    }
}
