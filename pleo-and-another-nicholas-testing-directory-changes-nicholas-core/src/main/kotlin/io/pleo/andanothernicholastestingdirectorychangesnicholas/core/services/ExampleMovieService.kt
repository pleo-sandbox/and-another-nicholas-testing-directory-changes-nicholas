package io.pleo.andanothernicholastestingdirectorychangesnicholas.core.services

import io.pleo.andanothernicholastestingdirectorychangesnicholas.data.dal.ExampleMovieDal
import io.pleo.andanothernicholastestingdirectorychangesnicholas.data.models.ExampleMovie
import io.pleo.andanothernicholastestingdirectorychangesnicholas.data.models.ExampleMovieRecord
import io.pleo.andanothernicholastestingdirectorychangesnicholas.model.ExampleMovieModel
import io.pleo.andanothernicholastestingdirectorychangesnicholas.model.ExampleMovieRecordModel
import jakarta.inject.Inject
import java.util.UUID

class ExampleMovieService
    @Inject
    constructor(
        private val movieDal: ExampleMovieDal,
    ) {
        fun getMovieById(id: UUID): ExampleMovieRecordModel? = movieDal.getMovieById(id)?.toDto()

        fun findMovieByName(name: String): ExampleMovieRecordModel? = movieDal.findMovieByName(name)?.toDto()

        fun findMoviesByYear(year: Int): List<ExampleMovieRecordModel> =
            movieDal
                .findMoviesByYear(year)
                .map { it.toDto() }

        fun create(movie: ExampleMovieModel): UUID = movieDal.insert(movie.toDataModel())

        fun updateMovie(movie: ExampleMovieRecordModel): Int = movieDal.update(movie.toDataModel())
    }

fun ExampleMovieRecord.toDto(): ExampleMovieRecordModel =
    ExampleMovieRecordModel(
        id = this.id,
        name = this.name,
        year = this.year,
        director = this.director,
    )

fun ExampleMovieRecordModel.toDataModel(): ExampleMovieRecord =
    ExampleMovieRecord(
        id = this.id,
        name = this.name,
        year = this.year,
        director = this.director,
    )

fun ExampleMovieModel.toDataModel(): ExampleMovie =
    ExampleMovie(
        name = this.name,
        year = this.year,
        director = this.director,
    )
