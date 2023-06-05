package nah.prayer.library.datastore

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface DataStorePreferenceAPI {
    suspend fun<T> getPref(key: String, defaultValue : T): Flow<T>
//    fun<T> getPref(key: String, defaultValue : T): StateFlow<T>
    suspend fun<T> putPref(key: String, value: T)
    suspend fun removePref(key: String)
    suspend fun clearAllPreference()
}