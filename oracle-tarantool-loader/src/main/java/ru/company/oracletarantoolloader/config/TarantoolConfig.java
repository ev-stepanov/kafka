package ru.company.oracletarantoolloader.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.tarantool.TarantoolClusterClient;
import org.tarantool.TarantoolClusterClientConfig;

@Configuration
public class TarantoolConfig {
    @Value("${tarantool.db.cluster.user}")
    private String username;
    @Value("${tarantool.db.cluster.password:#{null}}")
    private String password;

    @Value("${tarantool.db.cluster.master.address}")
    private String hostClusterMaster;
    @Value("${tarantool.db.cluster.master.port}")
    private int portClusterMaster;
    @Value("${tarantool.db.cluster.slave.address}")
    private String hostClusterSlave;
    @Value("${tarantool.db.cluster.slave.port}")
    private int portClusterSlave;

    @Bean
    public TarantoolClusterClient getTarantoolClusterClient() {
        TarantoolClusterClientConfig config = new TarantoolClusterClientConfig();
        config.username = username;
        config.password = password;
        config.connectionTimeout = 2_000;
        config.retryCount = 3;

        String[] nodes = new String[] { hostClusterMaster + ":" + portClusterMaster, hostClusterSlave + ":" + portClusterSlave};

        return new TarantoolClusterClient(config, nodes);
    }
}
