package producer
import io.confluent.kafka.serializers.KafkaAvroSerializer
import org.apache.kafka.clients.producer.KafkaProducer
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
            props["batch.size"] = 16384
            props["linger.ms"] = 1
            props["buffer.memory"] = 33554432
            props["key.serializer"] = "org.apache.kafka.common.serialization.StringSerializer"
            props["schema.registry.url"] = schemaRegistryURL
            /*
               Exactly-once in order semantics per partition
               See also {@link https://www.confluent.io/blog/transactions-apache-kafka/}
             */
            props["enable.idempotence"] = true
            //retries must be non zero when enable.idempotence=true
            props["retries"] = 1

            props["transactional.id"] = "T0002"
            props["value.serializer"] = KafkaAvroSerializer::class.java
            return props
        }
    }

}