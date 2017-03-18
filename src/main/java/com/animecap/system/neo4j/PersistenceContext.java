package com.animecap.system.neo4j;

import org.neo4j.ogm.session.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
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
@PropertySource("classpath:ogm.properties")
public class PersistenceContext {
    @Bean
    public SessionFactory sessionFactory() {
        return new SessionFactory("com.animecap.system.models");
    }

    @Bean
    public Neo4jTransactionManager transactionManager() {
        return new Neo4jTransactionManager(sessionFactory());
    }
}