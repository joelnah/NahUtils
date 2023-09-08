package nah.prayer.library

import android.content.Context
import nah.prayer.library.datastore.DataStoreManager.dataSource
import nah.prayer.library.datastore.DataStoreManager.dataStore

object NahUtils {
    fun init(context: Context) {
        dataSource = context.dataStore
        Nlog.init(context, "nah")
    }
}