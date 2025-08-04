import io.pleo.gradle.plugin.openAPIClient.OpenAPIClientCleanTask

group = "pleo-io.and-another-nicholas-testing-directory-changes-nicholas"
version = "0.0.0"

dependencies {
    implementation(libs.jackson.module.kotlin)
    implementation(libs.jackson.datatype.jsr)
    implementation(libs.okhttp3)
}

tasks.withType<OpenAPIClientCleanTask> {
    excludeFromCleanUp.set(listOf("build"))
}
