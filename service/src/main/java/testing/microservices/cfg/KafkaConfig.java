package testing.microservices.cfg;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

import java.util.Map;

/**
 * Bean configuration class to setup the factory beans necessary to initialize an appropriate kafka consumer
 * This bean has two sources for the configuration one is the {@code application.properties} the other is the
 * environment-variable defined at {@code KafkaConnectionProperties}. The properties at the application.properties
 * are more static values, you have to change in rare cases. The value at {@code KafkaConnectionProperties} more
 * for runtime configuration depends on the environment
 */

@Configuration
@EnableKafka
@EnableConfigurationProperties(KafkaProperties.class)
public class KafkaConfig {

  @Value("${testing.kafka.consumer.auto-offset-reset}")
  String offsetReset;
  @Value("${testing.kafka.consumer.enable-auto-commit:}")
  String autoCommit;

  @Autowired
  KafkaConnectionProperties kafkaConnectionProperties;

  @Value("${testing.kafka.group-id}")
  private String groupId;

  /**
   * Get the Kafka consumer configs.
   *
   * @return map with the Kafka consumer configs
   */
  @Bean
  public Map<String, Object> consumerConfigs() {
    Map<String, Object> defaultProps = new KafkaProperties().buildConsumerProperties();
    defaultProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConnectionProperties.getBootstrapServers());
    defaultProps.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
    defaultProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, offsetReset);
    defaultProps.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, autoCommit);
    return defaultProps;
  }

  @Bean
  public ConsumerFactory<String, String> consumerFactory() {
    return new DefaultKafkaConsumerFactory<>(consumerConfigs());
  }

  /**
   * Get the Kafka listener container factory.
   *
   * @return the Kafka listener container factory
   */
  @Bean
  public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListenerContainerFactory() {
    ConcurrentKafkaListenerContainerFactory<String, String> factory =
      new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(consumerFactory());
    return factory;
  }
}

