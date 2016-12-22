package com.animecap.system.models.repos;

import com.animecap.system.models.Stream;
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
public interface StreamRepository extends GraphRepository<Stream> {
    @Query("MATCH (:Video {original:{originalUUID}})-[:HAS_STREAM]->(stream:Stream {codec_type:{codecType}}) RETURN stream")
    List<Stream> findByUUIDAndType(@Param("originalUUID") String original, @Param("codecType") String codecType);

    @Query("MATCH (:Video {original:{originalUUID}})-[:HAS_STREAM]->(stream:Stream {codec_name:{type}}) RETURN stream")
    List<Stream> findByUUIDAndSubtitleType(@Param("originalUUID") String original, @Param("type") String type);

}
