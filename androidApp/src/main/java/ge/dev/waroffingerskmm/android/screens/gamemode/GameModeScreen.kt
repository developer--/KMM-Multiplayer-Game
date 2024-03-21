package ge.dev.waroffingerskmm.android.screens.gamemode

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import ge.dev.waroffingerskmm.android.screens.AppScreens
import ge.dev.waroffingerskmm.android.screens.GameMode

/**
 * @Author: Jemo Mgebrishvili
 * @Date: 11.03.22
 */

@Composable
fun GameModeScreen(navController: NavController) {
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .fillMaxHeight(),
    contentAlignment = Alignment.Center
  ) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
      Text("Select Game")
      ButtonComponent("Rally") {
        navController.navigate(AppScreens.SelectPlayer.name.plus("/${GameMode.Rally.name}"))
      }
      Spacer(modifier = Modifier.width(8.dp))
      ButtonComponent("Crash Out") {
        navController.navigate(AppScreens.SelectPlayer.name.plus("/${GameMode.CrashOut.name}"))
      }
    }
  }
}

@Composable
fun ButtonComponent(title: String, onClick: (() -> Unit)) {
  Button(onClick = onClick, modifier = Modifier.width(150.dp)) {
    Text(title)
  }
}