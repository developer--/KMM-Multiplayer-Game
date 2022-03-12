package ge.dev.waroffingerskmm.android.screens.selectplayer

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ge.dev.waroffingerskmm.android.R
import ge.dev.waroffingerskmm.android.screens.AppScreens
import ge.dev.waroffingerskmm.android.screens.GameMode
import org.koin.androidx.compose.getViewModel

/**
 * @Author: Jemo Mgebrishvili
 * @Date: 11.03.22
 */

@Composable
fun SelectPlayerScreen(navController: NavController, gameMode: GameMode) {
  val vm: SelectPlayerViewModel = getViewModel()
  vm.gameMode = gameMode
  val p1Color = if (vm.firstPlayerState) Color.Green else Color.Red
  val p2Color = if (vm.secondPlayerState) Color.Green else Color.Red
  if (vm.startGame) {
    when (gameMode) {
      GameMode.CrashOut -> {
        navController.navigate(AppScreens.CrashOutScreen.name.plus("/${vm.getSelectedPlayerId()}"))
      }
      GameMode.Rally -> {
        navController.navigate(AppScreens.RallyScreen.name.plus("/${vm.getSelectedPlayerId()}"))
      }
      else -> {}
    }
    vm.resetGameState()
  }

  Box(
    modifier = Modifier
      .fillMaxWidth()
      .fillMaxHeight(),
    contentAlignment = Alignment.Center
  ) {
    Column() {
      Row() {
        Text(text = "P1")
        Spacer(modifier = Modifier.width(5.dp))
        PlayerStatusShape(p1Color)
        CarImage(R.drawable.red_car) {
          vm.firstPlayerSelected()
        }
      }
      Spacer(modifier = Modifier.height(20.dp))
      Row() {
        Text(text = "P2")
        Spacer(modifier = Modifier.width(5.dp))
        PlayerStatusShape(p2Color)
        CarImage(R.drawable.car_yellow) {
          vm.secondPlayerSelected()
        }
      }
    }
  }

}


@Composable
fun CarImage(@DrawableRes drawableRes: Int, onClick: () -> Unit) {
  Image(
    painter = painterResource(drawableRes),
    contentDescription = "car image",
    modifier = Modifier
      .size(150.dp)
      .clip(CircleShape)
      .clickable(onClick = onClick)
      .border(2.dp, MaterialTheme.colors.secondary, CircleShape)
  )
}


@Composable
fun PlayerStatusShape(color: Color) {
  Box(
    modifier = Modifier
      .size(10.dp)
      .clip(CircleShape)
      .background(color)
  )
}