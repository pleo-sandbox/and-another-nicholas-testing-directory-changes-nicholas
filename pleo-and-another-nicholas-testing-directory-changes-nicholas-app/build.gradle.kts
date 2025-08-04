import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    alias(libs.plugins.springframework.boot)
}

tasks.withType<Jar> {
    enabled = true
}

tasks.withType<BootJar> {
    archiveFileName = "${this.archiveBaseName.get()}.${this.archiveExtension.get()}"
}

tasks.bootRun {
    systemProperties = System.getProperties().map { it.key.toString() to it.value }.toMap()
}

dependencies {
    // expose internals to avoid re-importing everything in functional tests
    // ideally we would have a "package-private" equivalent, but it doesn't exist
    api(project(":pleo-and-another-nicholas-testing-directory-changes-nicholas-core"))
    api(project(":pleo-and-another-nicholas-testing-directory-changes-nicholas-kafka"))
    api(project(":pleo-and-another-nicholas-testing-directory-changes-nicholas-rest"))

    implementation(libs.pleo.app)
    implementation(libs.pleo.kafka)
    implementation(libs.pleo.kafka.dropwizard)

    // TASK: Add your external client SDKs here.
    implementation(libs.pleo.elara.client)
    implementation(libs.pleo.config)
    implementation(libs.pleo.feign)

    // Data Layer dependencies
    implementation(project(":pleo-and-another-nicholas-testing-directory-changes-nicholas-data"))
    implementation(project(":pleo-and-another-nicholas-testing-directory-changes-nicholas-model"))

    implementation(libs.pleo.kotlin)

    implementation(libs.pleo.data.exposed)
    implementation(libs.postgresql)
}
