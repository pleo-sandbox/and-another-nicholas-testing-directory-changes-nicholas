package io.pleo.andanothernicholastestingdirectorychangesnicholas.data.tables

import io.pleo.andanothernicholastestingdirectorychangesnicholas.data.models.ExampleMovie
import io.pleo.andanothernicholastestingdirectorychangesnicholas.data.models.ExampleMovieRecord
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.statements.UpdateBuilder

object ExampleMovieTable : Table("movie") {
    val id = uuid("id").autoGenerate()
    val name = text("name")
    val year = integer("year")
    val director = text("director")
    override val primaryKey = PrimaryKey(id)

    fun toModel(row: ResultRow): ExampleMovieRecord =
        ExampleMovieRecord(
            id = row[id],
            name = row[name],
            year = row[year],
            director = row[director],
        )

    fun fromModel(
        it: UpdateBuilder<Number>,
        movie: ExampleMovie,
    ) {
        it[name] = movie.name
        it[year] = movie.year
        it[director] = movie.director
    }
}
