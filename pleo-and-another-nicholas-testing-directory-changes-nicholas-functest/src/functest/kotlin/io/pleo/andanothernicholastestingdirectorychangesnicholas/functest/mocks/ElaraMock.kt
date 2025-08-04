package io.pleo.andanothernicholastestingdirectorychangesnicholas.functest.mocks

import feign.FeignException
import feign.Request
import feign.RequestTemplate
import io.mockk.every
import io.pleo.elara.models.BankHolidayV1Model
import java.time.LocalDate

enum class TYPE {
    ANY,
    EMPTY,
    TIMEOUT,
}

class ElaraMock(
    private val elaraApi: io.pleo.elara.api.DefaultApi,
) {
    fun getHolidays(type: TYPE) {
        when (type) {
            TYPE.ANY -> every { elaraApi.holidays(any()) } returns listOf(createTestBankHoliday())
            TYPE.EMPTY -> every { elaraApi.holidays(any()) } returns emptyList()
            TYPE.TIMEOUT -> every { elaraApi.holidays(any()) } throws timeout()
        }
    }

    private fun createTestBankHoliday() =
        BankHolidayV1Model(
            date = LocalDate.of(YEAR, MONTH, DAY),
            holidayType = BankHolidayV1Model.HolidayType.NORMAL_HOLIDAY,
            description = "",
            specialHolidayInfo = null,
        )

    private fun timeout() =
        FeignException.GatewayTimeout(
            "Simulating error",
            Request.create(
                Request.HttpMethod.GET,
                "https://empty.one",
                emptyMap(),
                Request.Body.empty(),
                RequestTemplate(),
            ),
            null,
            emptyMap(),
        )

    companion object {
        const val YEAR = 2023
        const val MONTH = 5
        const val DAY = 29
    }
}
