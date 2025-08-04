package io.pleo.andanothernicholastestingdirectorychangesnicholas.functest.base

import io.pleo.app.framework.PleoRestApplication
import io.pleo.vanguard.tests.ForeignKeyIndexTest

// This test ensures that there are no Foreign Keys missing and index in your DB definition
class ForeignKeyIndexTest :
    AndAnotherNicholasTestingDirectoryChangesNicholasVanguardTest(),
    ForeignKeyIndexTest {
    override fun getApp(): PleoRestApplication = application
}
