@file:Suppress("FunctionNaming")

package io.pleo.andanothernicholastestingdirectorychangesnicholas.functest.tests

import com.google.common.truth.Truth.assertThat
import dev.misfitlabs.kotlinguice4.getInstance
import io.pleo.andanothernicholastestingdirectorychangesnicholas.core.kafka.models.ExampleKafkaModel
import io.pleo.andanothernicholastestingdirectorychangesnicholas.core.services.ExampleKafkaConsumptionService
import io.pleo.andanothernicholastestingdirectorychangesnicholas.core.services.ExampleKafkaProductionService
import io.pleo.andanothernicholastestingdirectorychangesnicholas.functest.base.AndAnotherNicholasTestingDirectoryChangesNicholasVanguardTest
import io.pleo.andanothernicholastestingdirectorychangesnicholas.functest.base.awaitUntil
import io.pleo.andanothernicholastestingdirectorychangesnicholas.kafka.models.ExampleEvent
import io.pleo.andanothernicholastestingdirectorychangesnicholas.kafka.models.base.EventType
import io.pleo.vanguard.framework.extension.EnableExtension
import io.pleo.vanguard.framework.extension.ExtensionKafka
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

// This file illustrates example of use testing simple Kafka consumption and production via a service.

@EnableExtension(ExtensionKafka::class)
class KafkaTests : AndAnotherNicholasTestingDirectoryChangesNicholasVanguardTest() {
    private val testKafkaKey = "test-example-entity"
    private val testConsumerTopicPropName = "kafka.consumer.example.topics"
    private val testProducerTopicPropName = "kafka.producer.example.topics"

    private inline fun <reified T> getInstance(): T = application.injector.getInstance()

    @BeforeEach
    fun emptyServices() {
        getInstance<ExampleKafkaConsumptionService>().emptyConsumedMessages()
        getInstance<ExampleKafkaProductionService>().emptyProducedMessages()
    }

    @Test
    fun `should consume an example Kafka message`() {
        val consumptionService = getInstance<ExampleKafkaConsumptionService>()
        val expectedModel =
            ExampleKafkaModel(
                id = "test",
                type = ExampleKafkaModel.EventType.EXAMPLE,
                message = "test-message",
            )

        // Produce a Kafka message which the service under test is subscribed to.
        extensionKafka?.postJsonMessage(
            testConsumerTopicPropName,
            testKafkaKey,
            ExampleEvent("test", EventType.EXAMPLE, "test-message"),
        )

        awaitUntil {
            assertThat(consumptionService.consumedMessages).containsExactly(expectedModel)
        }
    }

    @Test
    fun `handles malformed Kafka messages`() {
        val consumptionService = getInstance<ExampleKafkaConsumptionService>()

        // Produce a Kafka message which the service under test is subscribed to.
        extensionKafka?.postJsonMessage(testConsumerTopicPropName, testKafkaKey, "")

        awaitUntil {
            assertThat(consumptionService.consumedMessages).isEmpty()
        }
    }

    @Test
    fun `should produce an example Kafka message`() {
        val productionService = getInstance<ExampleKafkaProductionService>()
        val event =
            ExampleEvent(
                id = "test",
                message = "test-message-123",
            )

        productionService.publish(event)

        awaitUntil {
            assertThat(productionService.producedMessages).isNotEmpty()
            val message = extensionKafka?.pollForJsonMessage<ExampleEvent>(testProducerTopicPropName)
            assertThat(message).isEqualTo(event)
        }
    }
}
