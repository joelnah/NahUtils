package nah.prayer.library

import android.content.Context
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
    private var isDebug = false

    fun init(context:Context, tag: String){
        isDebug = (context.applicationInfo.flags and android.content.pm.ApplicationInfo.FLAG_DEBUGGABLE) != 0
        setTag(tag)
    }

    fun setTag(tag:String, su:Boolean = false){
        if(isDebug || su) Timber.plant(TimberDebugTree(tag))
    }

    private fun trance(msg: Any?): String {
        val element = Throwable().stackTrace[2]
        return "${element.fileName}[${element.lineNumber}]\n\t" + msg
    }

    fun d(msg: Any?) {
        Timber.d(trance(msg))
    }

    fun d(tag:String, msg: Any?) {
        Timber.tag(tag).d(trance(msg))
    }

    fun i(msg: Any?) {
        Timber.i(trance(msg))
    }

    fun i(tag:String, msg: Any?) {
        Timber.tag(tag).i(trance(msg))
    }

    fun w(msg: Any?) {
        Timber.w(trance(msg))
    }

    fun w(tag:String, msg: Any?) {
        Timber.tag(tag).w(trance(msg))
    }

    fun e(msg: Any?) {
        Timber.e(trance(msg))
    }

    fun e(tag:String, msg: Any?) {
        Timber.tag(tag).e(trance(msg))
    }

    fun e(e: Throwable) {
        Timber.e(e)
    }
}