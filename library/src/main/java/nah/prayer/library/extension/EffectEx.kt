package nah.prayer.library.extension

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


fun Modifier.shimmerEffect(
    isLoading: Boolean,
    brushColor: Color = Color.LightGray
): Modifier = composed {
    val gradient = listOf(
        brushColor.copy(alpha = 0.9f), //darker grey (90% opacity)
        brushColor.copy(alpha = 0.3f), //lighter grey (30% opacity)
        brushColor.copy(alpha = 0.9f)
    )

    val transition = rememberInfiniteTransition("") // animate infinite times

    val translateAnimation = transition.animateFloat( //animate the transition
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000, // duration for the animation
                easing = FastOutLinearInEasing
            )
        ), label = ""
    )
    val brush = Brush.linearGradient(
        colors = gradient,
        start = Offset(200f, 200f),
        end = Offset(
            x = translateAnimation.value,
            y = translateAnimation.value
        )
    )

    if(isLoading){
        background(brush = brush, shape = RoundedCornerShape(8.dp))
    }else {
        this
    }
}