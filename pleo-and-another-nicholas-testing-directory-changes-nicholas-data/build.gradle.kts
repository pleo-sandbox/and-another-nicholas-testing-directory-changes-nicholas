dependencies {
    implementation(libs.pleo.data.exposed)
    implementation(libs.pleo.data.flyway)
    implementation(libs.postgresql)
    implementation(libs.pleo.rest.models)
    implementation(project(":pleo-and-another-nicholas-testing-directory-changes-nicholas-model"))

    testImplementation(libs.pleo.daltest)
}

tasks {
    test {
        systemProperty(
            "postgresdaltest.localjdbcurl",
            System.getProperty("postgresdaltest.localjdbcurl.andanothernicholastestingdirectorychangesnicholas"),
        )
        systemProperty(
            "postgresdaltest.testcontainers.postgresdockerimagename",
            "postgres:17",
        )
    }
}
