@file:Suppress("FunctionNaming")

package io.pleo.andanothernicholastestingdirectorychangesnicholas.functest.tests

import com.google.common.truth.Truth.assertThat
import io.pleo.andanothernicholastestingdirectorychangesnicholas.functest.base.AndAnotherNicholasTestingDirectoryChangesNicholasVanguardTest
import io.pleo.andanothernicholastestingdirectorychangesnicholas.models.ExampleMovieDto
import io.pleo.andanothernicholastestingdirectorychangesnicholas.models.ExampleMovieRecordDto
import org.junit.jupiter.api.Test

const val YEAR_1994 = 1994
const val YEAR_1988 = 1988
const val YEAR_1977 = 1977

class ExampleMovieModelResourceTest : AndAnotherNicholasTestingDirectoryChangesNicholasVanguardTest() {
    @Test
    fun `should return movie by name`() {
        andAnotherNicholasTestingDirectoryChangesNicholasClient.addMovie(
            ExampleMovieDto(
                director = "Quentin Tarantino",
                name = "Pulp Fiction",
                year = YEAR_1994,
            ),
        )

        val selectedMovie = andAnotherNicholasTestingDirectoryChangesNicholasClient.findMovies(movieName = "Pulp Fiction").data[0]

        assertThat(selectedMovie.id).isNotNull()
        assertThat(selectedMovie.name).isEqualTo("Pulp Fiction")
        assertThat(selectedMovie.year).isEqualTo(YEAR_1994)
        assertThat(selectedMovie.director).isEqualTo("Quentin Tarantino")
    }

    @Test
    fun `should return list of movies by year`() {
        andAnotherNicholasTestingDirectoryChangesNicholasClient.addMovie(
            ExampleMovieDto(
                name = "My Neighbour Totoro",
                year = YEAR_1988,
                director = "Hayao Miyazaki",
            ),
        )
        andAnotherNicholasTestingDirectoryChangesNicholasClient.addMovie(
            ExampleMovieDto(
                name = "Grave of the Fireflies",
                year = YEAR_1988,
                director = "Isao Takahata",
            ),
        )
        andAnotherNicholasTestingDirectoryChangesNicholasClient.addMovie(
            ExampleMovieDto(
                name = "Star Wars: Episode IV - A New Hope",
                year = YEAR_1977,
                director = "George Lucas",
            ),
        )

        val movies1988 = andAnotherNicholasTestingDirectoryChangesNicholasClient.findMovies(movieYear = YEAR_1988).data
        val movies1977 = andAnotherNicholasTestingDirectoryChangesNicholasClient.findMovies(movieYear = YEAR_1977).data

        assertThat(movies1988.size).isEqualTo(2)
        assertThat(movies1977.size).isEqualTo(1)
    }

    @Test
    fun `should update the director of a movie`() {
        val faultyJawsMovie =
            andAnotherNicholasTestingDirectoryChangesNicholasClient
                .addMovie(
                    ExampleMovieDto(name = "Jaws", year = 1975, director = "Dick Richards"),
                ).data

        val correctedJawsMovie =
            ExampleMovieRecordDto(
                id = faultyJawsMovie.id,
                name = faultyJawsMovie.name,
                year = faultyJawsMovie.year,
                director = "Steven Spielberg",
            )

        andAnotherNicholasTestingDirectoryChangesNicholasClient.updateMovie(correctedJawsMovie)

        val movie = andAnotherNicholasTestingDirectoryChangesNicholasClient.findMovies(movieName = "Jaws").data[0]
        assertThat(movie.director).isEqualTo("Steven Spielberg")
    }
}
