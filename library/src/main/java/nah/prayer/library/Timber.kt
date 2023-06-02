package nah.prayer.library

import android.annotation.SuppressLint
import android.util.Log
import timber.log.Timber

//class TimberReleaseTree : Timber.Tree() {
//    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
//        if (priority == Log.ERROR || priority == Log.WARN) {
//            Timber.e(trance(message))
//        }
//    }
//}
class TimberDebugTree(private val tag:String) : Timber.DebugTree() {
    override fun createStackElementTag(element: StackTraceElement): String {
        return tag
    }
}
object Nlog {
    private fun trance(msg: Any?): String {
        val element = Throwable().stackTrace[2]
        return "${element.fileName}[${element.lineNumber}]\n\t" + msg
    }

    fun d(msg: Any?) {
        Timber.d(trance(msg))
    }

    fun i(msg: String) {
        Timber.i(trance(msg))
    }

    fun w(msg: String) {
        Timber.w(trance(msg))
    }

    @SuppressLint("LogNotTimber")
    fun e(msg: String) {
        if (BuildConfig.DEBUG) {
            Timber.e(trance(msg))
        } else {
            Log.e("nah", msg)
        }
    }

    fun e(e: Throwable) {
        Timber.e(e)
    }
}