package nah.prayer.nahutils

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import nah.prayer.library.Nlog
import nah.prayer.library.datastore.Npref
import nah.prayer.nahutils.ui.theme.NahUtilsTheme
import java.util.Random
import java.util.UUID

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
    val viewModel by lazy { MainActViewModel() }
    val scope = rememberCoroutineScope()
//    val preferenceAPIImpl = Npref()


    val text = viewModel.text.collectAsState()
    val su = viewModel.su.collectAsState()
//    var su by remember{ mutableStateOf(0) }
//
//    LaunchedEffect(key1 = Unit, block = {
//        Npref().getPref(stringKey,name).collect {
//            text = it
//            Nlog.d("text : $text")
//        }
//        Npref().getPref(intKey,su).collect {
//            Nlog.d("int : $it")
//        }
//    })

    Nlog.d(text.value)
    Nlog.d(su.value)
    Column {
        Button(onClick = {
            // USER_AGE 값 저장
            scope.launch {
                viewModel.pref.putPref(viewModel.intKey, Random().nextInt(100))
            }
            scope.launch {
                viewModel.pref.putPref(viewModel.stringKey, UUID.randomUUID().toString())
            }

        }) {
            Text(
                text = "Hello ${su.value} - ${text.value}!",
                modifier = modifier
            )
        }
        Button(onClick = {
            // USER_AGE 값 저장
            scope.launch {
//                viewModel.pref.clearAllPreference()
                viewModel.pref.removePref(viewModel.stringKey)
            }
        }) {
            Text(
                text = "del",
                modifier = modifier
            )
        }


    }
}