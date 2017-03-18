package com.animecap.system.models.repos;

import com.animecap.system.models.Video;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Nathaniel on 12/18/2016.
 */
@Repository
public interface VideoRepository extends GraphRepository<Video> {
    Video findByOriginal(String original);

    @Query("MATCH (n:Video{original:{original}})<-[:SOURCE_VIDEO]-(t:Task)-[:HAS_PRESET]->(p:VideoPreset{name:{preset}}), (t)-[:NEW_VIDEO]->(video:Video) return video")
    List<Video> findConvertedByOriginalAndPreset(@Param("original") String original, @Param("preset") String preset);

}
