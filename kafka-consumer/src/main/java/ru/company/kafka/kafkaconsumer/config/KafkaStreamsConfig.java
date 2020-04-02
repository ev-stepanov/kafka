package ru.company.kafka.kafkaconsumer.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KTable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerde;
import ru.company.kafka.kafkaconsumer.model.BankAccountInfo;
import ru.company.kafka.kafkaconsumer.repository.AccountRepository;
import ru.company.kafka.kafkaconsumer.util.Converter;
import ru.company.kafka.model.producer.AddressDto;
import ru.company.kafka.model.producer.BankAccountDto;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafkaStreams
@Slf4j
public class KafkaStreamsConfig {
    @Value("${kafka.topic.bank-accounts}")
    private String bankAccountsTopic;

    @Value("${kafka.topic.address-generator}")
    private String addressGeneratorTopic;

    private final AccountRepository repository;

    public KafkaStreamsConfig(AccountRepository repository) {
        this.repository = repository;
    }

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
        try (JsonSerde<BankAccountDto> bankAccountDtoJsonSerde = new JsonSerde<>(BankAccountDto.class);
             JsonSerde<AddressDto> addressDtoJsonSerde = new JsonSerde<>(AddressDto.class)) {
            KTable<String, BankAccountDto> accountStream =
                    streamsBuilder.table(bankAccountsTopic, Consumed.with(Serdes.String(), bankAccountDtoJsonSerde));

            KTable<String, AddressDto> addressStream =
                    streamsBuilder.table(addressGeneratorTopic, Consumed.with(Serdes.String(), addressDtoJsonSerde));

            KTable<String, BankAccountInfo> bankAccountInfoKTable = accountStream.join(addressStream,
                    (bankAccount, addressDto) -> {
                        log.info("Data was joined by uuid " + bankAccount.getUuid());
                        return Converter.addressAndAccountToBankAccountInfo(bankAccount, addressDto);
                    });

            bankAccountInfoKTable.toStream().foreach((id, bankAccountInfo) -> repository.save(bankAccountInfo));
        }
        return streamsBuilder.build();
    }
}
