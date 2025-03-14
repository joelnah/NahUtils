package nah.prayer.library.composutils

import android.content.Context
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


@Composable
fun Int.toDp() = with(LocalDensity.current) { toDp() }
fun Int.toDp(context: Context) = (this / context.resources.displayMetrics.density).toInt().dp

@Composable
fun Number.spPixed(): TextUnit {
    val density = LocalDensity.current
    val dp = this.toFloat().dp
    return with(density) { dp.toSp() }
}

