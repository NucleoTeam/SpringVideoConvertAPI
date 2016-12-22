package com.animecap.system.models;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by Nathaniel on 12/3/2016.
 */
@NodeEntity
public class Task {
    public Task(){

    }

    @GraphId public Long id;

    @Relationship(type="SOURCE_VIDEO", direction = Relationship.OUTGOING)
    public List<Video> videos = new ArrayList<>();

    @Relationship(type="NEW_VIDEO", direction = Relationship.OUTGOING)
    public List<Video> newVideos = new ArrayList<>();

    @Relationship(type="HAS_PRESET", direction = Relationship.OUTGOING)
    public List<VideoPreset> preset = new ArrayList<>();

    public int completed = 0;

    public int videoStreamIndex=0;
    public int audioStreamIndex=-1;
    public int subtitleIndex=-1;

    public List<Video> getVideo() {
        return videos;
    }

    public List<Video> getNewVideo() {
        return newVideos;
    }

    public List<VideoPreset> getPreset() {
        return preset;
    }

    public int isCompleted() {
        return completed;
    }

    public Long getId() {
        return id;
    }

    public int getVideoStreamIndex() {
        return videoStreamIndex;
    }

    public int getAudioStreamIndex() {
        return audioStreamIndex;
    }

    public int getSubtitleIndex() {
        return subtitleIndex;
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
