package io.pleo.andanothernicholastestingdirectorychangesnicholas.app.modules

import io.pleo.andanothernicholastestingdirectorychangesnicholas.core.services.BankHolidaysService
import io.pleo.andanothernicholastestingdirectorychangesnicholas.rest.resources.v1.BankHolidaysResource
import io.pleo.app.modules.KotlinAbstractModule

class V1Module : KotlinAbstractModule() {
    override fun configure() {
        // TASK: Define and use your own external services and resources here.
        bind<BankHolidaysService>().asSingleton()
        bind<BankHolidaysResource>().asSingleton()
    }
}
