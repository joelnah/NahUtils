package nah.prayer.library.composutils

import android.graphics.Rect
import android.view.ViewTreeObserver
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalView

/**
 * Check if the keyboard is visible or not
 * */
@Composable
fun rememberKeyboardVisibility(): Boolean {
    val keyboardVisibility = remember { mutableStateOf(false) }

    val view = LocalView.current
    DisposableEffect(view) {
        val listener = ViewTreeObserver.OnGlobalLayoutListener {
            val rect = Rect()
            view.getWindowVisibleDisplayFrame(rect)
            val screenHeight = view.rootView.height
            val keyboardHeight = screenHeight - rect.bottom

            keyboardVisibility.value = keyboardHeight > screenHeight * 0.15
        }

        view.viewTreeObserver.addOnGlobalLayoutListener(listener)


        onDispose {
            view.viewTreeObserver.removeOnGlobalLayoutListener(listener)
        }
    }

    return keyboardVisibility.value
}
