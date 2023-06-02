package nah.prayer.nahutils

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch
import nah.prayer.library.Nlog
import nah.prayer.library.datastore.DataStorePreferenceAPIImp
import nah.prayer.library.datastore.DataStorePreferenceConstants.stringKey
import nah.prayer.nahutils.ui.theme.NahUtilsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NahUtilsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    val scope = rememberCoroutineScope()
    Nlog.d("Greeting")
//    val preferenceAPIImpl = DataStorePreferenceAPIImp()
//    val stringKey = stringKey("string_key")
//
    var text by remember{ mutableStateOf(name) }
//
//    LaunchedEffect(key1 = Unit, block = {
//        preferenceAPIImpl.getPreference(stringKey,name).collect {
//            text = it
//            Nlog.d("text : $text")
//        }
//    })

    Button(onClick = {
        Nlog.d("text : $text")
        // USER_AGE 값 저장
//        scope.launch {
//            preferenceAPIImpl.putPreference(stringKey, "tsetsetsetsets")
//        }
    }) {
        Text(
            text = "Hello $text!",
            modifier = modifier
        )
    }

}
