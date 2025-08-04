plugins {
    `java-test-fixtures`
}

dependencies {
    implementation(libs.pleo.kafka)
    implementation(libs.pleo.prop.core)

    testFixturesApi(rootProject.libs.mockk)
}
