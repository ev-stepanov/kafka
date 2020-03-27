package ru.company.kafka.addressgenerator.config;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.Serializer;
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
import ru.company.kafka.addressgenerator.util.JsonPOJODeserializer;
import ru.company.kafka.addressgenerator.util.JsonPOJOSerializer;

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
        config.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, "false");
        return new KafkaStreamsConfiguration(config);
    }


    @Bean
    public KStream<String, AccountDto> kStream(StreamsBuilder kStreamBuilder) {
        Map<String, Class<AccountDto>> configs = Collections.singletonMap("JsonPOJOClass", AccountDto.class);
        final Serializer<AccountDto> pageViewSerializer = new JsonPOJOSerializer<>();
        pageViewSerializer.configure(configs, false);
        final Deserializer<AccountDto> pageViewDeserializer = new JsonPOJODeserializer<>();
        pageViewDeserializer.configure(configs, false);

        KStream<String, AccountDto> stream = kStreamBuilder.stream(inputTopic, Consumed.with(Serdes.String(),  Serdes.serdeFrom(pageViewSerializer, pageViewDeserializer)));

        stream.filter((k, v) -> {
            System.out.println("LastName: " + v.getLastName());
            return v.getLastName().toUpperCase().startsWith("M");
        } )
                .mapValues(v -> {
                    System.out.println("Processing :: " + v);
                    return v;
                })
                .to(outputTopic);
        return stream;
    }

}
