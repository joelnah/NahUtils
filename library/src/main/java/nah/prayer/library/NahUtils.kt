package nah.prayer.library

import android.content.Context

object NahUtils {
    fun init(context: Context, tag: String = "NLog") {
        PreferencesManager(context)
        Nlog.init(context, tag)
    }
}