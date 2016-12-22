package com.animecap.system.models.repos;

import com.animecap.system.models.Video;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Nathaniel on 12/18/2016.
 */
@Repository
public interface VideoRepository extends GraphRepository<Video> {
    Video findByOriginal(String original);
}
