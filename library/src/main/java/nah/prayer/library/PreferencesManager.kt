package nah.prayer.library

import android.content.Context
import androidx.datastore.core.DataStoreFactory
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import nah.prayer.library.serializer.EncryptedPreferencesSerializer
import java.io.File

const val PREF_NAME = "nah_app_preferences.preferences_pb"
const val DATASTORE_NAME = "nah_app_datastore.preferences_pb"

internal class PreferencesManager(val context: Context) {
    private val dataStoreFile = File(context.filesDir, DATASTORE_NAME)
//    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATASTORE_NAME)
    private val masterKey: MasterKey = MasterKey.Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()
    init {
        Nstore.dataStore = DataStoreFactory.create(
            serializer = EncryptedPreferencesSerializer,
            produceFile = { dataStoreFile }
        )
//        Nstore.dataStore = context.dataStore
        Npref.sharedPreferences = EncryptedSharedPreferences.create(
            context,
            PREF_NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }
}