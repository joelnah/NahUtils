package nah.prayer.library.extension

import androidx.compose.foundation.Indication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


/**
 * Remove ripple effect from clickable
 * */
fun Modifier.nonRippleClickable(onClick:()->Unit): Modifier = composed{
    singleClickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = null,
        onClick = onClick,
    )
}

/**
 * Single clickable with debounce time
 * */
fun Modifier.singleClickable(
    debounceTime: Long = 600L,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = MutableInteractionSource(),
    indication: Indication? = null,
    onClick: () -> Unit
) = composed {
    var lastClickTime by remember { mutableLongStateOf(0L) }
    var job by remember { mutableStateOf<Job?>(null) }
    val scope = rememberCoroutineScope()

    clickable(
        enabled = enabled,
        interactionSource = interactionSource,
        indication = indication,
    ) {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastClickTime >= debounceTime) {
            lastClickTime = currentTime
            job?.cancel()
            job = scope.launch {
                onClick()
                delay(debounceTime)
            }
        }
    }
}

/**
 * Set system status bar padding
 * */
fun Modifier.sysStatusBarPadding(): Modifier = composed {
    val view = LocalView.current
    val density = LocalDensity.current
    val windowInsets = ViewCompat.getRootWindowInsets(view)
    val statusBarHeightPx = windowInsets?.getInsets(WindowInsetsCompat.Type.statusBars())?.top ?: 0
    padding(top = with(density) { statusBarHeightPx.toDp() })
}