package nah.prayer.library

import androidx.compose.runtime.Composable
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.gson.Gson
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.IOException

@Composable
inline fun <reified T : Any> rememberDataStore(key: String, defaultValue: T): T {
    val scope = rememberCoroutineScope()
    val stateFlow = remember(key) { Nstore.getDS(scope, key, defaultValue) }
    val state by stateFlow.collectAsState()
    return state
}

@JvmName("anyKey")
fun anyPreferencesKey(name: String): Preferences.Key<String> = stringPreferencesKey(name)

object Nstore {
    lateinit var dataSource: DataStore<Preferences>
    private val gson = Gson()

    fun<T:Any> getDS(scope: CoroutineScope, key: String, defaultValue: T): StateFlow<T> {
        val flow = dataSource.data.catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            when (defaultValue) {
                is String -> preferences[stringPreferencesKey(key)] as? T ?: defaultValue
                is Int -> preferences[intPreferencesKey(key)] as? T ?: defaultValue
                is Long -> preferences[longPreferencesKey(key)] as? T ?: defaultValue
                is Float -> preferences[floatPreferencesKey(key)] as? T ?: defaultValue
                is Double -> preferences[doublePreferencesKey(key)] as? T ?: defaultValue
                is Boolean -> preferences[booleanPreferencesKey(key)] as? T ?: defaultValue
                else -> gson.fromJson(preferences[anyPreferencesKey(key)], defaultValue::class.java) ?: defaultValue
            }
        }.stateIn(scope = scope, started = SharingStarted.Lazily, initialValue = defaultValue)

        return flow
    }

    fun <T>putDS(scope: CoroutineScope, key: String, value: T) {
        scope.launch {
            dataSource.edit { preferences ->
                when (value) {
                    is String -> preferences[stringPreferencesKey(key)] = value
                    is Int -> preferences[intPreferencesKey(key)] = value
                    is Long -> preferences[longPreferencesKey(key)] = value
                    is Float -> preferences[floatPreferencesKey(key)] = value
                    is Double -> preferences[doublePreferencesKey(key)] = value
                    is Boolean -> preferences[booleanPreferencesKey(key)] = value
                    else -> preferences[anyPreferencesKey(key)] = gson.toJson(value)
                }
            }
        }
    }

    fun removeDS(scope: CoroutineScope, key: String) {
        scope.launch {
            dataSource.data
                .map { preferences ->
                    val keyToRemove = preferences.asMap().keys.firstOrNull { i -> i.name == key }
                    if (keyToRemove != null) {
                        dataSource.edit {
                            it.remove(keyToRemove)
                        }
                    } else {
                        // 키가 존재하지 않는 경우에 대한 처리
                        Nlog.i("키가 존재하지 않습니다: $key")
                    }
                }
                .distinctUntilChanged()  // 중복 실행 방지
                .firstOrNull()
        }
    }


    fun clearData(scope: CoroutineScope) {
        scope.launch {
            dataSource.edit {
                it.clear()
            }
        }
    }

}