package io.pleo.andanothernicholastestingdirectorychangesnicholas.app.modules

import io.pleo.andanothernicholastestingdirectorychangesnicholas.rest.base.AppHealthCheck
import io.pleo.andanothernicholastestingdirectorychangesnicholas.rest.base.DbHealthCheck
import io.pleo.andanothernicholastestingdirectorychangesnicholas.rest.base.HealthResource
import io.pleo.app.modules.KotlinAbstractModule

class HealthModule : KotlinAbstractModule() {
    override fun configure() {
        bind<AppHealthCheck>().asSingleton()
        bind<HealthResource>().asSingleton()
        bind<DbHealthCheck>().asSingleton()
    }
}
