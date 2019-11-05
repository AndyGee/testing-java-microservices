package testing.microservices.service.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ContentImporter {

  private Logger logger = LoggerFactory.getLogger(ContentImporter.class);

  /**
   * Listen on Kafka topic.
   *
   * @param consumerRecord the consumer record read from Kafka topic
   */
  @KafkaListener(topics = "${testing.kafka.topic}", groupId = "${testing.kafka.group-id")
  public void receiveCommand(ConsumerRecord<String, String> consumerRecord) {

    logger.info("Got a mesage: " + consumerRecord);
  }
}
