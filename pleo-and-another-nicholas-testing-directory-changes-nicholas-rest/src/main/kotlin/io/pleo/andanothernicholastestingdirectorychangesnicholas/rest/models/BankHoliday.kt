package io.pleo.andanothernicholastestingdirectorychangesnicholas.rest.models

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDate

@Schema(description = "A Bank holiday", example = """{ "date": "2024-12-25" }""")
data class BankHoliday(
    @Schema(description = "Date of the bank holiday", example = "2024-12-25")
    val date: LocalDate,
)
