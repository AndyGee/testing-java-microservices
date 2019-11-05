package testing.microservices.cfg;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MessagesConfig implements WebMvcConfigurer {

  @Value("${spring.messages.basename}")
  private String springMessagesBasename = null;

  /**
   * Create the message source bean.
   */
  @Bean
  public MessageSource messageSource() {
    ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
    messageSource.setBasenames(springMessagesBasename, "i18n/nav");
    return messageSource;
  }
}
