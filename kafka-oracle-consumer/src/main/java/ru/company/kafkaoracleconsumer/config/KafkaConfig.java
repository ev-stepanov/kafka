package ru.company.kafkaoracleconsumer.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.kafka.support.serializer.JsonSerde;
import ru.company.kafkaoracleconsumer.dto.AddressDto;
import ru.company.kafkaoracleconsumer.dto.BankAccountDto;
import ru.company.kafkaoracleconsumer.repository.AddressRepository;
import ru.company.kafkaoracleconsumer.repository.BankAccountRepository;
import ru.company.kafkaoracleconsumer.util.ConverterDto;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Configuration
@EnableKafkaStreams
@RequiredArgsConstructor
public class KafkaConfig {
    private final BankAccountRepository bankAccountRepository;
    private final AddressRepository addressRepository;

    @Value("${kafka.topic.bank-accounts}")
    private String bankAccountsTopic;

    @Value("${kafka.topic.address-generator}")
    private String addressGeneratorTopic;

    @Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
    public KafkaStreamsConfiguration kafkaStreamsConfiguration(KafkaProperties kafkaProperties) {
        Map<String, Object> config = new HashMap<>();
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, kafkaProperties.getClientId());
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, JsonSerde.class);

        return new KafkaStreamsConfiguration(config);
    }

    @Bean
    public Topology kafkaBankAccountStreams(StreamsBuilder streamsBuilder) {
        try (JsonSerde<BankAccountDto> bankAccountDtoJsonSerde = new JsonSerde<>(BankAccountDto.class).ignoreTypeHeaders()) {
            streamsBuilder.stream(bankAccountsTopic, Consumed.with(Serdes.String(), bankAccountDtoJsonSerde))
                    .peek((key, value) -> log.info("Key: {} Value: {}", key, value))
                    .map((key, value) -> new KeyValue<>(key, ConverterDto.convertToBankAccountFrom(value)))
                    .foreach((key, value) -> bankAccountRepository.save(value));
        }
        return streamsBuilder.build();
    }

    @Bean
    public Topology kafkaAddressGeneratorStreams(StreamsBuilder streamsBuilder) {
        try (JsonSerde<AddressDto> addressDtoJsonSerde = new JsonSerde<>(AddressDto.class).ignoreTypeHeaders()) {
            streamsBuilder.stream(addressGeneratorTopic, Consumed.with(Serdes.String(), addressDtoJsonSerde))
                    .peek((key, value) -> log.info("Key: {} Value: {}", key, value))
                    .map((key, value) -> new KeyValue<>(key, ConverterDto.convertToAddressFrom(value)))
                    .foreach((key, value) -> {
                        value.setUuid(UUID.fromString(key));
                        addressRepository.save(value);
                    });
        }
        return streamsBuilder.build();
    }
}
