package io.pleo.andanothernicholastestingdirectorychangesnicholas.core.services

import io.pleo.andanothernicholastestingdirectorychangesnicholas.core.kafka.models.ExampleKafkaModel
import io.pleo.kafka.Headers
import io.pleo.kafka.dropwizard.lifecycle.SqsKafkaLazyJsonListenerManaged
import io.pleo.kafka.utils.metadataHeaders
import io.pleo.logging.KotlinLogging
import jakarta.inject.Inject

private val logger = KotlinLogging.logger {}

class ExampleKafkaConsumptionService
    @Inject
    constructor() {
        @Suppress("unused")
        @Inject
        lateinit var listenerManaged: SqsKafkaLazyJsonListenerManaged<ExampleKafkaModel>

        val consumedMessages = mutableListOf<ExampleKafkaModel>()

        /**
         * Handles consumed Kafka messages.
         * This handler only adds consumed messages to a list for example purposes.
         */
        fun handle(
            instance: ExampleKafkaModel,
            headers: Headers,
        ) {
            logger.info {
                "Received '${ExampleKafkaModel::class.java.simpleName}' event over Kafka '${instance.id}' " +
                    "of type '${instance.type}' with message '${instance.message}'; " +
                    "headers: ${headers.metadataHeaders}"
            }
            consumedMessages.add(instance)
        }

        fun emptyConsumedMessages() {
            while (consumedMessages.isNotEmpty()) {
                consumedMessages.removeFirst()
            }
        }
    }
