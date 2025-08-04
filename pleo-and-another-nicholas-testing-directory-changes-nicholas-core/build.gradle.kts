dependencies {
    implementation(libs.pleo.app)
    implementation(libs.pleo.kafka)
    implementation(libs.pleo.kafka.dropwizard)
    implementation(project(":pleo-and-another-nicholas-testing-directory-changes-nicholas-kafka"))
    implementation(project(":pleo-and-another-nicholas-testing-directory-changes-nicholas-data"))
    implementation(project(":pleo-and-another-nicholas-testing-directory-changes-nicholas-model"))
    // TASK: Define and use your own external client dependencies here.
    api(libs.pleo.elara.client)
}
