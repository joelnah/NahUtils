package nah.prayer.nahutils.utils

import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavHostController


val LocalNavController = compositionLocalOf<NavHostController> {
    error("No NavController provided")
}
