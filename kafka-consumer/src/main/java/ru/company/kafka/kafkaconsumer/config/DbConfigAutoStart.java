package ru.company.kafka.kafkaconsumer.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.KeyspaceOption;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import java.util.Collections;
import java.util.List;

@Configuration
@EnableCassandraRepositories(basePackages = "ru.company.kafka.kafkaconsumer.repository")
public class DbConfigAutoStart extends AbstractCassandraConfiguration {

    @Override
    public String getContactPoints() {
        return "192.168.99.100";
    }

    @Override
    protected int getPort() {
        return 9042;
    }

    @Override
    protected boolean getMetricsEnabled() {
        return false;
    }

    @Override
    public String getKeyspaceName() {
        return "exampleKeyspace";
    }

    @Override
    protected List<CreateKeyspaceSpecification> getKeyspaceCreations() {
        CreateKeyspaceSpecification specification = CreateKeyspaceSpecification
                .createKeyspace("exampleKeyspace").ifNotExists()
                .with(KeyspaceOption.DURABLE_WRITES, true).withSimpleReplication();
        return Collections.singletonList(specification);
    }

    @Override
    public SchemaAction getSchemaAction() {
        return SchemaAction.CREATE_IF_NOT_EXISTS;
    }

    @Override
    public String[] getEntityBasePackages() {
        return new String[]{"ru.company.kafka.kafkaconsumer.model"};
    }
}