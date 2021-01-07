import consumers.NotificationConsumer

fun main() {
    val schemaRegistryURL = "http://0.0.0.0:8081"
    val bootstrapURL = "localhost:9092"
    val topicName = "notification"
    val consumer = NotificationConsumer(topicName = topicName ,groupName = "playground" ,schemaRegistryURL = schemaRegistryURL ,bootstrapURL = bootstrapURL)
    consumer.consume()

}