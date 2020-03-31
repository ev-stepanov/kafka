package ru.company.kafka.kafkaconsumer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

//@Configuration
//@EnableCassandraRepositories(basePackages = "ru.company.kafka.kafkaconsumer.model")
public class CassandraConfig  {

//    @Value("${spring.cassandra.contactpoints}")
    private String contactPoints;

//    @Value("${spring.cassandra.port}")
    private int port;

//    @Value("${spring.cassandra.keyspace}")
    private String keySpace;

//    @Value("${spring.cassandra.basePackages}")
    private String basePackages;

//    @Override
    protected String getKeyspaceName() {
        return keySpace;
    }

//    @Override
    protected String getContactPoints() {
        return contactPoints;
    }

//    @Override
    protected int getPort() {
        return port;
    }

//    @Override
    public SchemaAction getSchemaAction() {
        return SchemaAction.CREATE_IF_NOT_EXISTS;
    }

//    @Override
    public String[] getEntityBasePackages() {
        return new String[] {basePackages};
    }
}