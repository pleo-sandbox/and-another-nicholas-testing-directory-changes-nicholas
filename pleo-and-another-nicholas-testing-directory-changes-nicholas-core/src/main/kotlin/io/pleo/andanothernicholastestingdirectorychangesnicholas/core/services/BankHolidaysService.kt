package io.pleo.andanothernicholastestingdirectorychangesnicholas.core.services

import com.neovisionaries.i18n.CurrencyCode
import feign.FeignException
import io.pleo.elara.api.DefaultApi
import io.pleo.elara.models.BankHolidayV1Model
import io.pleo.logging.KotlinLogging
import io.pleo.utils.exceptions.PleoException
import jakarta.inject.Inject

private val logger = KotlinLogging.logger {}

// ⚠️ INFO ⚠️

// You don't really need this file and can be safely removed!
// The point of it is to illustrate example of use cases you may come across.
// - with ❤️, DevX

class BankHolidaysService
    @Inject
    constructor(
        private val elaraClient: DefaultApi,
    ) {
        fun findBankHolidays(currencyCode: CurrencyCode): List<BankHolidayV1Model> =
            try {
                elaraClient.holidays(DefaultApi.CurrencyCodeHolidays.valueOf(currencyCode.name))
            } catch (e: FeignException) {
                logger.error { "Unexpected error when fetching holidays from Elara moon" }
                throw PleoException(e)
            }
    }
