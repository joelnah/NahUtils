package nah.prayer.nahutils.ui.test

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import nah.prayer.library.log.Nlog

@Composable
fun Test1() {
    Nlog.d(msg = "Test1 function called", true)
    Test2()
}