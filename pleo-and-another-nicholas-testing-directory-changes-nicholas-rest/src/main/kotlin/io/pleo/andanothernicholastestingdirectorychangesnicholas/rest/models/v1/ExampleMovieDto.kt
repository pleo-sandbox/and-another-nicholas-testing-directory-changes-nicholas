package io.pleo.andanothernicholastestingdirectorychangesnicholas.rest.models.v1

import io.swagger.v3.oas.annotations.media.Schema

@Schema(
    description = "A movie data object",
    example = """{ "name": "Die Hard", "year": 1988, "director": "John McTiernan"}""",
)
data class ExampleMovieDto(
    @Schema(description = "The name of the movie", example = "Die Hard")
    val name: String,
    @Schema(description = "The release year of the movie", example = "1988")
    val year: Int,
    @Schema(description = "The director of the movie", example = "John McTiernan")
    val director: String,
)
