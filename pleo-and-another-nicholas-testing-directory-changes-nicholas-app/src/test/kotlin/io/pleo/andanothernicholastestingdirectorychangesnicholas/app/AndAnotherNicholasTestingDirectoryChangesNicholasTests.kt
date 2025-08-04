package io.pleo.andanothernicholastestingdirectorychangesnicholas.app

import io.pleo.app.framework.PleoKotlinRestApplication
import io.pleo.app.test.EnvConfigurationTest

/**
 * Tests both whether all bindings requested are in fact bound, and if there are any syntax errors in the used
 * properties in the k8s/product-(dev|staging|production)/application-and-another-nicholas-testing-directory-changes-nicholas.yaml files.
 */
class AndAnotherNicholasTestingDirectoryChangesNicholasTests : EnvConfigurationTest(service = "and-another-nicholas-testing-directory-changes-nicholas") {
    override fun buildApp(): PleoKotlinRestApplication = AndAnotherNicholasTestingDirectoryChangesNicholas()
}
