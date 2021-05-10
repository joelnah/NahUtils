package nah.prayer.library

import android.annotation.SuppressLint
import android.os.Build
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

object TimeUtil {

    /**
     * 타임스탬프를 데이터포멧으로 변경
     * @param current_date 20160817104517 null=현재 시간
     * @param pattern 패턴
     * HH : 0 -23
     * kk : 1 - 24
     * KK : 0 -11
     * hh : 1 - 12
     * yyyy.M.d (E) HH:mm
     * yyyy.M.d a hh:mm
     */
    @SuppressLint("DefaultLocale")
    fun dateToDate(current_date: Any?, pattern: String?): String {
        try {
            val timeStamp = toTimeStamp(current_date)
            if (timeStamp != null) {

                //            String[] pattern = {"yyyy년 M월 d일",""};
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timeStamp),
                        ZoneId.of("Asia/Seoul"))
                    return localDateTime.format(DateTimeFormatter.ofPattern(pattern, Locale.KOREA))
//                return localDateTime.format(DateTimeFormatter.ofPattern(pattern, Locale.KOREA))
                } else {
                    val sdfNow = SimpleDateFormat(pattern, Locale.KOREA)
//                val sdfNow = SimpleDateFormat(pattern, Locale.KOREA)
                    sdfNow.timeZone = TimeZone.getTimeZone("Asia/Seoul")
                    try {
                        return sdfNow.format(Date(timeStamp))
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
            else return ""
        } catch (e: NumberFormatException) {
            println(e.message)
        }
        return ""
    }

    fun dateToDate(pattern: String?): String {
        return dateToDate(null, pattern)
    }

    fun toTimeStamp(current_date: Any?) = run {
        when (current_date) {
            is String -> {
                if (current_date.isNotEmpty()) {
                    val sdf = SimpleDateFormat("yyyyMMddHHmmss")

                    var date = reg.replace(current_date, "")
                    if (date.length == 6) {
                        date += "01000000"
                    }else if (date.length == 8) {
                        date += "000000"
                    }

                    if(date.length==14){
                        sdf.parse(date).time
                    }else{
                        System.currentTimeMillis()
                    }


                } else null
            }
            is Long -> {
                current_date
            }
            else -> {
                System.currentTimeMillis()
            }
        }
    }
    private val reg = Regex("[^0-9]")
}