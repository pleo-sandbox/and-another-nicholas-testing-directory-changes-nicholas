package io.pleo.andanothernicholastestingdirectorychangesnicholas.app.modules

import io.pleo.andanothernicholastestingdirectorychangesnicholas.kafka.ExampleKafkaProducer
import io.pleo.app.modules.KotlinAbstractModule
import io.pleo.kafka.KafkaProducerConfigFactory
import io.pleo.kafka.dropwizard.lifecycle.KafkaProducerManaged

class ExampleKafkaProducerModule : KotlinAbstractModule() {
    override fun configure() {
        bind<ExampleKafkaProducer>().asSingleton()
        bind<KafkaProducerManaged<String, Any>>().asSingleton()
        bind<KafkaProducerConfigFactory>().asSingleton()
    }
}
