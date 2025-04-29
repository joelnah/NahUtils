package nah.prayer.library

import android.content.Context
import android.content.SharedPreferences
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
import java.io.IOException


object Npref {
    lateinit var sharedPreferences: SharedPreferences
    val gson = Gson()

    inline fun <reified T>putData(key: String, value: T) {
        sharedPreferences.edit().apply {
            when(value) {
                is String -> putString(key, value)
                is Int -> putInt(key, value)
                is Boolean -> putBoolean(key, value)
                is Float -> putFloat(key, value)
                is Long -> putLong(key, value)
                else -> putString(key, gson.toJson(value, T::class.java))
            }
        }.apply()
    }

    inline fun <reified T>getData(key: String, defaultValue: T): T {
        return when(defaultValue) {
            is String ->( sharedPreferences.getString(key, defaultValue as String) ?: defaultValue) as T
            is Int -> (sharedPreferences.getInt(key, defaultValue))  as T
            is Boolean -> (sharedPreferences.getBoolean(key, defaultValue)) as T
            is Float -> (sharedPreferences.getFloat(key, defaultValue)) as T
            is Long -> (sharedPreferences.getLong(key, defaultValue)) as T
            else -> {
                val json = sharedPreferences.getString(key, "")
                if (json.isNullOrEmpty()) defaultValue
                else gson.fromJson(json, T::class.java) ?: defaultValue
            }
        }
    }

    fun removeData(key: String) {
        sharedPreferences.edit().remove(key).apply()
    }

    fun clearData() {
        sharedPreferences.edit().clear().apply()
    }

}

