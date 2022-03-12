package ge.dev.waroffingerskmm.android.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ge.dev.waroffingerskmm.android.screens.crashout.CrashOutModeScreen
import ge.dev.waroffingerskmm.android.screens.gamemode.GameModeScreen
import ge.dev.waroffingerskmm.android.screens.rally.RallyModeScreen
import ge.dev.waroffingerskmm.android.screens.selectplayer.SelectPlayerScreen

/**
 * @Author: Jemo Mgebrishvili
 * @Date: 11.03.22
 */

@Composable
fun MainApp() {
  val navController = rememberNavController()
  NavigationComponent(navController = navController)
}

@Composable
fun NavigationComponent(navController: NavHostController) {
  NavHost(
    navController = navController,
    startDestination = AppScreens.SelectGameMode.name
  ) {
    composable(AppScreens.SelectGameMode.name) {
      GameModeScreen(navController)
    }
    composable(
      route = AppScreens.SelectPlayer.name.plus("/{${RouteKeys.GAME_MODE}}"),
      arguments = listOf(navArgument(RouteKeys.GAME_MODE) { type = NavType.StringType })
    ) {
      val gameModeStr: String = it.arguments?.getString(RouteKeys.GAME_MODE) ?: return@composable
      val gameMode = GameMode.valueOf(gameModeStr)
      SelectPlayerScreen(navController, gameMode)
    }
    composable(
      route = AppScreens.RallyScreen.name.plus("/{${RouteKeys.SELECTED_PLAYER_ID}}"),
      arguments = listOf(navArgument(RouteKeys.SELECTED_PLAYER_ID) { type = NavType.StringType })
    ) {
      val playerId = it.arguments?.getString(RouteKeys.SELECTED_PLAYER_ID) ?: return@composable
      RallyModeScreen(playerId)
    }
    composable(AppScreens.CrashOutScreen.name) {
      CrashOutModeScreen()
    }
  }
}

object RouteKeys {
  const val GAME_MODE = "GAME_MODE"
  const val SELECTED_PLAYER_ID = "SELECTED_PLAYER_ID"
}

