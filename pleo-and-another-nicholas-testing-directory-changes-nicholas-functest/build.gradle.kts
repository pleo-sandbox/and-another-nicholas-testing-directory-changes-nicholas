@file:Suppress("UnstableApiUsage")

testing {
    suites {
        @Suppress("UnusedPrivateMember")
        val functest by getting(JvmTestSuite::class) {
            dependencies {
                implementation(project(":pleo-and-another-nicholas-testing-directory-changes-nicholas-app"))
                implementation(project(":pleo-and-another-nicholas-testing-directory-changes-nicholas-core"))
                implementation(project(":pleo-and-another-nicholas-testing-directory-changes-nicholas-client-kotlin"))

                implementation(libs.pleo.kafka.dropwizard)
                implementation(libs.pleo.kafka)
                implementation(libs.pleo.vanguard)
                implementation(libs.awaitility)
                implementation(libs.pleo.kerberos.client)
                implementation(libs.logging.interceptor)
                implementation(libs.dropwizard.testing)
                implementation(libs.junit.pioneer)

                runtimeOnly(project(":pleo-and-another-nicholas-testing-directory-changes-nicholas-rest"))

                implementation(libs.kotlin.coroutines)
            }
        }
    }
}
