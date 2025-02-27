package nah.prayer.library.log

import timber.log.Timber

class LogManager {
    lateinit var tag: String
    fun setTag(isDebug:Boolean = false, tag:String, ){
        this.tag = tag
        if(isDebug) Timber.plant(TimberDebugTree(tag))
    }

    fun trance(msg: Any?, isPath: Boolean): String {
        return (if (isPath) buildPathMsg() else buildMsg()) + "\n" + msg.toString()
    }

    private fun getTraceElements(stackTrace: Array<StackTraceElement>): Int {
        val methodNames = Level.entries.map { it.name.first().lowercase() }
        var logIndex = -1
        //Nlog 클래스의 methodNames 메소드를 찾아서 해당 위치부터 Activity.kt 파일이 있는 곳까지만 출력
        for (i in stackTrace.indices) {
            if (methodNames.contains(stackTrace[i].methodName) && stackTrace[i].className.endsWith("Nlog")) {
                logIndex = i
                break
            }
        }
        return logIndex
    }

    private fun buildMsg(index: Int = 1): String {
        val stackTrace = Thread.currentThread().stackTrace
        val logIndex = getTraceElements(stackTrace)
        if (logIndex == -1) return "Unknown location"
        val directCaller = stackTrace[logIndex + index]
        return if (directCaller.fileName.contains("Nlog")) {
            buildMsg(index + 1)
        } else {
            "${directCaller.fileName}:${directCaller.lineNumber}"
        }
    }

    private fun buildPathMsg(): String {
        val stackTrace = Thread.currentThread().stackTrace

        val logIndex = getTraceElements(stackTrace)

        if (logIndex == -1) return "Unknown location"

        val relevantFrames = mutableListOf<StackTraceElement>()

        var startIndex = -1
        //Activity.kt 파일이 있는 곳까지만 출력
        for (i in (logIndex + 1) until stackTrace.size) {
            val element = stackTrace[i]
            if (element.fileName?.endsWith("Activity.kt") == true) {
                startIndex = i
                break
            }
        }

        //역순으로 출력
        if (startIndex != -1) {
            for (i in (logIndex + 1)..startIndex) {
                val element = stackTrace[i]
                if (element.fileName?.endsWith(".kt") == true) {
                    relevantFrames.add(element)
                }
            }
        }

        // -> 구분자 추가
        return relevantFrames.joinToString(" -> ") { frame ->
            "${frame.fileName}:${frame.lineNumber}"
        }
    }
}