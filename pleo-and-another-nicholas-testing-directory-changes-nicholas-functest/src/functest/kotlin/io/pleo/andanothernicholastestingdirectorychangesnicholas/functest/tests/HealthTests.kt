@file:Suppress("FunctionNaming")

package io.pleo.andanothernicholastestingdirectorychangesnicholas.functest.tests

import com.google.common.truth.Truth.assertThat
import io.pleo.andanothernicholastestingdirectorychangesnicholas.functest.base.AndAnotherNicholasTestingDirectoryChangesNicholasVanguardTest
import org.junit.jupiter.api.Test

class HealthTests : AndAnotherNicholasTestingDirectoryChangesNicholasVanguardTest() {
    @Test
    fun `liveness check succeeds`() {
        val livenessResult = andAnotherNicholasTestingDirectoryChangesNicholasClient.checkLiveness()

        assertThat(livenessResult.data.healthy).isTrue()
    }

    @Test
    fun `readiness check succeeds`() {
        val readinessResult = andAnotherNicholasTestingDirectoryChangesNicholasClient.checkReadiness()

        assertThat(readinessResult.data.healthy).isTrue()
    }
}
