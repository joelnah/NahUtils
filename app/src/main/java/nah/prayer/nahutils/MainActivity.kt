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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import nah.prayer.library.Nlog
import nah.prayer.library.Npref
import nah.prayer.nahutils.ui.theme.NahUtilsTheme
import java.util.Random

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


    val text = viewModel.text.collectAsState()
    val su = viewModel.su.collectAsState()
    val data = viewModel.data.collectAsState()


    Nlog.d(text.value)
    Nlog.setTag("NahUtils")
    Nlog.d(su.value)
    Column {
        Button(onClick = {
            // USER_AGE 값 저장
            Npref.putPref(scope, viewModel.intKey, Random().nextInt(100))
        }) {
            Text(
                text = "Hello ${su.value} - ${text.value}!",
                modifier = modifier
            )
        }
        Button(onClick = {
            // 모델 값 저장
            Npref.putPref(scope, viewModel.anyKey, DataModel(id = "0", name = "이름-${text.value}", age = su.value + 1))
        }) {
            Text(
                text = data.value.toString(),
                modifier = modifier
            )
        }
        Button(onClick = {
            // USER_AGE 값 삭제
            Npref.removePref(scope, viewModel.stringKey)
        }) {
            Text(
                text = "del",
                modifier = modifier
            )
        }


    }
}