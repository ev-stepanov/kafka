package ru.company.kafka.addressgenerator;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerde;
import ru.company.kafka.addressgenerator.dto.AccountDto;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafkaStreams
public class KafkaStreamConfig {

    @Value("${kafka.topic.input}")
    private String inputTopic;

    @Value("${kafka.topic.even-output}")
    private String outputTopic;

    @Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
    public KafkaStreamsConfiguration kStreamsConfigs(KafkaProperties kafkaProperties) {
        Map<String, Object> config = new HashMap<>();
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, kafkaProperties.getClientId());
        config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, JsonSerde.class);
        config.put(JsonDeserializer.TRUSTED_PACKAGES, "com.springmiddleware.entities");
        config.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, "false");
        config.put(StreamsConfig.CLIENT_ID_CONFIG, "square-finder");
        return new KafkaStreamsConfiguration(config);
    }


    @Bean
    public KStream<String, AccountDto> kStream(StreamsBuilder kStreamBuilder) {
        KStream<String, AccountDto> stream = kStreamBuilder.stream(inputTopic, Consumed.with(Serdes.String(), new JsonSerde<>(AccountDto.class)));

        stream.filter((k, v) -> {
            System.out.println(k + "    " + v);
            return v == null;
        } )
                .mapValues(v -> {
                    System.out.println("Processing :: " + v);
                    return v;
                })
                .to(outputTopic);
        return stream;
    }

}
