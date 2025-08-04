@file:Suppress("UnstableApiUsage")

import com.adarshr.gradle.testlogger.theme.ThemeType

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.test.logger)
    alias(libs.plugins.pleo.gradle.plugin)
}

buildscript {
    dependencies {
        classpath(libs.pleo.openapi.lint.rules)
        classpath(libs.pleo.openapi.templates)
    }
}

allprojects {
    group = "pleo-io.and-another-nicholas-testing-directory-changes-nicholas"

    apply {
        plugin(
            rootProject.libs.plugins.kotlin.jvm
                .get()
                .pluginId,
        )
        plugin(
            rootProject.libs.plugins.test.logger
                .get()
                .pluginId,
        )
    }

    repositories {
        mavenCentral()
        mavenLocal()
    }

    dependencies {
        implementation(platform(rootProject.libs.jackson.bom))

        // All dependencies declared before this block will be added to your client.
        // In order to avoid dependency conflicts, try to avoid as many dependencies as possible in your clients.
        if (project.name.contains("pleo-and-another-nicholas-testing-directory-changes-nicholas-client-kotlin")) {
            return@dependencies
        }

        // Align versions of all Kotlin components
        implementation(platform(rootProject.libs.kotlin.bom))
        implementation(rootProject.libs.kotlin.reflect)
        implementation(rootProject.libs.kotlin.coroutines)

        // Common utils used by most moons. Remove those you don't need or add new as needed
        ktlint(rootProject.libs.pleo.ktlint.rules)
        implementation(rootProject.libs.pleo.money)
        implementation(rootProject.libs.pleo.utils)
        implementation(rootProject.libs.pleo.kotlin)
        implementation(rootProject.libs.pleo.aws)

        // Set up logging
        implementation(rootProject.libs.pleo.logging)
        implementation(rootProject.libs.logback)
    }

    project.configurations.findByName("runtimeClasspath")?.let { configuration ->
        tasks.register<DependencyReportTask>("listRuntimeDependencies") {
            configurations = mutableSetOf(configuration)
        }
    }
    kotlin {
        jvmToolchain {
            languageVersion = JavaLanguageVersion.of(JavaVersion.VERSION_21.majorVersion)
        }
    }

    testing {
        suites {
            val test by getting(JvmTestSuite::class)

            @Suppress("UnusedPrivateMember")
            val functest by creating(JvmTestSuite::class) {
                targets {
                    all {
                        testTask.configure {
                            // Enables using a single DB container for vanguard tests (per JVM)
                            // See: https://github.com/pleo-io/jvm-commons/tree/main/vanguard#postgres-database
                            systemProperty("vanguardtest.testcontainers.sharedpostgrescontainer", "true")

                            // Enables splitting out the test classes on multiple JVMs to parallelise their execution
                            // The specific number of forks is taken from:
                            // https://docs.gradle.org/current/userguide/performance.html#execute_tests_in_parallel
                            maxParallelForks = (Runtime.getRuntime().availableProcessors() / 2).coerceAtLeast(1)
                        }
                    }
                }
            }

            configureEach {
                if (this is JvmTestSuite) {
                    useJUnitJupiter()
                    useKotlinTest()

                    dependencies {
                        implementation(rootProject.libs.google.truth)
                        implementation(rootProject.libs.mockk)
                        implementation(platform(rootProject.libs.junit.bom))
                        implementation(rootProject.libs.junit.pioneer)
                    }

                    testlogger {
                        theme = ThemeType.MOCHA

                        // Show only failed tests
                        showPassed = false
                        showSkipped = false
                        showFailed = true
                        showOnlySlow = false
                    }

                    targets {
                        all {
                            testTask.configure {
                                systemProperty("file.encoding", "UTF-8")

                                // see https://junit.org/junit5/docs/snapshot/user-guide/#writing-tests-parallel-execution
                                // Configuration parameters to execute classes in parallel but methods in same thread
                                systemProperty("junit.jupiter.execution.parallel.enabled", "true")
                                systemProperty("junit.jupiter.execution.parallel.mode.default", "same_thread")
                                systemProperty("junit.jupiter.execution.parallel.mode.classes.default", "concurrent")
                                systemProperty("junit.jupiter.execution.parallel.config.strategy", "dynamic")
                            }
                        }
                    }
                }
            }
        }
    }
}

openAPISchemaExtension {
    resourcePackages.set(setOf("io.pleo.andanothernicholastestingdirectorychangesnicholas.rest"))
    objectMapperProcessorClass =
        "io.pleo.andanothernicholastestingdirectorychangesnicholas.rest.translators.KotlinObjectMapperProcessor"
}
