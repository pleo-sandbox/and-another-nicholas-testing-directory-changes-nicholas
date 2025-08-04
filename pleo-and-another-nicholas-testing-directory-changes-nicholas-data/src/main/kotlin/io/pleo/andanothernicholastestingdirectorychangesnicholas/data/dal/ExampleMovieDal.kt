package io.pleo.andanothernicholastestingdirectorychangesnicholas.data.dal

import io.pleo.andanothernicholastestingdirectorychangesnicholas.data.models.ExampleMovie
import io.pleo.andanothernicholastestingdirectorychangesnicholas.data.models.ExampleMovieRecord
import io.pleo.andanothernicholastestingdirectorychangesnicholas.data.tables.ExampleMovieTable
import jakarta.inject.Inject
import java.util.UUID
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class ExampleMovieDal
    @Inject
    constructor(
        private val db: Database,
    ) {
        fun getMovieById(id: UUID): ExampleMovieRecord? {
            val query = ExampleMovieTable.selectAll().where { ExampleMovieTable.id.eq(id) }

            return transaction(db) {
                query
                    .limit(1)
                    .map {
                        ExampleMovieTable.toModel(it)
                    }.firstOrNull()
            }
        }

        fun findMovieByName(name: String): ExampleMovieRecord? {
            val query = ExampleMovieTable.selectAll().where { ExampleMovieTable.name.eq(name) }

            return transaction(db) {
                query
                    .limit(1)
                    .map {
                        ExampleMovieTable.toModel(it)
                    }.firstOrNull()
            }
        }

        fun findMoviesByYear(year: Int): List<ExampleMovieRecord> =
            transaction(db) {
                ExampleMovieTable.selectAll().where { ExampleMovieTable.year.eq(year) }.map {
                    ExampleMovieTable.toModel(it)
                }
            }

        fun insert(movie: ExampleMovie): UUID =
            transaction(db) {
                ExampleMovieTable.insert {
                    fromModel(it, movie)
                } get ExampleMovieTable.id
            }

        fun update(movie: ExampleMovieRecord): Int =
            transaction(db) {
                ExampleMovieTable.update({ ExampleMovieTable.id.eq(movie.id) }) {
                    it[name] = movie.name
                    it[year] = movie.year
                    it[director] = movie.director
                }
            }
    }
