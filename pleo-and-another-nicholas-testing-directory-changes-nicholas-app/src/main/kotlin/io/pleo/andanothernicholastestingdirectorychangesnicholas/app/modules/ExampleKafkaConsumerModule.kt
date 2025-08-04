package io.pleo.andanothernicholastestingdirectorychangesnicholas.app.modules

import com.google.inject.Provides
import io.pleo.andanothernicholastestingdirectorychangesnicholas.core.kafka.models.ExampleKafkaModel
import io.pleo.andanothernicholastestingdirectorychangesnicholas.core.services.ExampleKafkaConsumptionService
import io.pleo.app.modules.KotlinAbstractModule
import io.pleo.features.Feature
import io.pleo.features.Flow
import io.pleo.kafka.dropwizard.lifecycle.DefaultKafkaListenerContext
import io.pleo.kafka.dropwizard.lifecycle.KafkaListenerContext
import io.pleo.kafka.dropwizard.lifecycle.SqsKafkaLazyJsonListenerManaged
import io.pleo.kafka.dropwizard.lifecycle.sqsKafkaLazyJsonListenerManaged
import io.pleo.prop.core.Prop
import jakarta.inject.Named

class ExampleKafkaConsumerModule : KotlinAbstractModule() {
    override fun configure() {
        bind<DefaultKafkaListenerContext>()
        // Use `DefaultKafkaListenerContext` when the application is running.
        bind<KafkaListenerContext>().to<DefaultKafkaListenerContext>()
        bind<ExampleKafkaConsumptionService>().asSingleton()
    }

    /**
     * Provide a Kafka consumer for the Guice injection on the topics described in 'application.properties'.
     */
    @Provides
    fun provideExampleConsumer(
        @Named("sqs.dlq.example.url") deadLetterQueueUrl: Prop<String>,
        @Named("kafka.consumer.example.topics") topics: Prop<List<String>>,
        // `KafkaListenerContext` is used as the high-level type to allow Vanguard testing of Kafka consumers/producers.
        kafkaListenerContext: KafkaListenerContext,
        exampleKafkaConsumptionService: ExampleKafkaConsumptionService,
    ): SqsKafkaLazyJsonListenerManaged<ExampleKafkaModel> =
        sqsKafkaLazyJsonListenerManaged(
            topics = topics,
            sqsDlqUrl = deadLetterQueueUrl,
            consume = exampleKafkaConsumptionService::handle,
            pleoFeatureAndFlow =
                Pair(
                    Feature.TEST,
                    Flow.EMPTY_FLOW,
                ),
            kafkaListenerContext = kafkaListenerContext,
        )
}
