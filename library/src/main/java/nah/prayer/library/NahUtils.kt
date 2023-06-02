package nah.prayer.library

import android.content.Context
import nah.prayer.library.datastore.DataStoreManager.dataSource
import nah.prayer.library.datastore.DataStoreManager.dataStore
import timber.log.Timber

object NahUtils {
    fun init(context: Context, tag: String="nah") {
        dataSource = context.dataStore
        if (BuildConfig.DEBUG) {
            Timber.plant(TimberDebugTree(tag))
        } else {
//            Timber.plant(TimberReleaseTree())}
        }
    }
}