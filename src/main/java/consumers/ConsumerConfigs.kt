package consumers
import io.confluent.kafka.serializers.KafkaAvroDeserializer
import org.apache.kafka.clients.consumer.ConsumerConfig
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
            /*
            The following configs will be added to the KafkaConsumer client:
             isolation.level
            Here are the possible values:
            read_uncommitted: consume all available messages in offset ordering. This is the default value.
            read_committed: only consume non-transactional messages or transactional messages that are already committed, in offset ordering.
            Default: read_uncommitted
             */
            properties[ConsumerConfig.DEFAULT_ISOLATION_LEVEL] = "read_committed"
            properties["value.deserializer"] = KafkaAvroDeserializer::class.java
            return properties
        }
    }
}