package com.animecap.system.models.repos;

import com.animecap.system.models.Task;
import com.animecap.system.models.VideoPreset;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Nathaniel on 12/18/2016.
 */
@Repository
public interface VideoPresetRepository extends GraphRepository<VideoPreset> {
    @Query("MATCH (vp:VideoPreset{name:{name}}) return vp")
    VideoPreset findByName(@Param("name") String name);

    @Query("MATCH (presets:VideoPreset) return presets")
    List<VideoPreset> all();
}
