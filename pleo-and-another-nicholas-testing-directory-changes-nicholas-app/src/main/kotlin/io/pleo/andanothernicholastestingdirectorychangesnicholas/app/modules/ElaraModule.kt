package io.pleo.andanothernicholastestingdirectorychangesnicholas.app.modules

import com.google.inject.Provides
import io.pleo.app.modules.KotlinAbstractModule
import io.pleo.elara.api.DefaultApi
import io.pleo.prop.core.Default
import io.pleo.prop.core.Prop
import jakarta.inject.Named
import jakarta.inject.Singleton
import io.pleo.elara.infrastructure.ApiClient as ElaraClient

// ⚠️ INFO ⚠️

// You don't really need this file and can be safely removed!
// The point of it is to illustrate example of use cases you may come across
// with ❤️, DevX

class ElaraModule : KotlinAbstractModule() {
    @Provides
    @Singleton
    fun elaraClient(
        @Default("<default needed to boot a service, should be removed once actual values are on AWS SecretsManager>")
        @Named("service.username") username: Prop<String>,
        @Default("<default needed to boot a service, should be removed once actual values are on AWS SecretsManager>")
        @Named("service.password") password: Prop<String>,
        @Named("elara.endpoint") endpoint: Prop<String>,
    ): DefaultApi {
        ElaraClient.username = username.get()
        ElaraClient.password = password.get()
        return DefaultApi(endpoint.get())
    }
}
