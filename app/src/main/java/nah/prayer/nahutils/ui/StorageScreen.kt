package nah.prayer.nahutils.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
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
    var prefRandomInt by remember { mutableIntStateOf(-1) }
    val scope = rememberCoroutineScope()

    val context = LocalContext.current

    val text = rememberDataStore(stringKey, "nil")
    val su = rememberDataStore(intKey, 0)
    val data = rememberDataStore(anyKey, DataModel())
    Nlog.d("NahUtil - $su")

    Column(
        verticalArrangement = Arrangement.Center,
    ) {
        /**
         * DataStore
         * */
        Button(onClick = {
            // It saved the Types
            Nstore.putDS(scope, intKey, Random().nextInt(100))
        }) {
            Text(
                text = "Hello ${su} - ${text}!",
            )
        }
        Button(onClick = {
            // It saved the DataModel
            Nstore.putDS(scope, anyKey, DataModel(id = "0", name = "이름-${text}", age = su + 1))
        }) {
            Text(
                text = data.toString(),
            )
        }
        Button(onClick = {
            // remove
            Nstore.removeDS(scope, stringKey)
            Nstore.removeDS(scope, intKey)
            Nstore.removeDS(scope, anyKey)
        }) {
            Text(
                text = "del",
            )
        }


        /**
         * SharedPreferences
         * the usage is the same as DataStore
         * */
        Button(onClick = {
            Npref.putData(intKey, Random().nextInt(100))
        }) {
            Text(
                text = "랜덤값 생성!",
            )
        }

        Button(onClick = {
            prefRandomInt = Npref.getData(intKey, -1)
        }) {
            Text(
                text = "랜덤값 ${prefRandomInt}",
            )
        }

        Button(onClick = {
            Npref.removeData(intKey)
        }) {
            Text(
                text = "del",
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
            )
        }


    }
}