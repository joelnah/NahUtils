package nah.prayer.library

import android.content.Context
import nah.prayer.library.DataStoreManager.dataSource
import nah.prayer.library.DataStoreManager.dataStore

object NahUtils {
    fun init(context: Context) {
        dataSource = context.dataStore
        Nlog.init(context, "nah")
    }
}