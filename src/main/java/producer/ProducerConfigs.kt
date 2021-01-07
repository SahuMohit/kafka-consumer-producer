package producer
import io.confluent.kafka.serializers.KafkaAvroSerializer
import java.util.*

class ProducerConfigs {
    companion object {
        fun getNotificationProducerConfigs(
            bootStrapServerURL: String,
            schemaRegistryURL: String
        ): Properties {
            val props = Properties()
            props["bootstrap.servers"] = bootStrapServerURL
            props["acks"] = "all"
            props["retries"] = 0
            props["batch.size"] = 16384
            props["linger.ms"] = 1
            props["buffer.memory"] = 33554432
            props["key.serializer"] = "org.apache.kafka.common.serialization.StringSerializer"
            props["schema.registry.url"] = schemaRegistryURL
            props["value.serializer"] = KafkaAvroSerializer::class.java
            return props
        }
    }

}