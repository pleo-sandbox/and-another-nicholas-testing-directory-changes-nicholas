package io.pleo.andanothernicholastestingdirectorychangesnicholas.data.dal

import jakarta.inject.Inject
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction

class HealthDal
    @Inject
    constructor(
        private val db: Database,
    ) {
        fun check() {
            transaction(db) {
                exec("SELECT 1")
            }
        }
    }
