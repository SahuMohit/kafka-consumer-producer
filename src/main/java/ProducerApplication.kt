import models.Notification
import producer.NotificationProducer
import java.util.*

fun main() {
    val schemaRegistryURL = "http://0.0.0.0:8081"
    val bootstrapURL = "localhost:9092"
    val topicName = "inventoryTopic"

    val producer = NotificationProducer(topicName, bootstrapURL, schemaRegistryURL)
    while (true) {
        val future = producer.produceBulkNotifications(values = getNotifications())
        val recordMetadata = future?.get()
        println("Bulk Records Produced, last offset ${recordMetadata?.offset()}, partition ${recordMetadata?.partition()}")
        Thread.sleep(2000L)
    }
}


fun getNotifications(): List<Notification> {
    val notification = Notification()
    notification.setUuid("One")
    notification.setOrgId("orgId")
    notification.setSubscriber(UUID.randomUUID().toString())
//    notification.setType("Tracknet")

    val n2 = Notification()
    n2.setUuid("Two")
    n2.setOrgId("orgId")
    n2.setSubscriber(UUID.randomUUID().toString())
//    n2.setType("Tracknet")

    val n3 = Notification()
    n3.setUuid("Three")
    n3.setOrgId("orgId")
    n3.setSubscriber(UUID.randomUUID().toString())
//    n3.setType("Tracknet")
    return listOf(notification, n2, n3)

}