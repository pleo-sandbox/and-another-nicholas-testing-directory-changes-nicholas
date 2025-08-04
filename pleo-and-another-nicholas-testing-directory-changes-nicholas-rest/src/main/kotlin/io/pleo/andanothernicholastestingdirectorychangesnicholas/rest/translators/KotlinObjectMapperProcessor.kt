package io.pleo.andanothernicholastestingdirectorychangesnicholas.rest.translators

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.cfg.MapperConfig
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.swagger.v3.oas.integration.api.ObjectMapperProcessor

/*
 * WARNING *
 This class is used to configure the ObjectMapper used by the OpenAPI Generator.
 It's not used by the application itself or by clients of the SDK.
 See the mustache template in resources for the client mapper.
 */
@Suppress("unused")
class KotlinObjectMapperProcessor : ObjectMapperProcessor {
    override fun processJsonObjectMapper(mapper: ObjectMapper) {
        mapper.registerKotlinModule()
        setPropertyNamingStrategy(mapper)
    }

    override fun processOutputJsonObjectMapper(mapper: ObjectMapper) {
        mapper.registerKotlinModule()
        setPropertyNamingStrategy(mapper)
    }

    private fun setPropertyNamingStrategy(mapper: ObjectMapper) {
        // By default, Jackson renames primitive boolean field that start with 'is' by removing 'is'.
        // So if you have a field, isSuccessful, it will be automatically renamed to "successful".
        // This causes unexpected issues with the OpenAPI Generator. This is here to prevent that.
        mapper.propertyNamingStrategy =
            object : PropertyNamingStrategy() {
                override fun nameForGetterMethod(
                    config: MapperConfig<*>?,
                    method: AnnotatedMethod?,
                    defaultName: String?,
                ): String? =
                    if (method != null &&
                        method.rawReturnType === Boolean::class.java &&
                        method.name.startsWith("is")
                    ) {
                        method.name
                    } else {
                        super.nameForGetterMethod(config, method, defaultName)
                    }
            }
    }
}
