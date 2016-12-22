package com.animecap.system.models;

import net.bramp.ffmpeg.probe.FFmpegStream;
import org.apache.commons.lang3.math.Fraction;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

/**
 * Created by Nathaniel on 12/18/2016.
 */
@NodeEntity
public class Stream {
    public Stream(){}
    public Stream(FFmpegStream fs) throws Exception{
        Field[] fields = fs.getClass().getDeclaredFields();
        for(Field f: fields){
            Object obj = f.get(fs);
            try {
                Field localField = this.getClass().getField(f.getName());
                if (localField.getType() != f.getType()) {
                    if (localField.getType() == String.class) {
                        localField.set(this, obj.toString());
                    } else if (f.getType() == Fraction.class) {
                        localField.set(this, ((Fraction) obj).doubleValue());
                    }
                } else {
                    localField.set(this, obj);
                }
            }catch (Exception e){
            }
        }
    }
    @GraphId public Long id;
    public int index;
    public String codec_name;
    public String codec_long_name;
    public String profile;
    public String codec_type;
    public double codec_time_base;

    public String codec_tag_string;
    public String codec_tag;

    public int width, height;

    public int has_b_frames;

    public String sample_aspect_ratio;
    public String display_aspect_ratio;

    public String pix_fmt;
    public int level;
    public String chroma_location;
    public int refs;
    public String is_avc;
    public String nal_length_size;
    public double r_frame_rate;
    public double avg_frame_rate;
    public double time_base;

    public int start_pts;
    public double start_time;

    public long duration_ts;
    public double duration;

    public long bit_rate;
    public long max_bit_rate;
    public int bits_per_raw_sample;
    public int bits_per_sample;

    public long nb_frames;

    public String sample_fmt;
    public int sample_rate;
    public int channels;
    public String channel_layout;

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
