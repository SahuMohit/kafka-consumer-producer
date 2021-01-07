package consumers

import models.Notification
import org.apache.kafka.clients.consumer.Consumer
import org.apache.kafka.clients.consumer.KafkaConsumer
import java.util.*

class NotificationConsumer(
    private val topicName: String,
    groupName: String,
    bootstrapURL: String,
    schemaRegistryURL: String
) {
    private val consumer: Consumer<String, Notification> = KafkaConsumer(
        ConsumerConfigs.getNotificationConsumerConfigs(
            groupName = groupName,
            schemaRegistryURL = schemaRegistryURL,
            bootStrapServerURL = bootstrapURL
        )
    )

    fun consume() {
        println("-------Starting Notification consumer-----")
        consumer.subscribe(Arrays.asList(topicName))
        val r = {
            while (true) {
                try {
                    val records = consumer.poll(1000)
                    val iterator = records.iterator()
                    while (iterator.hasNext()) {
                        val currentRecord = iterator.next()
                        try {
                            println("Record ${currentRecord.value()}")
                        } catch (e: Exception) {
                            println("Error in consumer KEY: ${currentRecord.key()} VALUE: ${currentRecord.value()} OFFSET: ${currentRecord.offset()} PARTITION: ${currentRecord.partition()} ")
                        } finally {

                        }
                    }
                } catch (e: Exception) {
                    println("Error in notification ${e.message}")
                    e.printStackTrace()
                }
            }
        }
        val t = Thread(r)
        t.start()
        println("-------Notification consumer started-----")
    }

}