package io.pleo.andanothernicholastestingdirectorychangesnicholas.core.kafka.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize

@JsonDeserialize
@JsonIgnoreProperties(ignoreUnknown = true)
data class ExampleKafkaModel(
    val id: String,
    @JsonProperty("eventType")
    val type: EventType,
    val message: String,
) {
    @Suppress("unused")
    enum class EventType(
        @Suppress("UNUSED_PARAMETER") code: String,
    ) {
        EXAMPLE("example"),
    }
}
