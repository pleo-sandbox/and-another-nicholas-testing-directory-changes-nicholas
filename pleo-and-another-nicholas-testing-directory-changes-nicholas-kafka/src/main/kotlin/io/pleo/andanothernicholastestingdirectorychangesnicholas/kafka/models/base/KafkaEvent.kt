package io.pleo.andanothernicholastestingdirectorychangesnicholas.kafka.models.base

import com.fasterxml.jackson.annotation.JsonValue

interface KafkaEvent {
    val id: String
    val eventType: Any
    val entityType: EntityType
    val message: String

    fun name(): EventName
}

enum class EventType {
    EXAMPLE,
}

enum class EntityType(
    @JsonValue
    val value: String,
) {
    EXAMPLE("example"),
}

enum class EventName(
    @JsonValue
    val value: String,
) {
    EXAMPLE("example"),
}
