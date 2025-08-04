package io.pleo.andanothernicholastestingdirectorychangesnicholas.data.dal

import com.google.common.truth.Truth.assertThat
import io.pleo.andanothernicholastestingdirectorychangesnicholas.data.models.ExampleMovie
import io.pleo.andanothernicholastestingdirectorychangesnicholas.data.models.ExampleMovieRecord
import io.pleo.dal.test.ExposedDalTest
import java.util.regex.Pattern
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.parallel.Execution
import org.junit.jupiter.api.parallel.ExecutionMode

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Execution(ExecutionMode.SAME_THREAD)
class ExampleMovieModelDalTest : ExposedDalTest() {
    private val dal: ExampleMovieDal by lazy {
        ExampleMovieDal(staticDbContext)
    }

    @Test
    fun `inserting a movie returns its id`() {
        // given
        val movie = ExampleMovie("The Shawshank Redemption", 1994, "Frank Darabont")

        // when
        val insertedMovieId = dal.insert(movie)

        // then
        assertThat(Pattern.matches("([a-f0-9]{8}(-[a-f0-9]{4}){4}[a-f0-9]{8})", insertedMovieId.toString())).isTrue()
    }

    @Test
    fun `select an existing movie returns the movie`() {
        // given
        val expectedMovie = ExampleMovie("The Shawshank Redemption", 1994, "Frank Darabont")
        dal.insert(expectedMovie)

        // when
        val selectedMovie = dal.findMovieByName(expectedMovie.name)

        // then
        assertThat(selectedMovie?.name).isEqualTo(expectedMovie.name)
        assertThat(selectedMovie?.year).isEqualTo(expectedMovie.year)
        assertThat(selectedMovie?.director).isEqualTo(expectedMovie.director)
    }

    @Test
    fun `select a non-existing movie returns nothing`() {
        // given
        val movieName = "The Shawshank Redemption"

        // when
        val selectedMovie = dal.findMovieByName(movieName)

        // then
        assertThat(selectedMovie).isNull()
    }

    @Test
    fun `find all movies on a given year returns a list of movies`() {
        // given
        dal.insert(ExampleMovie("Pulp Fiction", 1994, "Quentin Tarantino"))
        dal.insert(ExampleMovie("Forrest Gump", 1994, "Robert Zemeckis"))
        dal.insert(ExampleMovie("The Godfather", 1972, "Francis Ford Coppola"))

        // when
        val movies1994 = dal.findMoviesByYear(1994)
        val movies1972 = dal.findMoviesByYear(1972)

        // then
        assertThat(movies1994.size).isEqualTo(2)
        assertThat(movies1972.size).isEqualTo(1)
    }

    @Test
    fun `update director in a movie persists the change as expected`() {
        // given
        val resourceId = dal.insert(ExampleMovie("Spartacus", 1960, "Anthony Mann"))

        // when
        dal.update(ExampleMovieRecord(resourceId, "Spartacus", 1960, "Stanley Kubrick"))

        // then
        val movie = dal.findMovieByName("Spartacus")
        assertThat(movie?.director).isEqualTo("Stanley Kubrick")
    }
}
