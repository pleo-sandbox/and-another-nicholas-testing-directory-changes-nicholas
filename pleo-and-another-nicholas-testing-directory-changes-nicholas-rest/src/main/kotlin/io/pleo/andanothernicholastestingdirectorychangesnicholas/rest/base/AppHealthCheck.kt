package io.pleo.andanothernicholastestingdirectorychangesnicholas.rest.base

import com.codahale.metrics.health.HealthCheck

/**
 * At least one HealthCheck instance must be registered in Jersey environment.healthChecks() to avoid
 * ugly warning in logs on start.
 *
 * Moons have to override check() method and do actual validation such as Database availability, connection to
 * the downstream services etc.
 * */
class AppHealthCheck : HealthCheck() {
    override fun check(): Result = Result.healthy()
}
