package nah.prayer.library

import android.content.Context
import android.content.pm.ApplicationInfo
import nah.prayer.library.datastore.DataStoreManager.dataSource
import nah.prayer.library.datastore.DataStoreManager.dataStore
import timber.log.Timber

object NahUtils {
    fun init(context: Context) {
        dataSource = context.dataStore
        Nlog.init(context, "nah")
    }
}