package com.animecap.system.models;

import com.animecap.system.models.interfaces.Media;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by Nathaniel on 11/26/2016.
 */
@NodeEntity
public class Video implements Media {
    public Video(){}
    public Video(String original){
        this.original = original;
    }

    @GraphId
    public Long id;

    @Relationship(type="CONVERTED_FROM", direction = Relationship.INCOMING)
    public List<Video> children = new ArrayList<>();

    @Relationship(type="HAS_STREAM", direction = Relationship.OUTGOING)
    public List<Stream> streams = new ArrayList<>();

    private String original;

    public String getOriginal() {
        return original;
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
