package com.animecap.system.neo4j;

import org.neo4j.ogm.session.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.transaction.Neo4jTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by Nathaniel on 12/18/2016.
 */
@Configuration
@EnableTransactionManagement
@ComponentScan("com.animecap.system")
@EnableNeo4jRepositories("com.animecap.system.models.repos")
public class PersistenceContext extends Neo4jConfiguration {
    @Override
    public SessionFactory getSessionFactory() {
        return new SessionFactory("com.animecap.system.models");
    }
}