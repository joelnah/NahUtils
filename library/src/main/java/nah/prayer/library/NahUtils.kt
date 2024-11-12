package nah.prayer.library

import android.content.Context

object NahUtils {
    fun init(context: Context) {
        PreferencesManager(context)
        Nlog.init(context, "Log")
    }
}