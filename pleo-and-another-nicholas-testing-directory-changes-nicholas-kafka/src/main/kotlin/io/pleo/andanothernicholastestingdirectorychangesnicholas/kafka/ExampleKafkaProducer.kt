package io.pleo.andanothernicholastestingdirectorychangesnicholas.kafka

import io.pleo.andanothernicholastestingdirectorychangesnicholas.kafka.Bindings.EXAMPLE_TOPICS
import io.pleo.andanothernicholastestingdirectorychangesnicholas.kafka.Bindings.KAFKA_PREFIX
import io.pleo.andanothernicholastestingdirectorychangesnicholas.kafka.models.base.KafkaEvent
import io.pleo.kafka.ExceptionLoggerCallback
import io.pleo.logging.KotlinLogging
import io.pleo.prop.core.Prop
import jakarta.inject.Inject
import jakarta.inject.Named
import java.util.concurrent.Future
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.clients.producer.RecordMetadata

private val logger = KotlinLogging.logger {}

class ExampleKafkaProducer
    @Inject
    constructor(
        @Named(KAFKA_PREFIX) private val topicPrefix: Prop<String>,
        private val kafkaJsonProducer: KafkaProducer<String, Any>,
        @Suppress("unused")
        @Named(EXAMPLE_TOPICS)
        private val exampleTopics: Prop<List<String>>,
    ) {
        /**
         * Publish a Kafka message to the topic defined in the provided message object.
         */
        fun publish(event: KafkaEvent): Future<RecordMetadata> {
            val topicName = event.toTopicName()
            logger.info { "Publishing Kafka event ${event.name()} to topic '$topicName'" }

            return kafkaJsonProducer.send(
                ProducerRecord(
                    topicName,
                    event.id,
                    event,
                ),
                ExceptionLoggerCallback(event),
            )
        }

        private fun KafkaEvent.toTopicName(): String = "${topicPrefix()}-${name().value}"
    }
