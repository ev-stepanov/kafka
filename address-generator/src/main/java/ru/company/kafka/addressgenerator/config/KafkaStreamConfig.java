package ru.company.kafka.addressgenerator.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
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
import org.springframework.kafka.support.serializer.JsonSerde;
import ru.company.kafka.addressgenerator.service.AddressGeneratorService;
import ru.company.kafka.model.producer.AddressDto;
import ru.company.kafka.model.producer.BankAccountDto;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
@EnableKafkaStreams
@RequiredArgsConstructor
public class KafkaStreamConfig {
    private final AddressGeneratorService addressGeneratorService;

    @Value("${kafka.topic.bank-accounts}")
    private String bankAccountsTopic;

    @Value("${kafka.topic.address-generator}")
    private String addressGeneratorTopic;

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
        try (JsonSerde<BankAccountDto> bankAccountDtoJsonSerde = new JsonSerde<>(BankAccountDto.class);
             JsonSerde<AddressDto> addressDtoJsonSerde = new JsonSerde<>(AddressDto.class)) {
            KStream<String, BankAccountDto> accountStream =
                    streamsBuilder.stream(bankAccountsTopic, Consumed.with(Serdes.String(), bankAccountDtoJsonSerde));

            accountStream
                    .filter((id, account) -> account.getLastName().startsWith("A"))
                    .mapValues((id, account) -> {
                        log.info("For " + id + "was generated new address");
                        return addressGeneratorService.generateAddress(account.getUuid());
                    })
                    .to(addressGeneratorTopic, Produced.with(Serdes.String(), addressDtoJsonSerde));
        }

        return streamsBuilder.build();
    }
}
