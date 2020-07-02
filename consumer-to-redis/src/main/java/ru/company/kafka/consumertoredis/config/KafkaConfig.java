package ru.company.kafka.consumertoredis.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.ValueJoiner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.kafka.support.serializer.JsonSerde;
import ru.company.kafka.consumertoredis.model.Address;
import ru.company.kafka.consumertoredis.model.BankAccount;
import ru.company.kafka.consumertoredis.model.BankAccountInfo;
import ru.company.kafka.consumertoredis.util.BankAccountAndAddressValueJoiner;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
@EnableKafkaStreams
@RequiredArgsConstructor
public class KafkaConfig {
    @Value("${kafka.topic.bank-accounts}")
    private String bankAccountsTopic;

    @Value("${kafka.topic.address-generator}")
    private String addressGeneratorTopic;

    private final ReactiveRedisTemplate<String, BankAccountInfo> redisTemplate;

    @Bean
    public ValueJoiner<BankAccount, Address, BankAccountInfo> valueJoiner() {
        return new BankAccountAndAddressValueJoiner();
    }

    @Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
    public KafkaStreamsConfiguration kStreamsConfigs(KafkaProperties kafkaProperties) {
        Map<String, Object> config = new HashMap<>();
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, kafkaProperties.getClientId());
        config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, JsonSerde.class);
        config.put(StreamsConfig.NUM_STANDBY_REPLICAS_CONFIG, 3);
        config.put(StreamsConfig.REPLICATION_FACTOR_CONFIG, 3);
        return new KafkaStreamsConfiguration(config);
    }

    @Bean
    public Topology kStream(StreamsBuilder streamsBuilder) {
        try (final JsonSerde<BankAccount> bankAccountJsonSerde = new JsonSerde<>(BankAccount.class).ignoreTypeHeaders();
             final JsonSerde<Address> addressJsonSerde = new JsonSerde<>(Address.class).ignoreTypeHeaders()) {
            KTable<String, BankAccount> bankAccountKTable = streamsBuilder.table(bankAccountsTopic, Consumed.with(Serdes.String(), bankAccountJsonSerde));
            KTable<String, Address> addressKTable = streamsBuilder.table(addressGeneratorTopic, Consumed.with(Serdes.String(), addressJsonSerde));
            KStream<String, BankAccountInfo> bankAccountInfoKStream = bankAccountKTable.join(addressKTable, valueJoiner()).toStream();
            bankAccountInfoKStream.foreach((key, value) -> redisTemplate.opsForValue()
                    .set(BankAccountInfo.BANK_ACCOUNT_NAME_KEY + key, value)
                    .log()
                    .subscribe()
            );
        }

        return streamsBuilder.build();
    }
}
