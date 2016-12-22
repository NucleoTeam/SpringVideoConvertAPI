package com.animecap.system.models.repos;

import com.animecap.system.models.Task;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Nathaniel on 12/18/2016.
 */
@Repository
public interface TaskRepository extends GraphRepository<Task> {
    Task findByCompleted(int completed);
}
