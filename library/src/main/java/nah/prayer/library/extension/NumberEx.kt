package nah.prayer.library.extension

import android.icu.text.NumberFormat
import java.util.Locale

/**
 * It changes the number to a string with commas
 * */
fun Number.formatNumberWithCommas(): String {
    return NumberFormat.getNumberInstance(Locale.getDefault()).format(this)
}