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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import nah.prayer.library.datastore.DataStoreManager.dataSource
import java.io.IOException
import kotlin.reflect.KClass

object DataStoreManager {
    internal val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "app_preferences")
    internal lateinit var dataSource: DataStore<Preferences>
}

class DataStorePreferenceAPIImp : DataStorePreferenceAPI {


    override suspend fun<T> getPreference(key : Preferences.Key<T>, defaultValue : T) :
            Flow<T> = dataSource.data.catch { exception ->
        if (exception is IOException) {
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }.map { preferences ->
        val result = preferences[key]?: defaultValue
        result
    }


//    override suspend fun<T> putPreference(key: String, value: T) {
//
//        val preferencesKey = when (value) {
//            is String -> stringPreferencesKey(key)
//            is Int -> intPreferencesKey(key)
//            is Long -> longPreferencesKey(key)
//            is Float -> floatPreferencesKey(key)
//            is Double -> doublePreferencesKey(key)
//            is Boolean -> booleanPreferencesKey(key)
//            else -> throw IllegalArgumentException("This type can't be saved into Preferences")
//        }
//        val preferencesValue = when (value) {
//            is String -> value as String
//            is Int -> value as Int
//            is Long -> value as Long
//            is Float -> value as Float
//            is Double -> value as Double
//            is Boolean -> value as Boolean
//            else -> value as String
//        }
//        dataSource.edit { preferences ->
//            preferences[preferencesKey] = value
//        }
//    }




    override suspend fun<T> putPreference(key: Preferences.Key<T>, value: T) {
        dataSource.edit { preferences ->
            preferences[key] = value
        }
    }

    override suspend fun<T> removePreference(key: Preferences.Key<T>) {
        dataSource.edit {
            it.remove(key)
        }
    }

    override suspend fun clearAllPreference() {
        dataSource.edit { preferences ->
            preferences.clear()
        }
    }

}