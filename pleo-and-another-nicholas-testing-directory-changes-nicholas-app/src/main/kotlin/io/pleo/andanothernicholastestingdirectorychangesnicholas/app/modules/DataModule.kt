package io.pleo.andanothernicholastestingdirectorychangesnicholas.app.modules

import com.google.inject.Provides
import com.google.inject.multibindings.Multibinder
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import com.zaxxer.hikari.metrics.micrometer.MicrometerMetricsTrackerFactory
import io.micrometer.prometheusmetrics.PrometheusMeterRegistry
import io.pleo.andanothernicholastestingdirectorychangesnicholas.data.dal.HealthDal
import io.pleo.app.modules.KotlinAbstractModule
import io.pleo.data.flyway.FlywayMigration
import io.pleo.data.flyway.GuiceMigrationResolver
import io.pleo.prop.commonsconfig.utils.CompositeConfigurationBuilder
import jakarta.inject.Singleton
import java.util.Properties
import javax.sql.DataSource
import org.flywaydb.core.Flyway
import org.flywaydb.database.postgresql.PostgreSQLConfigurationExtension
import org.jetbrains.exposed.sql.Database
import org.postgresql.ds.PGSimpleDataSource

private const val HIKARI_CONFIG_PREFIX = "hikari"
private const val FLYWAY_SCHEMA_VERSION_TABLE = "schema_version"

class DataModule : KotlinAbstractModule() {
    override fun configure() {
        bind<GuiceMigrationResolver>().asSingleton()
        Multibinder.newSetBinder(binder(), FlywayMigration::class.java)

        bindDataObjects()
    }

    fun bindDataObjects() {
        bind<HealthDal>().asSingleton()
    }

    @Provides
    @Singleton
    fun dataSource(
        configurationBuilder: CompositeConfigurationBuilder,
        prometheusMeterRegistry: PrometheusMeterRegistry,
    ): DataSource {
        val config = configurationBuilder.configuration
        val properties = Properties()

        for (key in config.getKeys("dataSource")) {
            properties[key] = config.getString(key)
        }

        // Can be used to set properties on the HikariConfig object
        for (key in config.getKeys(HIKARI_CONFIG_PREFIX)) {
            properties[key.substring(HIKARI_CONFIG_PREFIX.length + 1)] = config.getString(key)
        }

        val hikariConfig =
            HikariConfig(properties).apply {
                dataSourceClassName = PGSimpleDataSource::class.java.canonicalName
                metricsTrackerFactory = MicrometerMetricsTrackerFactory(prometheusMeterRegistry)
            }

        return HikariDataSource(hikariConfig)
    }

    @Provides
    @Singleton
    fun database(dataSource: DataSource): Database = Database.connect(dataSource)

    @Provides
    @Singleton
    fun flyway(
        dataSource: DataSource,
        migrationResolver: GuiceMigrationResolver,
    ): Flyway =
        Flyway
            .configure()
            .dataSource(dataSource)
            .resolvers(migrationResolver)
            .baselineVersion("1")
            .baselineOnMigrate(true)
            .validateOnMigrate(false)
            .table(FLYWAY_SCHEMA_VERSION_TABLE)
            .apply {
                // Flyway >= 9.1.2 hangs on concurrent index creation unless the following is set
                // (See https://flywaydb.org/documentation/configuration/parameters/postgresqlTransactionalLock)
                pluginRegister.getPlugin(PostgreSQLConfigurationExtension::class.java).isTransactionalLock = false
            }.load()
}
