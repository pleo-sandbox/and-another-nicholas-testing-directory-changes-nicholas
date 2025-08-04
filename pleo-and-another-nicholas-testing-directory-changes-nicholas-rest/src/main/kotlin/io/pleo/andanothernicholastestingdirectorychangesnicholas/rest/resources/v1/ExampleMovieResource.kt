package io.pleo.andanothernicholastestingdirectorychangesnicholas.rest.resources.v1

import io.pleo.andanothernicholastestingdirectorychangesnicholas.core.services.ExampleMovieService
import io.pleo.andanothernicholastestingdirectorychangesnicholas.rest.base.ApiDefinition
import io.pleo.andanothernicholastestingdirectorychangesnicholas.rest.models.v1.ExampleMovieDto
import io.pleo.andanothernicholastestingdirectorychangesnicholas.rest.models.v1.ExampleMovieRecordDto
import io.pleo.rest.models.DataResponse
import io.pleo.utils.MediaTypes
import io.pleo.utils.exceptions.InvalidRequestException
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.parameters.RequestBody
import io.swagger.v3.oas.annotations.responses.ApiResponse
import jakarta.annotation.security.PermitAll
import jakarta.inject.Inject
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.GET
import jakarta.ws.rs.NotFoundException
import jakarta.ws.rs.POST
import jakarta.ws.rs.PUT
import jakarta.ws.rs.Path
import jakarta.ws.rs.PathParam
import jakarta.ws.rs.Produces
import jakarta.ws.rs.QueryParam
import java.util.UUID
import io.pleo.andanothernicholastestingdirectorychangesnicholas.model.ExampleMovieModel as ExampleMovie
import io.pleo.andanothernicholastestingdirectorychangesnicholas.model.ExampleMovieRecordModel as ExampleMovieRecord

@PermitAll
@Path("/$API_VERSION/movies")
@Consumes(MediaTypes.APPLICATION_JSON_UTF_8)
@Produces(MediaTypes.APPLICATION_JSON_UTF_8)
class ExampleMovieResource
    @Inject
    constructor(
        private val movieService: ExampleMovieService,
    ) : ApiDefinition {
        @GET
        @Path("/{id}")
        @Operation(
            summary = "Fetch movie by id",
            description = "Fetches a movie by the id assigned at creation time",
            responses = [
                ApiResponse(
                    responseCode = "200",
                    description = "Movie fetch successful",
                    useReturnTypeSchema = true,
                    content = [
                        Content(
                            mediaType = "application/json",
                            examples = [
                                ExampleObject(
                                    value = """
                                        { 
                                            "data": {
                                                "id": "acdcdaed-6c4d-41e4-815d-af6170cd5fa8",
                                                "name": "Die Hard",
                                                "year": 1988,
                                                "director": "John McTiernan"
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
        fun getMovieById(
            @Parameter(
                description = "The identifier of the movie",
                required = true,
                example = "acdcdaed-6c4d-41e4-815d-af6170cd5fa8",
            )
            @PathParam("id")
            id: UUID,
        ): DataResponse<ExampleMovieRecordDto> {
            val movie = movieService.getMovieById(id) ?: throw NotFoundException("No movie found")

            return DataResponse(movie.toDto())
        }

        @GET
        @Operation(
            summary = "Find movies by their properties",
            description = "Finds movies by their name or release year",
            responses = [
                ApiResponse(
                    responseCode = "200",
                    description = "Matching Movies fetch successful",
                    useReturnTypeSchema = true,
                    content = [
                        Content(
                            mediaType = "application/json",
                            examples = [
                                ExampleObject(
                                    value = """
                                        { 
                                            "data": [
                                                {
                                                    "id": "acdcdaed-6c4d-41e4-815d-af6170cd5fa8",
                                                    "name": "Die Hard",
                                                    "year": 1988,
                                                    "director": "John McTiernan"
                                                }
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
        fun findMovies(
            @Parameter(description = "The name of the movie", required = false, example = "Die Hard")
            @QueryParam("movie_name")
            movieName: String?,
            @Parameter(description = "The year the movie was published", required = false, example = "1988")
            @QueryParam("movie_year")
            movieYear: Int?,
        ): DataResponse<List<ExampleMovieRecordDto>> =
            if (movieName != null && movieYear == null) {
                DataResponse(listOf(movieService.findMovieByName(movieName)?.toDto() ?: throw NotFoundException()))
            } else if (movieYear != null && movieName == null) {
                DataResponse(movieService.findMoviesByYear(movieYear).map { it.toDto() })
            } else {
                throw InvalidRequestException("Invalid query parameters. Expected either movie_name or movie_year")
            }

        @POST
        @Operation(
            summary = "Add a movie",
            description = "Adds a new movie to the service",
            responses = [
                ApiResponse(
                    responseCode = "200",
                    description = "Movie create successful",
                    useReturnTypeSchema = true,
                    content = [
                        Content(
                            mediaType = "application/json",
                            examples = [
                                ExampleObject(
                                    value = """
                                        { 
                                            "data": {
                                                "id": "acdcdaed-6c4d-41e4-815d-af6170cd5fa8",
                                                "name": "Die Hard",
                                                "year": 1988,
                                                "director": "John McTiernan"
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
        fun addMovie(
            @RequestBody(description = "object representation of a movie", required = true) request: ExampleMovieDto,
        ): DataResponse<ExampleMovieRecordDto> {
            val movieId = movieService.create(request.toCoreModel())
            return DataResponse(movieService.getMovieById(movieId)!!.toDto())
        }

        @PUT
        @Operation(
            summary = "Update the details of a movie",
            description = "Updates the details of a movie. The movie is identified by its ID",
            responses = [ ApiResponse(description = "Movie updated successfully", responseCode = "200") ],
        )
        fun updateMovie(
            @RequestBody(
                description = "object representation of a movie",
                required = true,
            ) request: ExampleMovieRecordDto,
        ) {
            movieService.updateMovie(request.toCoreModel())
        }
    }

fun ExampleMovieDto.toCoreModel() = ExampleMovie(this.name, this.year, this.director)

fun ExampleMovieRecord.toDto() = ExampleMovieRecordDto(this.id, this.name, this.year, this.director)

fun ExampleMovieRecordDto.toCoreModel() = ExampleMovieRecord(this.id, this.name, this.year, this.director)
