package ru.company.kafka.consumer.config;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.*;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerde;
import ru.company.kafka.model.Account;
//import ru.company.kafka.model.cassandra.AccountWithAdditionalInfo;
import ru.company.kafka.model.Address;


import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafkaStreams
public class KafkaStreamConfig {

    @Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
    public KafkaStreamsConfiguration kStreamsConfigs(KafkaProperties kafkaProperties) {
        Map<String, Object> config = new HashMap<>();
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, kafkaProperties.getClientId());
        config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, JsonSerde.class);
        config.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, "false");
        return new KafkaStreamsConfiguration(config);
    }


    @Bean
    public Topology createTopology(StreamsBuilder streamsBuilder) {
        KTable<String, Account> accountStream =
                streamsBuilder.table("input-topic", Consumed.with(Serdes.String(), new JsonSerde<>(Account.class)));

        KTable<String, Address> addressStream =
                streamsBuilder.table("output-topic", Consumed.with(Serdes.String(), new JsonSerde<>(Address.class)));

//        KTable<String, AccountWithAdditionalInfo> joined = accountStream.join(addressStream,
//                (leftValue, rightValue) -> {
//                    System.out.println(leftValue);
//                    System.out.println(rightValue);
//                    return new AccountWithAdditionalInfo();
//                }
//        );

        return streamsBuilder.build();
    }
}
