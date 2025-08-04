package io.pleo.andanothernicholastestingdirectorychangesnicholas.app.modules

import io.pleo.andanothernicholastestingdirectorychangesnicholas.core.services.ExampleMovieService
import io.pleo.andanothernicholastestingdirectorychangesnicholas.data.dal.ExampleMovieDal
import io.pleo.andanothernicholastestingdirectorychangesnicholas.rest.resources.v1.ExampleMovieResource
import io.pleo.app.modules.KotlinAbstractModule

class ExampleMovieModule : KotlinAbstractModule() {
    override fun configure() {
        bind<ExampleMovieDal>().asSingleton()
        bind<ExampleMovieService>().asSingleton()
        bind<ExampleMovieResource>().asSingleton()
    }
}
