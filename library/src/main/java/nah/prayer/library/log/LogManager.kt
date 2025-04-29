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

        // logIndex 다음부터 Activity.kt 파일이 나올 때까지의 .kt 파일들을 수집
        for (i in (logIndex + 1) until stackTrace.size) {
            val element = stackTrace[i]
            if (element.fileName?.endsWith(".kt") == true) {
                relevantFrames.add(element)
                // Activity.kt를 찾았다면 해당 프레임까지 포함하고 중단
                if (element.fileName?.endsWith("Activity.kt") == true) {
                    break
                }
            }
        }

        // 스택 트레이스는 가장 최신 호출이 앞에 오므로, 호출 순서대로 표시하려면 뒤집을 필요 없음
        // 각 프레임을 "파일명:라인번호" 형식으로 변환하고 " -> " 구분자로 연결
        return relevantFrames.joinToString(" -> ") { frame ->
            "${frame.fileName}:${frame.lineNumber}"
        }
    }
}