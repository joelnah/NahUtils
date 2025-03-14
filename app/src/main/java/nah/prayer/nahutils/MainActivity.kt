package nah.prayer.nahutils

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import nah.prayer.library.NetworkUtil
import nah.prayer.library.Npref
import nah.prayer.library.Nstore
import nah.prayer.library.composutils.rememberKeyboardVisibility
import nah.prayer.library.log.Nlog
import nah.prayer.library.rememberDataStore
import nah.prayer.nahutils.ui.ExtensionScreen
import nah.prayer.nahutils.ui.StartScreen
import nah.prayer.nahutils.ui.StorageScreen
import nah.prayer.nahutils.ui.TestScreen
import nah.prayer.nahutils.ui.theme.NahUtilsTheme
import nah.prayer.nahutils.utils.LocalNavController
import nah.prayer.nahutils.utils.Screens
import java.util.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NahUtilsTheme {
                val navController = rememberNavController()
                CompositionLocalProvider(
                    LocalNavController provides navController,
                ) {
                    NavHost(navController = LocalNavController.current, startDestination = Screens.START) {
                        composable(Screens.START) { StartScreen() }
                        composable(Screens.STORAGE) { StorageScreen() }
                        composable(Screens.EXTENSION) { ExtensionScreen() }
                        composable(Screens.TEST) { TestScreen() }
                    }
                }

            }
        }
    }
}


