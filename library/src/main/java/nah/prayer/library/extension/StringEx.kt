package nah.prayer.library.extension

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.style.TextDecoration

/**
 * It adds underline to the string
 * */
fun String.toUnderline(): AnnotatedString {
    return AnnotatedString.Builder(this)
        .apply {
            addStyle(
                style = SpanStyle(textDecoration = TextDecoration.Underline),
                start = 0,
                end = length
            )
        }.toAnnotatedString()
}

/**
* It changes the "String" to a string with commas
* */
fun String.formatNumberWithCommas(): String {
    val value = this.trim()
    return if(value.containsNonDigit()) {
        value
    } else {
        value.toLong().formatNumberWithCommas()
    }
}

/**
 * Check if the string contains non-digit characters
 * */
fun String.containsNonDigit(): Boolean {
    return this.any { !it.isDigit() }
}