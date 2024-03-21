package ge.dev.waroffingerskmm.android.screens.selectplayer

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ge.dev.waroffingerskmm.android.PlayerConstants
import ge.dev.waroffingerskmm.android.app.utils.DeviceIdRetriever
import ge.dev.waroffingerskmm.android.screens.GameMode
import ge.dev.waroffingerskmm.model.PlayerInfo
import ge.dev.waroffingerskmm.model.SelectedPlayer
import ge.dev.waroffingerskmm.socket.Events
import ge.dev.waroffingerskmm.socket.SocketEventsProcessor
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * @Author: Jemo Mgebrishvili
 * @Date: 11.03.22
 */
class SelectPlayerViewModel(
  private val socketEventsProcessor: SocketEventsProcessor,
  private val deviceIdRetriever: DeviceIdRetriever
) : ViewModel() {

  private var selectedPlayerId: String? = null

  fun getSelectedPlayerId() = selectedPlayerId

  var firstPlayerState: Boolean by mutableStateOf(false)
    private set
  var secondPlayerState: Boolean by mutableStateOf(false)
    private set

  var startGame: Boolean by mutableStateOf(false)
    private set

  var gameMode = GameMode.None

  fun resetGameState() {
    startGame = false
    socketEventsProcessor.disconnect()
    viewModelScope.launch {
      delay(100)
      secondPlayerState = false
      firstPlayerState = false
    }
  }

  init {
    socketEventsProcessor.connect()
    socketEventsProcessor.onPlayerSelected = {
      try {
        println("onPlayerSelected $it")
        val data = Json.decodeFromString<SelectedPlayer>(it)
        val selectedCarId = data.id
        if (selectedCarId == PlayerConstants.PLAYER_1_ID.toString()) {
          firstPlayerState = true
        } else {
          secondPlayerState = true
        }
      } catch (e: Exception) {
        e.printStackTrace()
      }
    }

    socketEventsProcessor.onPlayersReady = {
      startGame = true
    }
  }

  fun firstPlayerSelected() {
    selectedPlayerId = PlayerConstants.PLAYER_1_ID.toString()
    playerSelected()

  }

  fun secondPlayerSelected() {
    selectedPlayerId = PlayerConstants.PLAYER_2_ID.toString()
    playerSelected()
  }

  private fun playerSelected() {
    if (selectedPlayerId == null) return
    val playerInfo = Json.encodeToString(
      PlayerInfo(
        playerId = selectedPlayerId!!,
        deviceId = deviceIdRetriever.getDeviceId()
      )
    )
    println("json_ $playerInfo")
    socketEventsProcessor.sendData(Events.Request.PLAYER_SELECTED, playerInfo)
  }
}