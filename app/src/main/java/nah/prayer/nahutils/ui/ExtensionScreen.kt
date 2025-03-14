package nah.prayer.nahutils.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import nah.prayer.library.composutils.rememberKeyboardVisibility
import nah.prayer.library.composutils.spPixed
import nah.prayer.library.extension.formatNumberWithCommas
import nah.prayer.library.extension.nonRippleClickable
import nah.prayer.library.extension.shimmerEffect
import nah.prayer.library.extension.toUnderline
import nah.prayer.library.log.Nlog

@Composable
fun ExtensionScreen() {
    var shimmerEffectText by remember { mutableStateOf("") }
    var nonRippleText by remember { mutableStateOf("I'm a Button") }
    val scope = rememberCoroutineScope()

    //It's testing the LaunchedEffect
    LaunchedEffect(Unit) {
        scope.launch {
            delay(3000)
            shimmerEffectText = "Hello, Mooney!"
        }
    }

    //It states that the keyboard is visible or not
    val keyboardVisible = rememberKeyboardVisibility()
    LaunchedEffect(keyboardVisible) {
        Nlog.i("keyboardVisible: $keyboardVisible")
    }



    Column(
        modifier = Modifier.padding(16.dp).fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {

        //shimmerEffect and spPixed
        Text(
            modifier = Modifier
                .widthIn(min = 150.dp)
                //It's used to show the shimmer effect when the text is empty.
                .shimmerEffect(isLoading = shimmerEffectText.isEmpty()),
            text = shimmerEffectText,
            style = MaterialTheme.typography.bodySmall,
            fontSize = 16.spPixed() //It's used as a fixed value.
        )

        //nonRippleClickable
        Text(
            text = nonRippleText,
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(color = Color.Cyan)
                .padding(16.dp)
                .nonRippleClickable {
                    nonRippleText = "remove the ripple effect from clickable"
                }

        )

        //formatNumberWithCommas and toUnderline
        Text(
            text = 123456789.formatNumberWithCommas().toUnderline(),
        )
    }



}