package io.pleo.andanothernicholastestingdirectorychangesnicholas.kafka.models

import io.pleo.andanothernicholastestingdirectorychangesnicholas.kafka.models.base.EntityType
import io.pleo.andanothernicholastestingdirectorychangesnicholas.kafka.models.base.EventName
import io.pleo.andanothernicholastestingdirectorychangesnicholas.kafka.models.base.EventType
import io.pleo.andanothernicholastestingdirectorychangesnicholas.kafka.models.base.KafkaEvent

data class ExampleEvent(
    override val id: String,
    override val eventType: EventType = EventType.EXAMPLE,
    override val message: String,
) : KafkaEvent {
    override val entityType: EntityType = EntityType.EXAMPLE

    override fun name(): EventName = EventName.EXAMPLE
}
