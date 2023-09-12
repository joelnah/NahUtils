package nah.prayer.library.datastore

import android.content.Context
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
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import nah.prayer.library.datastore.DataStoreManager.dataSource
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.Serializable
import kotlin.reflect.KClass

object DataStoreManager {
    internal val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "app_preferences")
    internal lateinit var dataSource: DataStore<Preferences>
}


object Npref {
    val gson = Gson()

    @Suppress("UNCHECKED_CAST")
    fun<T> getPref(scope: CoroutineScope, key: String, defaultValue: T): StateFlow<T> {
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
                else -> throw IllegalArgumentException("Wrong Type")
            }
        }.stateIn(scope = scope, started = SharingStarted.Lazily, initialValue = defaultValue)

        return flow
    }

    fun<T> getPref(scope: CoroutineScope, key: String, defaultValue: Class<T>): StateFlow<T?> {
        val flow = dataSource.data.catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            gson.fromJson(preferences[stringPreferencesKey(key)], defaultValue) ?: null
        }.stateIn(scope = scope, started = SharingStarted.Lazily, initialValue = null)

        return flow
    }



    fun putPref(scope: CoroutineScope, key: String, value: Any) {
        scope.launch {
            dataSource.edit { preferences ->
                when (value) {
                    is String -> preferences[stringPreferencesKey(key)] = value
                    is Int -> preferences[intPreferencesKey(key)] = value
                    is Long -> preferences[longPreferencesKey(key)] = value
                    is Float -> preferences[floatPreferencesKey(key)] = value
                    is Double -> preferences[doublePreferencesKey(key)] = value
                    is Boolean -> preferences[booleanPreferencesKey(key)] = value
                    else -> preferences[stringPreferencesKey(key)] = gson.toJson(value)
                }
            }
        }
    }
    fun removePref(scope: CoroutineScope, key: String) {
        scope.launch {
            dataSource.data.map { preferences ->
                dataSource.edit {
                    it.remove(preferences.asMap().keys.first { i -> i.name == key })
                }
            }.firstOrNull()
        }
    }

    fun clearAllPreference(scope: CoroutineScope) {
        scope.launch {
            dataSource.edit { preferences ->
                preferences.clear()
            }
        }
    }
}