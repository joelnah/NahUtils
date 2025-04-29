package nah.prayer.nahutils.ui

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import nah.prayer.library.NetworkUtil
import nah.prayer.library.Npref
import nah.prayer.library.Nstore
import nah.prayer.library.log.Nlog
import nah.prayer.library.rememberDataStore
import nah.prayer.nahutils.domain.DataModel
import nah.prayer.nahutils.utils.anyKey
import nah.prayer.nahutils.utils.intKey
import nah.prayer.nahutils.utils.stringKey
import java.util.Random

@Composable
fun StorageScreen() {
    var prefRandomInt by remember { mutableStateOf(Npref.getData(anyKey, DataModel())) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    // DataStore 값들을 remember를 통해 상태로 관리
    val text = rememberDataStore(stringKey, "nil")
    val su = rememberDataStore(intKey, 0)
    val data = rememberDataStore(anyKey, DataModel())

    Nstore.testDataStoreEncryption()

    Column(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        /**
         * DataStore
         * */
        Text(
            "DataStore",
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Button(onClick = {
            // Random 객체 생성은 한 번만 하도록 수정
            val random = Random()
            val randomChar = ('a' + random.nextInt(26)).toString()
            val randomInt = random.nextInt(100)

            scope.launch {
                // 값을 state에 반영하도록 수정
                Nstore.putDS(scope, stringKey, randomChar)
                Nstore.putDS(scope, intKey, randomInt)
                Nstore.putDS(
                    scope,
                    anyKey,
                    DataModel(
                        id = random.nextInt(100).toString(),
                        name = randomChar,
                        age = randomInt
                    )
                )
            }
        }) {
            Text(text = "값 저장하기 (age: $su, name: $text)")
        }

        Button(onClick = {
            scope.launch {
                val random = Random()
                val newData = DataModel(
                    id = random.nextInt(100).toString(),
                    name = text,
                    age = su
                )

                Nstore.putDS(scope, anyKey, newData)
            }
        }) {
            Text(text = "DataModel: $data")
        }

        Button(
            onClick = {
                scope.launch {
                    Nstore.removeDS(scope, stringKey)
                    Nstore.removeDS(scope, intKey)
                    Nstore.removeDS(scope, anyKey)
                }
            },
        ) {
            Text(text = "DataStore 삭제",)
        }

        Spacer(modifier = Modifier.height(24.dp))

        /**
         * SharedPreferences
         * the usage is the same as DataStore
         * */
        Text(
            "SharedPreferences",
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Button(onClick = {
            val random = Random()
            val newData = DataModel(
                id = random.nextInt(100).toString(),
                name = ('a' + random.nextInt(26)).toString(),
                age = random.nextInt(100)
            )

            Npref.putData(anyKey, newData)
            // 상태 업데이트
            prefRandomInt = newData
        }) {
            Text(text = "랜덤값 생성")
        }

        Button(onClick = {
            // 데이터를 가져와서 상태 업데이트
            prefRandomInt = Npref.getData(anyKey, DataModel())
        }) {
            Text(text = "현재값 조회: $prefRandomInt")
        }

        Button(
            onClick = {
                Npref.removeData(anyKey)
                prefRandomInt = DataModel() // 상태 초기화
            },
        ) {
            Text(text = "SharedPreferences 삭제",)
        }

        Spacer(modifier = Modifier.height(24.dp))

        /**
         * NetworkUtil
         * */
        Button(
            onClick = {
                scope.launch {
                    val networkType = NetworkUtil.getWhatKindOfNetwork(context)
                    // 네트워크 상태를 토스트 메시지로 표시
                    Toast.makeText(
                        context,
                        "현재 네트워크: $networkType",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
        ) {
            Text(text = "네트워크 상태 확인")
        }
    }
}

