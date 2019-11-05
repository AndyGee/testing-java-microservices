package testing.microservices.cfg;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * This class is used to configure the redis server from outside the application
 * You can use the environment {@code }redis.hostname} to the java process to set this property
 */
@Component
@ConfigurationProperties("redis")
public class RedisConnectionProperties {

  public static final String DEFAULT = "localhost";
  private String hostname = DEFAULT;

  public String getHostname() {
    return hostname;
  }

  public void setHostname(String hostname) {
    this.hostname = hostname;
  }
}
