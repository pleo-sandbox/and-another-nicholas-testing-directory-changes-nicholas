package io.pleo.andanothernicholastestingdirectorychangesnicholas.functest.base

import io.mockk.mockk
import io.pleo.andanothernicholastestingdirectorychangesnicholas.api.AndAnotherNicholasTestingDirectoryChangesNicholasApi
import io.pleo.andanothernicholastestingdirectorychangesnicholas.app.AndAnotherNicholasTestingDirectoryChangesNicholas
import io.pleo.andanothernicholastestingdirectorychangesnicholas.functest.mocks.ElaraMock
import io.pleo.andanothernicholastestingdirectorychangesnicholas.infrastructure.ApiClient
import io.pleo.config.modules.ConfigModule
import io.pleo.vanguard.framework.Vanguard
import io.pleo.vanguard.framework.VanguardTest
import io.pleo.vanguard.framework.extension.ExtensionDatabase
import io.pleo.vanguard.framework.extension.ExtensionKafka
import java.time.Duration
import okhttp3.logging.HttpLoggingInterceptor
import org.awaitility.Awaitility
import org.awaitility.core.ThrowingRunnable
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.parallel.Execution
import org.junit.jupiter.api.parallel.ExecutionMode
import io.pleo.elara.api.DefaultApi as ElaraApi

const val KAFKA_LATENCY = 8L

fun awaitUntil(assertion: ThrowingRunnable) {
    Awaitility
        .await()
        .atMost(Duration.ofSeconds(KAFKA_LATENCY))
        .untilAsserted(assertion)
}

@Execution(ExecutionMode.SAME_THREAD)
@ExtendWith(ExtensionDatabase::class)
@ExtendWith(ExtensionKafka::class)
abstract class AndAnotherNicholasTestingDirectoryChangesNicholasVanguardTest : VanguardTest<AndAnotherNicholasTestingDirectoryChangesNicholas>() {
    private val elaraClient = mockk<ElaraApi>()

    // This is how you mock your services, in this case a 3rd party client
    protected val elaraMock = ElaraMock(elaraClient)

    protected val andAnotherNicholasTestingDirectoryChangesNicholasClient by lazy {
        AndAnotherNicholasTestingDirectoryChangesNicholasApi("http://localhost:${server.localPort}/rest")
    }

    override val kafkaConsumerGroupTopicPropNames =
        mapOf(
            "kafka.group_id" to
                listOf(
                    "kafka.consumer.example.topics",
                ),
        )

    override val application =
        Vanguard<AndAnotherNicholasTestingDirectoryChangesNicholas> {
            // This replaces the original service in the context with the mock instance taken from above
            it.bind<ElaraApi>().toInstance(elaraClient)

            this@AndAnotherNicholasTestingDirectoryChangesNicholasVanguardTest.extensionAuth?.bindMockedAuthClient(it)

            // Bind the Kafka producer
            this@AndAnotherNicholasTestingDirectoryChangesNicholasVanguardTest.extensionKafka?.bindKafkaProducer(it)
            // Bind the Kafka broker for the Kafka consumer
            this@AndAnotherNicholasTestingDirectoryChangesNicholasVanguardTest.extensionKafka?.bindKafkaListenerContextProvider(it)
        }

    init {
        println("DISABLE_EXTERNAL_CONFIGURATION internal")
        System.setProperty(ConfigModule.DISABLE_EXTERNAL_CONFIGURATION, "true")
        /* The generated client only supports a single type of authentication at the time;
         * pick *either* ...
         *
         * ```
         * * ApiClient.accessToken = userAuthorization
         * ```
         *
         * or
         *
         * ```
         * ApiClient.username = clientUsername
         * ApiClient.password = clientPassword
         * ```
         */
        enableClientLogging()
    }

    private fun enableClientLogging() {
        with(ApiClient.builder) {
            if (interceptors().none { it is HttpLoggingInterceptor }) {
                val interceptor = HttpLoggingInterceptor()
                interceptor.level = HttpLoggingInterceptor.Level.BASIC
                addInterceptor(interceptor)
            }
        }
    }
}
