rootProject.name = "and-another-nicholas-testing-directory-changes-nicholas"

include(
    "pleo-and-another-nicholas-testing-directory-changes-nicholas-app",
    "pleo-and-another-nicholas-testing-directory-changes-nicholas-rest",
    "pleo-and-another-nicholas-testing-directory-changes-nicholas-core",
    "pleo-and-another-nicholas-testing-directory-changes-nicholas-functest",
    "pleo-and-another-nicholas-testing-directory-changes-nicholas-client-kotlin",
    "pleo-and-another-nicholas-testing-directory-changes-nicholas-kafka",
    "pleo-and-another-nicholas-testing-directory-changes-nicholas-model",
    "pleo-and-another-nicholas-testing-directory-changes-nicholas-data"
)

pluginManagement {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/pleo-io/pleo-gradle-plugin")
            credentials {
                username = providers.gradleProperty("gpr.user").getOrElse(System.getenv("GITHUB_ACTOR"))
                password = providers.gradleProperty("gpr.key").getOrElse(System.getenv("GRADLE_READ_KEY"))
            }
        }
        gradlePluginPortal()

        mavenLocal()
    }
}
