package ge.dev.waroffingerskmm.android.screens.rally

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import ge.dev.waroffingerskmm.model.SelectionResult
import ge.dev.waroffingerskmm.socket.Events
import ge.dev.waroffingerskmm.socket.SocketEventsProcessor
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

/**
 * @Author: Jemo Mgebrishvili
 * @Date: 11.03.22
 */
class RallyViewModel(private val socketEventsProcessor: SocketEventsProcessor) : ViewModel() {
  var offsetCar1 by mutableStateOf(0f)
    private set

  var offsetCar2 by mutableStateOf(0f)
    private set

  var selectedCarId: String? = null

  init {
    socketEventsProcessor.connect()
    socketEventsProcessor.onTapEventReceived = { data ->
      String
      val result = Json.decodeFromString<SelectionResult>(data)
      val isP1 = result.p1
      if (isP1) {
        offsetCar1 += 5.dp.value * -1
      } else {
        offsetCar2 += 5.dp.value * -1
      }
    }
  }

  fun onTap() {
    selectedCarId?.let {
      socketEventsProcessor.sendData(Events.Request.ON_TAP, it)
    }
  }

}