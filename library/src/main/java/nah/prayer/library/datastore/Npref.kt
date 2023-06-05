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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import nah.prayer.library.Nlog
import nah.prayer.library.datastore.DataStoreManager.dataSource
import java.io.IOException

object DataStoreManager {
    internal val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "app_preferences")
    internal lateinit var dataSource: DataStore<Preferences>
}

class Npref : DataStorePreferenceAPI {

//    @Suppress("UNCHECKED_CAST")
//    override fun<T> getPref(key : String, defaultValue : T) : StateFlow<T> = dataSource.data.catch { exception ->
//        if (exception is IOException) {
//            emit(emptyPreferences())
//        } else {
//            throw exception
//        }
//    }.map { preferences ->
//        when (defaultValue) {
//            is String -> preferences[stringPreferencesKey(key)] as? T ?: defaultValue
//            is Int -> preferences[intPreferencesKey(key)] as? T ?: defaultValue
//            is Long -> preferences[longPreferencesKey(key)] as? T ?: defaultValue
//            is Float -> preferences[floatPreferencesKey(key)] as? T ?: defaultValue
//            is Double -> preferences[doublePreferencesKey(key)] as? T ?: defaultValue
//            is Boolean -> preferences[booleanPreferencesKey(key)] as? T ?: defaultValue
//            else -> throw IllegalArgumentException("Unsupported type")
//        }
//    }.stateIn(scope = CoroutineScope(Dispatchers.IO), started = SharingStarted.Lazily, initialValue = defaultValue)

    @Suppress("UNCHECKED_CAST")
    override suspend fun<T> getPref(key : String, defaultValue : T) :
            Flow<T> = dataSource.data.catch { exception ->
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
            else -> throw IllegalArgumentException("Unsupported type")
        }
    }

    override suspend fun<T> putPref(key: String, value: T) {
        dataSource.edit { preferences ->
            when(value){
                is String -> preferences[stringPreferencesKey(key)] = value
                is Int -> preferences[intPreferencesKey(key)] = value
                is Long -> preferences[longPreferencesKey(key)] = value
                is Float -> preferences[floatPreferencesKey(key)] = value
                is Double -> preferences[doublePreferencesKey(key)] = value
                is Boolean -> preferences[booleanPreferencesKey(key)] = value
                else -> preferences[stringPreferencesKey(key)] = value.toString()
            }
        }
    }

    override suspend fun removePref(key: String) {
        dataSource.data.map { preferences ->
            dataSource.edit {
                it.remove(preferences.asMap().keys.first { i -> i.name == key })
            }
        }.firstOrNull()
    }

    override suspend fun clearAllPreference() {
        dataSource.edit { preferences ->
            preferences.clear()
        }
    }

}