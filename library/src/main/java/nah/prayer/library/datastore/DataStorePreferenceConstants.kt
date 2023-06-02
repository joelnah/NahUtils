package nah.prayer.library.datastore

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object DataStorePreferenceConstants {
    fun stringKey(key: String): Preferences.Key<String> = stringPreferencesKey(key)
    fun intKey(key: String): Preferences.Key<Int> = intPreferencesKey(key)
    fun longKey(key: String): Preferences.Key<Long> = longPreferencesKey(key)
    fun floatKey(key: String): Preferences.Key<Float> = floatPreferencesKey(key)
    fun doubleKey(key: String): Preferences.Key<Double> = doublePreferencesKey(key)
    fun booleanKey(key: String): Preferences.Key<Boolean> = booleanPreferencesKey(key)
}