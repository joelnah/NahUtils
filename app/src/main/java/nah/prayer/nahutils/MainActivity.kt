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
import androidx.compose.runtime.Composable
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
import kotlinx.coroutines.launch
import nah.prayer.library.NetworkUtil
import nah.prayer.library.Nlog
import nah.prayer.library.Npref
import nah.prayer.library.Nstore
import nah.prayer.library.rememberDataStore
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
                    Greeting()
                }
            }
        }
    }
}

@Composable
fun Greeting(modifier: Modifier = Modifier) {
    val stringKey = "string_key"
    val intKey = "int_key"
    val anyKey = "ANY"
    var prefRandomInt by remember { mutableIntStateOf(-1) }
    val scope = rememberCoroutineScope()

    val context = LocalContext.current

    val text = rememberDataStore(stringKey, "nil")
    val su = rememberDataStore(intKey, 0)
    val data = rememberDataStore(anyKey, DataModel())

    Nlog.d("NahUtilㄴ - ${text} - ${su} - ${data}")


    Column(
        verticalArrangement = Arrangement.Center,
    ) {

        /**
         * DataStore
         * */
        Button(onClick = {
            // Nstore 값 저장
            Nstore.putDS(scope, intKey, Random().nextInt(100))
        }) {
            Text(
                text = "Hello ${su} - ${text}!",
                modifier = modifier
            )
        }
        Button(onClick = {
            // 모델 값 저장
            Nstore.putDS(scope, anyKey, DataModel(id = "0", name = "이름-${text}", age = su + 1))
        }) {
            Text(
                text = data.toString(),
                modifier = modifier
            )
        }
        Button(onClick = {
            // Nstore 값 삭제
            Nstore.removeDS(scope, stringKey)
            Nstore.removeDS(scope, intKey)
            Nstore.removeDS(scope, anyKey)
        }) {
            Text(
                text = "del",
                modifier = modifier
            )
        }

        /**
         * SharedPreferences
         * */
        Button(onClick = {
            // SharedPreferences에 값 저장
            Npref.putData(intKey, Random().nextInt(100))
        }) {
            Text(
                text = "랜덤값 생성!",
                modifier = modifier
            )
        }
        Button(onClick = {
            // SharedPreferences의 값 가져오기
            prefRandomInt = Npref.getData(intKey, -1)
        }) {
            Text(
                text = "랜덤값 ${prefRandomInt}",
                modifier = modifier
            )
        }

        Button(onClick = {
            // SharedPreferences의 값 삭제
            Npref.removeData(intKey)
        }) {
            Text(
                text = "del",
                modifier = modifier
            )
        }



        /**
         * NetworkUtil
         * */
        Button(onClick = {
            // network check
            scope.launch {
                NetworkUtil.getWhatKindOfNetwork(context)
            }
        }) {
            Text(
                text = "network check",
                modifier = modifier
            )
        }


    }
}


@Composable
fun ColumnScope.ButtonTest(color:Color) {
    val gradientBrush = Brush.verticalGradient(
        colors = listOf(
            Color.Transparent,
            Color.White.copy(alpha = 0.07f),
            Color.Transparent,
        ),
        startY = 0f,
        endY = Float.POSITIVE_INFINITY,
        tileMode = TileMode.Clamp
    )
    Box(){
        Button(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.small,
            colors = ButtonDefaults.buttonColors(
                containerColor = color,
            ),
            onClick = {
                // USER_AGE 값 저장
            }) {
            Text(
                text = "시작",
                color = Color.White,
            )
        }
        Box(modifier = Modifier.fillMaxWidth().height(30.dp).background(gradientBrush).align(
            Alignment.Center))
    }

    Spacer(modifier = Modifier.height(60.dp))
}