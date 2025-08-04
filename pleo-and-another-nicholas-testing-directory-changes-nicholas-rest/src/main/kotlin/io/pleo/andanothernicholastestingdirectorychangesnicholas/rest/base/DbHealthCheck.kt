package io.pleo.andanothernicholastestingdirectorychangesnicholas.rest.base

import com.codahale.metrics.health.HealthCheck
import io.pleo.andanothernicholastestingdirectorychangesnicholas.data.dal.HealthDal
import jakarta.inject.Inject

class DbHealthCheck
    @Inject
    constructor(
        private val healthDal: HealthDal,
    ) : HealthCheck() {
        override fun check(): Result =
            runCatching {
                healthDal.check()
                Result.healthy()
            }.let {
                when {
                    it.isSuccess -> it.getOrDefault(Result.unhealthy("Database health-check failed"))
                    else -> Result.unhealthy("Database health-check failed")
                }
            }
    }
