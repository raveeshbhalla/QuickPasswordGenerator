package app.pwdgen.ui.main

import org.junit.Test

import org.junit.Assert.*

class MainFragmentKtTest {

    @Test
    fun getAdjustedSeekbarProgress() {
        assertTrue(getAdjustedSeekbarProgress(0) == 4)
        assertTrue(getAdjustedSeekbarProgress(21) == 25)
        assertTrue(getAdjustedSeekbarProgress(4) == 8)
        assertTrue(getAdjustedSeekbarProgress(12) == 16)
        assertTrue(getAdjustedSeekbarProgress(16) == 20)
    }
}