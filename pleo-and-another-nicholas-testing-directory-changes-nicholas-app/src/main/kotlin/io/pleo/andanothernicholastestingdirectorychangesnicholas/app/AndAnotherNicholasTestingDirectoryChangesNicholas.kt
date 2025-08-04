package io.pleo.andanothernicholastestingdirectorychangesnicholas.app

import com.google.inject.Binder
import io.pleo.andanothernicholastestingdirectorychangesnicholas.app.modules.AndAnotherNicholasTestingDirectoryChangesNicholasModule
import io.pleo.andanothernicholastestingdirectorychangesnicholas.app.modules.DataModule
import io.pleo.andanothernicholastestingdirectorychangesnicholas.app.modules.ElaraModule
import io.pleo.andanothernicholastestingdirectorychangesnicholas.app.modules.ExampleKafkaConsumerModule
import io.pleo.andanothernicholastestingdirectorychangesnicholas.app.modules.ExampleKafkaProducerModule
import io.pleo.andanothernicholastestingdirectorychangesnicholas.app.modules.ExampleMovieModule
import io.pleo.andanothernicholastestingdirectorychangesnicholas.app.modules.HealthModule
import io.pleo.andanothernicholastestingdirectorychangesnicholas.app.modules.V1Module
import io.pleo.app.framework.PleoKotlinRestApplication
import io.pleo.app.modules.AsyncCoroutineModule
import io.pleo.kafka.KafkaProducerModule

fun main(vararg args: String) {
    AndAnotherNicholasTestingDirectoryChangesNicholas().runServer(*args)
}

open class AndAnotherNicholasTestingDirectoryChangesNicholas : PleoKotlinRestApplication(useApiStandardExceptionMappers = true) {
    // This name is used for fetching AWS Secrets, etc. And by default, it is inferred from
    // the Dropwizard 'Application' class name.
    override fun getName(): String = "and-another-nicholas-testing-directory-changes-nicholas"

    // We need to override the resource package name when the app name is overridden above because the
    // jvm-commons 'PleoRestApplication' infers the package name as "io.pleo." + name.lowercase() + ".rest".
    override val resourcePackage = "io.pleo.andanothernicholastestingdirectorychangesnicholas.rest"

    override fun configure(binder: Binder) {
        super.configure(binder)
        binder.apply {
            install(HealthModule())
            install(V1Module())
            install(AndAnotherNicholasTestingDirectoryChangesNicholasModule())
            install(ExampleKafkaConsumerModule())
            install(ExampleKafkaProducerModule())
            install(DataModule())
            install(ExampleMovieModule())
        }

        binder.configureExternalModules()
    }

    open fun Binder.configureExternalModules() {
        install(AsyncCoroutineModule())
        install(KafkaProducerModule(KafkaProducerModule.ProducerMode.BASIC))
        // TASK: Use your own external client SDK modules here.
        install(ElaraModule())
    }
}
