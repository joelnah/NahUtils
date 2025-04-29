package nah.prayer.nahutils.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import nah.prayer.nahutils.ui.test.Test1
import nah.prayer.nahutils.utils.LocalNavController
import nah.prayer.nahutils.utils.Screens

@Composable
fun StartScreen() {
    Test1()
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.SpaceAround
    ) {
        NavigationButton(Screens.STORAGE)
        NavigationButton(Screens.EXTENSION)
        NavigationButton(Screens.TEST)
    }
}

@Composable
fun NavigationButton(screen: String) {
    val navController = LocalNavController.current
    Button(onClick = { navController.navigate(screen) }) {
        Text("$screen Screen")
    }
}