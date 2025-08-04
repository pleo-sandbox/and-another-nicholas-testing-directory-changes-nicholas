package io.pleo.andanothernicholastestingdirectorychangesnicholas.rest.models.v1

import io.swagger.v3.oas.annotations.media.Schema
import java.util.UUID

@Schema(
    description = "A movie record object",
    example = """
        { 
            "id": "acdcdaed-6c4d-41e4-815d-af6170cd5fa8",
            "name": "Die Hard",
            "year": 1988,
            "director": "John McTiernan"
        }
        """,
)
data class ExampleMovieRecordDto(
    @Schema(description = "The unique identifier of the movie", example = "acdcdaed-6c4d-41e4-815d-af6170cd5fa8")
    val id: UUID,
    @Schema(description = "The name of the movie", example = "Die Hard")
    val name: String,
    @Schema(description = "The release year of the movie", example = "1988")
    val year: Int,
    @Schema(description = "The director of the movie", example = "John McTiernan")
    val director: String,
)
