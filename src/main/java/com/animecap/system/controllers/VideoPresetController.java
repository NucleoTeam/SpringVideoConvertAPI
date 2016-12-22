package com.animecap.system.controllers;

import com.animecap.system.components.Status;
import com.animecap.system.models.VideoPreset;
import com.animecap.system.models.repos.StreamRepository;
import com.animecap.system.models.repos.TaskRepository;
import com.animecap.system.models.repos.VideoPresetRepository;
import com.animecap.system.models.repos.VideoRepository;
import org.apache.commons.io.IOUtils;
import org.neo4j.ogm.annotation.GraphId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by Nathaniel on 12/18/2016.
 */
@RestController
@EnableAutoConfiguration
@RequestMapping("/video/preset")
public class VideoPresetController {
    @Autowired public VideoRepository videoRepo;
    @Autowired public StreamRepository streamRepo;
    @Autowired public TaskRepository taskRepo;
    @Autowired public VideoPresetRepository videoPresetRepo;
    public Object convert(Class type, Object in){
        if(String[].class == type){
            return String.valueOf(in).split(",");
        }else if(String.class == type){
            return String.valueOf(in);
        }else if(boolean.class == type){
            return Boolean.valueOf(String.valueOf(in));
        }else if(int[].class == type){
            String[] data = (String[]) convert(String[].class, in);
            int[] array = new int[data.length];
            int i=0;
            for(String s: data){
                array[i++]=Integer.valueOf(s);
            }
            return array;
        }else if(int.class == type){
            return Integer.valueOf(String.valueOf(in));
        }else if(double.class == type){
            return Double.valueOf(String.valueOf(in));
        }else if(float.class == type){
            return Float.valueOf(String.valueOf(in));
        }
        return in;
    }
    @RequestMapping("/create")
    public Status createPreset(@RequestParam("configs[]") String[] configs) {
        if(configs.length>0 && configs.length%2==0) {
            VideoPreset newPreset = new VideoPreset();
            for (int i=0;i<configs.length;i+=2) {
                try {
                    Field f = newPreset.getClass().getField(configs[i]);
                    if(!f.isAnnotationPresent(GraphId.class)) {
                        f.set(newPreset, convert(f.getType(), configs[i + 1]));
                    }
                }catch (Exception e){
                    return new Status(0, e);
                }
            }
            if(videoPresetRepo.findByName(newPreset.getName())==null){
                videoPresetRepo.save(newPreset);
                return new Status(1, newPreset);
            }
            return new Status(0, "Preset with that name already exists!");
        }
        return new Status(0, configs);
    }

    @RequestMapping("/configs")
    public Status createPresetConfig() {
        List<TreeMap<String, Object>> configOptions = new ArrayList<TreeMap<String, Object>>();
        VideoPreset defaultPreset = new VideoPreset();
        for(Field f : defaultPreset.getClass().getDeclaredFields()){
            if(!f.isAnnotationPresent(GraphId.class)) {
                TreeMap<String, Object> tmp = new TreeMap<String, Object>();
                try {
                    tmp.put("name", f.getName());
                    tmp.put("type", f.getType().getSimpleName());
                    tmp.put("default", f.get(defaultPreset));
                } catch (Exception e) {
                }
                configOptions.add(tmp);
            }
        }
        return new Status(1, configOptions);
    }

    @RequestMapping("/list")
    public Status listing() {
        List<VideoPreset> presets = videoPresetRepo.all();
        return new Status(((presets.size()>0)?1:0), presets);
    }

    @RequestMapping("/test")
    public Status makeNewPreset(){
        return new Status(1, null);
    }

    @RequestMapping("/add")
    public byte[] addPreset() {
        try {
            byte[] out = IOUtils.toByteArray(IndexController.class.getClassLoader().getResourceAsStream("www/addPreset.html"));
            return out;
        }catch(Exception e){

        }
        return null;
    }
}
