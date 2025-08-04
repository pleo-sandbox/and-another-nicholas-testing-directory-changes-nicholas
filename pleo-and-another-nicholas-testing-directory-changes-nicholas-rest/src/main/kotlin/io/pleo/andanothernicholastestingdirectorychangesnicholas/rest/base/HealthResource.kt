package io.pleo.andanothernicholastestingdirectorychangesnicholas.rest.base

import com.codahale.metrics.health.HealthCheck
import io.pleo.rest.models.DataResponse
import io.pleo.rest.models.ExceptionInfo
import io.pleo.utils.MediaTypes
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import jakarta.annotation.security.PermitAll
import jakarta.inject.Inject
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import java.time.Instant

/**
 * Resource is used by AWS/k8s infrastructure to check /rest/health HTTP endpoint availability.
 * If moon replies with HTTP 200 with any data in the response body it is considered as healthy.
 * */
@PermitAll
@Path("/health")
@Produces(MediaTypes.APPLICATION_JSON_UTF_8)
class HealthResource
    @Inject
    constructor(
        private val appHealthCheck: AppHealthCheck,
        private val dbHealthCheck: DbHealthCheck,
    ) : ApiDefinition {
        @GET
        @Operation(
            summary = "Returns the readiness health of the service",
            description =
                "Returns the readiness health of the service." +
                    "It checks the app health and the database connection health",
            responses = [
                ApiResponse(
                    responseCode = "200",
                    description = "Service readiness check successful",
                    useReturnTypeSchema = true,
                    content = [
                        Content(
                            mediaType = "application/json",
                            examples = [
                                ExampleObject(
                                    value = """
                                        { 
                                            "data": {
                                                "healthy": true,
                                                "message": "Readiness health-check passed",
                                                "time": "2024-07-22T15:36:49.826044985Z"
                                            }
                                        }
                                        """,
                                ),
                            ],
                        ),
                    ],
                ),
            ],
        )
        fun checkReadiness(): DataResponse<Result> {
            val firstFailure = findFirstFailureOrNull(listOf(appHealthCheck, dbHealthCheck))
            return if (firstFailure == null) {
                DataResponse(
                    Result(
                        healthy = true,
                        message = "Readiness health-check passed",
                    ),
                )
            } else {
                DataResponse(
                    Result(
                        healthy = false,
                        message = "At least one health-check failed: ${firstFailure.message}",
                        // We use simpleName as non-nullable because no anonymous classes are expected
                        error = ExceptionInfo(firstFailure.error::class.simpleName!!, firstFailure.error.message),
                        details = firstFailure.details,
                    ),
                )
            }
        }

        @GET
        @Path("/liveness")
        @Operation(
            summary = "Returns the liveness health of the service",
            description = "Returns the liveness health of the service. It checks the API is responsive",
            responses = [
                ApiResponse(
                    responseCode = "200",
                    description = "Service liveness check successful",
                    useReturnTypeSchema = true,
                    content = [
                        Content(
                            mediaType = "application/json",
                            examples = [
                                ExampleObject(
                                    value = """
                                    { 
                                        "data": { 
                                            "healthy": true,
                                            "message": "Liveness health-check passed",
                                            "time": "2024-07-22T15:36:49.826044985Z"
                                        }
                                    }
                                    """,
                                ),
                            ],
                        ),
                    ],
                ),
            ],
        )
        fun checkLiveness(): DataResponse<Result> =
            DataResponse(
                Result(healthy = true, message = "Liveness health-check passed"),
            )

        private fun findFirstFailureOrNull(healthChecks: List<HealthCheck>): HealthCheck.Result? {
            var firstFailure: HealthCheck.Result? = null
            for (check in healthChecks) {
                val result = check.execute()
                if (!result.isHealthy) {
                    firstFailure = result
                    break
                }
            }
            return firstFailure
        }

        @Schema(
            description = "Health Check Result",
            example = """
        { 
            "healthy": true,
            "message": "Readiness health-check passed",
            "time": "2024-07-22T15:36:49.826044985Z"  
        }
        """,
        )
        data class Result(
            @Schema(description = "Status of the service", example = "true")
            val healthy: Boolean,
            @Schema(description = "Status description of the service", example = "Liveness health-check passed")
            val message: String?,
            @Schema(description = "Status details of the service", example = """{ "key": {} }""")
            val details: Map<String?, Any?>? = null,
            @Schema(
                description = "Error details",
                example = """{ "type": "INTERNAL_SERVER_ERROR", "message": "Oops, something went wrong..." }""",
            )
            val error: ExceptionInfo? = null,
            @Schema(description = "Timestamp of the response creation", example = "2024-07-22T15:36:49.826044985Z")
            val time: Instant = Instant.now(),
        )
    }
