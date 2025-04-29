package nah.prayer.library

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import nah.prayer.library.log.Nlog
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



// 기존 Nstore 코드 수정
object Nstore {
    lateinit var dataStore: DataStore<Preferences>
    private val gson = Gson()
    /**
     * DataStore에서 값을 읽어 StateFlow로 반환합니다.
     * @param scope 코루틴 스코프
     * @param key 저장된 값의 키
     * @param defaultValue 기본값 (타입 추론에도 사용됨)
     * @return 저장된 값을 포함하는 StateFlow
     */
    fun <T : Any> getDS(scope: CoroutineScope, key: String, defaultValue: T): StateFlow<T> {
        val flow = dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                try {
                    when (defaultValue) {
                        is String -> preferences[stringPreferencesKey(key)] as? T ?: defaultValue
                        is Int -> preferences[intPreferencesKey(key)] as? T ?: defaultValue
                        is Long -> preferences[longPreferencesKey(key)] as? T ?: defaultValue
                        is Float -> preferences[floatPreferencesKey(key)] as? T ?: defaultValue
                        is Double -> preferences[doublePreferencesKey(key)] as? T ?: defaultValue
                        is Boolean -> preferences[booleanPreferencesKey(key)] as? T ?: defaultValue
                        else -> {
                            val jsonValue = preferences[anyPreferencesKey(key)]
                            if (jsonValue != null) {
                                gson.fromJson(jsonValue, defaultValue::class.java) ?: defaultValue
                            } else {
                                defaultValue
                            }
                        }
                    }
                } catch (e: Exception) {
                    Nlog.e("getDS 오류: ${e.message}")
                    defaultValue
                }
            }
            .stateIn(scope = scope, started = SharingStarted.Lazily, initialValue = defaultValue)

        return flow
    }

    // 나머지 함수들은 동일하게 유지
    /**
     * DataStore에 값을 저장합니다.
     * @param scope 코루틴 스코프
     * @param key 저장할 값의 키
     * @param value 저장할 값
     */
    fun <T> putDS(scope: CoroutineScope, key: String, value: T) {
        scope.launch {
            try {
                dataStore.edit { preferences ->
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
            } catch (e: Exception) {
                Nlog.e("putDS 오류: ${e.message}")
            }
        }
    }

    /**
     * DataStore에서 특정 키를 삭제합니다.
     * @param scope 코루틴 스코프
     * @param key 삭제할 키
     */
    fun removeDS(scope: CoroutineScope, key: String) {
        scope.launch {
            try {
                dataStore.edit { preferences ->
                    // 키 타입을 알 수 없으므로 모든 타입에 대해 시도
                    preferences.remove(stringPreferencesKey(key))
                    preferences.remove(intPreferencesKey(key))
                    preferences.remove(longPreferencesKey(key))
                    preferences.remove(floatPreferencesKey(key))
                    preferences.remove(doublePreferencesKey(key))
                    preferences.remove(booleanPreferencesKey(key))
                    preferences.remove(anyPreferencesKey(key))
                }
                Nlog.i("키 삭제 완료: $key")
            } catch (e: Exception) {
                Nlog.e("removeDS 오류: ${e.message}")
            }
        }
    }

    /**
     * DataStore의 모든 데이터를 지웁니다.
     * @param scope 코루틴 스코프
     */
    fun clearData(scope: CoroutineScope) {
        scope.launch {
            try {
                dataStore.edit { preferences ->
                    preferences.clear()
                }
                Nlog.i("모든 데이터 삭제 완료")
            } catch (e: Exception) {
                Nlog.e("clearData 오류: ${e.message}")
            }
        }
    }

    fun testDataStoreEncryption() {

        val testKey = "encryption_test_key"
        val testValue = "This is a test for encryption: ${System.currentTimeMillis()}"

        // 1. 값 저장
        MainScope().launch {
            try {
                Nlog.d("저장 시도: $testValue")
                dataStore.edit { preferences ->
                    preferences[stringPreferencesKey(testKey)] = testValue
                }
                Nlog.d("저장 완료")

                // 2. 저장 후 약간의 딜레이를 줌
                kotlinx.coroutines.delay(500)

                // 3. 저장된 값 읽기
                val retrievedValue = dataStore.data.first()[stringPreferencesKey(testKey)]
                Nlog.d("읽은 값: $retrievedValue")
                Nlog.d("테스트 결과: ${testValue == retrievedValue}")
            } catch (e: Exception) {
                Nlog.e("테스트 실패: ${e.message}")
                e.printStackTrace()
            }
        }
    }
}