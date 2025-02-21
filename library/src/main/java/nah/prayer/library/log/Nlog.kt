package nah.prayer.library.log

import android.content.Context
import android.content.pm.ApplicationInfo
import timber.log.Timber

class TimberDebugTree(private val tag:String) : Timber.DebugTree() {
    override fun createStackElementTag(element: StackTraceElement): String {
        return tag
    }
}
object Nlog {
    private val logManager = LogManager()
    private var isDebug = false

    fun init(context: Context, tag: String) {
        isDebug = context.applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0
        logManager.setTag(isDebug, tag)
    }

    private fun log(level: Level, tag: String = logManager.tag, msg: Any?, isPath:Boolean) {
        if (!isDebug) return

        val message = logManager.trance(msg, isPath)
        when (level) {
            Level.DEBUG -> Timber.tag(tag).d(message)
            Level.INFO -> Timber.tag(tag).i(message)
            Level.WARN -> Timber.tag(tag).w(message)
            Level.ERROR -> Timber.tag(tag).e(message)
        }
    }

    fun d(msg: Any?, isPath:Boolean = false) = log(Level.DEBUG, msg = msg, isPath = isPath)
    fun d(tag: String, msg: Any?, isPath:Boolean = false) = log(Level.DEBUG, tag, msg, isPath)

    fun i(msg: Any?, isPath:Boolean = false) = log(Level.INFO, msg = msg, isPath = isPath)
    fun i(tag: String, msg: Any?, isPath:Boolean = false) = log(Level.INFO, tag, msg, isPath)

    fun w(msg: Any?, isPath:Boolean = false) = log(Level.WARN, msg = msg, isPath = isPath)
    fun w(tag: String, msg: Any?, isPath:Boolean = false) = log(Level.WARN, tag, msg, isPath)

    fun e(msg: Any?, isPath:Boolean = false) = log(Level.ERROR, msg = msg, isPath = isPath)
    fun e(tag: String, msg: Any?, isPath:Boolean = false) = log(Level.ERROR, tag, msg, isPath)
    fun e(throwable: Throwable) = Timber.e(throwable)

    private enum class Level { DEBUG, INFO, WARN, ERROR }
}