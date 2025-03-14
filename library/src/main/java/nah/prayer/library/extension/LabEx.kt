package nah.prayer.library.extension


import android.content.Context
import android.icu.text.NumberFormat
import android.os.Build
import android.view.MotionEvent
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Indication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.util.Locale


object LabEx{
    fun <T> debounce(
        coroutineScope: CoroutineScope,
        waitMs: Long = 300L,
        destinationFunction: (T) -> Unit
    ): (T) -> Unit {
        var debounceJob: Job? = null
        return { param: T ->
            debounceJob?.cancel()
            debounceJob = coroutineScope.launch {
                delay(waitMs)
                destinationFunction(param)
            }
        }
    }

    fun <T> Flow<T>.throttleFirst(windowDuration: Long): Flow<T> = flow {
        var lastEmissionTime = 0L
        collect { upstream ->
            val currentTime = System.currentTimeMillis()
            if (currentTime - lastEmissionTime > windowDuration) {
                lastEmissionTime = currentTime
                emit(upstream)
            }
        }
    }


    fun <T> throttleFirst(
        skipMs: Long = 300L,
        coroutineScope: CoroutineScope,
        destinationFunction: (T) -> Unit
    ): (T) -> Unit {
        var throttleJob: Job? = null
        return { param: T ->
            if (throttleJob?.isCompleted != false) {
                throttleJob = coroutineScope.launch {
                    destinationFunction(param)
                    delay(skipMs)
                }
            }
        }
    }

    fun <T> throttleLatest(
        intervalMs: Long = 300L,
        coroutineScope: CoroutineScope,
        destinationFunction: (T) -> Unit
    ): (T) -> Unit {
        var throttleJob: Job? = null
        var latestParam: T
        return { param: T ->
            latestParam = param
            if (throttleJob?.isCompleted != false) {
                throttleJob = coroutineScope.launch {
                    delay(intervalMs)
                    latestParam.let(destinationFunction)
                }
            }
        }
    }
}
