package ge.dev.waroffingerskmm.android.screens

import androidx.compose.runtime.Composable
import ge.dev.waroffingerskmm.android.screens.gamemode.GameModeScreen

/**
 * @Author: Jemo Mgebrishvili
 * @Date: 11.03.22
 */
enum class AppScreens() {
  SelectGameMode,
  SelectPlayer,
  RallyScreen,
  CrashOutScreen;


  companion object {
    fun fromRoute(route: String?): AppScreens =
      when (route?.substringBefore("/")) {
        SelectGameMode.name -> SelectGameMode
        SelectPlayer.name -> SelectPlayer
        null -> SelectGameMode
        else -> throw IllegalArgumentException("Route $route is not recognized.")
      }
  }
}
