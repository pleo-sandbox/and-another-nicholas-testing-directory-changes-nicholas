package io.pleo.andanothernicholastestingdirectorychangesnicholas.core.services

import io.pleo.andanothernicholastestingdirectorychangesnicholas.kafka.ExampleKafkaProducer
import io.pleo.andanothernicholastestingdirectorychangesnicholas.kafka.models.ExampleEvent
import io.pleo.logging.KotlinLogging
import jakarta.inject.Inject
import org.apache.kafka.clients.producer.RecordMetadata

private val logger = KotlinLogging.logger {}

class ExampleKafkaProductionService
    @Inject
    constructor(
        private val exampleKafkaProducer: ExampleKafkaProducer,
    ) {
        val producedMessages = mutableListOf<RecordMetadata>()

        /**
         * Produces Kafka messages.
         * This only adds produced messages to a list for example purposes.
         */
        @Suppress("unused")
        fun publish(event: ExampleEvent) {
            logger.info { "Publishing 1 ${ExampleEvent::class.simpleName!!} Kafka event." }
            val future = exampleKafkaProducer.publish(event)
            producedMessages.add(future.get())
        }

        fun emptyProducedMessages() {
            while (producedMessages.isNotEmpty()) {
                producedMessages.removeFirst()
            }
        }
    }
