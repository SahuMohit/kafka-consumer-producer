package producer

import models.Notification
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.Producer
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.clients.producer.RecordMetadata
import java.lang.Exception
import java.util.concurrent.Future

class NotificationProducer(
    private val topicName: String,
    private val bootstrapURL: String,
    private val schemaRegistryURL: String
) {
    private val producer: Producer<String, Notification> = KafkaProducer(
        ProducerConfigs.getNotificationProducerConfigs(
            bootStrapServerURL = bootstrapURL,
            schemaRegistryURL = schemaRegistryURL
        )
    )

    fun produceNotification(key: String, value: Notification) : Future<RecordMetadata> {
       return producer.send(
            ProducerRecord<String, Notification>(
                topicName,
                key,
                value
            )
        )
    }

}