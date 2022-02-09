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

    init {
        /*
        This registers the producer with the broker as one that can use transactions,
        identifying it by its transactional.id and a sequence number, or epoch.
        In turn, the broker will use these to write-ahead any actions to a transaction log.
         */
        producer.initTransactions()
    }

    fun produceNotification(key: String, value: Notification): Future<RecordMetadata> {

        try {
            producer.beginTransaction()
            val future = producer.send(
                ProducerRecord<String, Notification>(
                    topicName,
                    key,
                    value
                )
            )
            producer.commitTransaction()
            return future
        } catch (e: Exception) {
            producer.abortTransaction()
            println("Error while produce ${e.message}")
            throw e
        } finally {

        }
    }

    fun produceBulkNotifications(values: List<Notification>): Future<RecordMetadata>? {
        try {
            producer.beginTransaction()
            var future: Future<RecordMetadata>? = null
            for (notification in values) {
                future = producer.send(
                    ProducerRecord<String, Notification>(
                        topicName,
                        notification.getUuid(),
                        notification
                    )
                )
            }

            producer.commitTransaction()
            return future
        } catch (e: Exception) {
            producer.abortTransaction()
            println("Error while produce ${e.message}")
            throw e
        } finally {

        }
    }

}