package com.animecap.system.models;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;

import java.lang.reflect.Field;
import java.util.Vector;

/**
 * Created by Nathaniel on 12/3/2016.
 */

@NodeEntity
public class VideoPreset {

    public VideoPreset(){}

    @GraphId public Long id;

    public String name;

    public String extension = "mp4";
    public String format = "mp4";
    public int targetSize = 250_000;
    public int quality = 5;

    public String videoCodec = "libx264";
    public int videoWidth = 640;
    public int videoHeight = 480;
    public int videoBitRate = 2000;


    public int audioBitrate = 32768;
    public int audioSampleRate = 48_000;
    public String audioCodec = "aac";
    public int audioChannels = 2;

    public String getExtension() {
        return extension;
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

    public Long getId() {
        return id;
    }

    public int getVideoWidth() {
        return videoWidth;
    }

    public int getVideoHeight() {
        return videoHeight;
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

    public String getName() {
        return name;
    }
    public String toString(){
        String output = "";
        for(Field f: this.getClass().getDeclaredFields()){
            try {
                output+=f.getName()+" = "+String.valueOf(f.get(this))+"\n";
            }catch (Exception e){

            }
        }
        return output;
    }
}
