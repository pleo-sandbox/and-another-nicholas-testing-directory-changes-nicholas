package io.pleo.andanothernicholastestingdirectorychangesnicholas.rest.resources.v1

import com.neovisionaries.i18n.CurrencyCode
import io.pleo.andanothernicholastestingdirectorychangesnicholas.core.services.BankHolidaysService
import io.pleo.andanothernicholastestingdirectorychangesnicholas.rest.base.ApiDefinition
import io.pleo.andanothernicholastestingdirectorychangesnicholas.rest.models.BankHoliday
import io.pleo.authentication.models.Permission
import io.pleo.features.Feature
import io.pleo.features.Flow
import io.pleo.rest.annotations.AllowInternalClient
import io.pleo.rest.annotations.AllowUser
import io.pleo.rest.annotations.AllowsWithAnyOf
import io.pleo.rest.models.DataResponse
import io.pleo.tracing.annotations.InitiatePleoFlow
import io.pleo.utils.MediaTypes
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import jakarta.inject.Inject
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.QueryParam

// ⚠️ INFO ⚠️

// You don't really need this file and can be safely removed!
// The point of it is to illustrate example of use cases you may come across
// with ❤️, DevX

@Path("/v1/holidays")
@Produces(MediaTypes.APPLICATION_JSON_UTF_8)
class BankHolidaysResource
    @Inject
    constructor(
        private val bankHolidaysService: BankHolidaysService,
    ) : ApiDefinition {
        @GET
        @Operation(
            summary = "Fetch bank holidays by given currency code",
            description = "Fetches bank holidays by a given currency code",
            responses = [
                ApiResponse(
                    responseCode = "200",
                    description = "Bank holidays fetch successful",
                    useReturnTypeSchema = true,
                    content = [
                        Content(
                            mediaType = "application/json",
                            schema = Schema(),
                            examples = [
                                ExampleObject(
                                    value = """
                                        { 
                                            "data": [
                                                { "date": "2024-01-01" },
                                                { "date": "2024-12-25" },
                                                { "date": "2024-12-26" }                                                
                                            ]
                                        }
                                        """,
                                ),
                            ],
                        ),
                    ],
                ),
            ],
        )
        // TODO: Change this to your own flow
        @InitiatePleoFlow(Feature.TEST, Flow.EMPTY_FLOW)
        @AllowsWithAnyOf(
            allowUsers = [
                AllowUser(
                    type = Permission.Type.OWNER,
                    resource = "admin",
                    resourceId = "and-another-nicholas-testing-directory-changes-nicholas",
                ),
            ],
            allowInternalClients = [
                AllowInternalClient(resource = "holidays", allResourceIds = true, action = "read"),
            ],
        )
        fun bankHolidaysFromElara(
            @Parameter(
                description = "The currency code used to derive the bank holidays",
                required = true,
                example = "EUR",
            )
            @QueryParam("currency_code")
            currencyCode: CurrencyCode,
        ): DataResponse<List<BankHoliday>> =
            DataResponse(
                bankHolidaysService
                    .findBankHolidays(currencyCode)
                    .map {
                        (BankHoliday(it.date))
                    },
            )
    }
