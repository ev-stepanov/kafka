package ru.company.kafka.addressgenerator.config;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerde;
//import ru.company.kafka.addressgenerator.dto.AddressDto;
import ru.company.kafka.addressgenerator.util.JsonPOJODeserializer;
import ru.company.kafka.addressgenerator.util.JsonPOJOSerializer;
import ru.company.kafka.model.Account;
import ru.company.kafka.model.Address;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafkaStreams
public class KafkaStreamConfig {

    @Value("${kafka.topic.input}")
    private String inputTopic;

    @Value("${kafka.topic.output}")
    private String outputTopic;

    @Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
    public KafkaStreamsConfiguration kStreamsConfigs(KafkaProperties kafkaProperties) {
        Map<String, Object> config = new HashMap<>();
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, kafkaProperties.getClientId());
        config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, JsonSerde.class);
        return new KafkaStreamsConfiguration(config);
    }

    @Bean
    public Topology createTopology(StreamsBuilder streamsBuilder) {
        KStream<String, Account> accountStream =
                streamsBuilder.stream(inputTopic, Consumed.with(Serdes.String(), new JsonSerde<>(Account.class).ignoreTypeHeaders()));

        accountStream
//                .filter((id, account) -> account.getLastName().startsWith("A"))
                .mapValues((id, account) -> {
                    System.out.println(account);
                    return new Address(account.getUuid(), account.getFirstName());
                })
                .to(outputTopic, Produced.with(Serdes.String(), new JsonSerde<>(Address.class).ignoreTypeHeaders()));

        return streamsBuilder.build();
    }
}
