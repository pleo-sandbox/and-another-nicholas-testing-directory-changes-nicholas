dependencies {
    implementation(project(":pleo-and-another-nicholas-testing-directory-changes-nicholas-core"))
    implementation(libs.dropwizard.metrics.healthchecks)
    implementation(libs.pleo.rest)
    implementation(libs.pleo.rest.models)
    implementation(libs.pleo.features)
    implementation(libs.swagger.integration)
    implementation(libs.swagger.annotations)

    // Data Layer dependencies
    implementation(project(":pleo-and-another-nicholas-testing-directory-changes-nicholas-data"))
    implementation(project(":pleo-and-another-nicholas-testing-directory-changes-nicholas-model"))
}

openAPIClientGenerationExtension {
    templateDir = "$projectDir/src/main/resources/custom_templates"
    useCentralizedTemplates = true
}
