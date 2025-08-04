package io.pleo.andanothernicholastestingdirectorychangesnicholas.app.modules

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.inject.Provides
import feign.Client
import feign.auth.BasicAuthRequestInterceptor
import feign.okhttp.OkHttpClient
import io.pleo.andanothernicholastestingdirectorychangesnicholas.core.services.ExampleKafkaProductionService
import io.pleo.app.modules.KotlinAbstractModule
import io.pleo.kafka.dropwizard.lifecycle.KafkaProducerManaged
import io.pleo.prop.core.Default
import io.pleo.prop.core.Prop
import io.pleo.serialization.serialization.providers.DefaultObjectMapperProvider
import jakarta.inject.Named
import jakarta.inject.Singleton

class AndAnotherNicholasTestingDirectoryChangesNicholasModule : KotlinAbstractModule() {
    override fun configure() {
        bind<ObjectMapper>().toProvider<DefaultObjectMapperProvider>().asSingleton()
        bind<Client>().toInstance(OkHttpClient())
        bind<KafkaProducerManaged<String, Any>>().asSingleton()
        bind<ExampleKafkaProductionService>().asSingleton()
    }

    @Suppress("unused")
    @Provides
    @Singleton
    fun basicAuthRequestInterceptor(
        @Default("<default needed to boot a service, should be removed once actual values are on AWS SecretsManager>")
        @Named("service.username") username: Prop<String>,
        @Default("<default needed to boot a service, should be removed once actual values are on AWS SecretsManager>")
        @Named("service.password") password: Prop<String>,
    ): BasicAuthRequestInterceptor =
        BasicAuthRequestInterceptor(
            username(),
            password(),
        )
}
