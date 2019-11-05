package testing.microservices.cfg;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * This class is used to configure the kafka bootstrap servers from outside the application
 * You can use the environment {@code }kafka.bootstrapServers} to the java process to set this property
 */
@Component
@ConfigurationProperties("kafka")
public class KafkaConnectionProperties {

  public static final String DEFAULT = "0.0.0.0:0000";
  private String bootstrapServers = DEFAULT;

  public String getBootstrapServers() {
    return bootstrapServers;
  }

  public void setBootstrapServers(String bootstrapServers) {
    this.bootstrapServers = bootstrapServers;
  }
}
