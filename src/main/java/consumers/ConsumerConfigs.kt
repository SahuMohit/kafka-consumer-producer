package consumers
import io.confluent.kafka.serializers.KafkaAvroDeserializer
import java.util.*

class ConsumerConfigs {
    companion object {
        fun getNotificationConsumerConfigs(
            groupName: String,
            bootStrapServerURL: String,
            schemaRegistryURL: String
        ): Properties {
            val properties = Properties()
            properties["bootstrap.servers"] = bootStrapServerURL
            properties["group.id"] = groupName
            properties["auto.offset.reset"] = "latest"
            properties["key.deserializer"] = "org.apache.kafka.common.serialization.StringDeserializer"
            properties["schema.registry.url"] = schemaRegistryURL
            properties["specific.avro.reader"] = "true"
            properties["value.deserializer"] = KafkaAvroDeserializer::class.java
            return properties
        }
    }
}