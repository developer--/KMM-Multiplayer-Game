package ge.dev.waroffingerskmm.android

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import ge.dev.waroffingerskmm.model.PlayerInfo
import ge.dev.waroffingerskmm.model.SelectedPlayer
import ge.dev.waroffingerskmm.socket.Events
import ge.dev.waroffingerskmm.socket.SocketEventsProcessor
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class SelectCarCrashPlayerActivity : AppCompatActivity() {
  private val socketProcessor: SocketEventsProcessor by lazy { SocketEventsProcessor() }
  private var selectedPlayerId: Int? = null
  private lateinit var img1: View
  private lateinit var img2: View
  private lateinit var tvStatus1: TextView
  private lateinit var tvStatus2: TextView

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_select_player)
    fullscreen()
    img1 = findViewById(R.id.ivPlayer1)
    img2 = findViewById(R.id.ivPlayer2)
    tvStatus1 = findViewById(R.id.tvOnlineStatus1)
    tvStatus2 = findViewById(R.id.tvOnlineStatus2)
    img1.isEnabled = true
    img2.isEnabled = true

    img1.setOnClickListener {
      img2.isEnabled = false
      playerSelected(PlayerConstants.PLAYER_1_ID)
    }

    img2.setOnClickListener {
      img1.isEnabled = false
      playerSelected(PlayerConstants.PLAYER_2_ID)
    }

    socketProcessor.connect()
    socketProcessor.onConnect = {
      socketProcessor.sendData(Events.Request.RESET_CURRENT_PLAYER, getDeviceId())
    }
    onPlayerSelectedEvent()
    onPlayersReady()
  }

  private fun onPlayerSelectedEvent() {
    socketProcessor.onPlayerSelected =  {
      try {
        val data = Json.decodeFromString<SelectedPlayer>(it)
        println("onMessage ${it}")
        val alpha = if (data.selected) 0.7f else 1f
        val bgRes = if (data.selected) R.drawable.green_drawable else R.drawable.red_drawable
        if (data.id == PlayerConstants.PLAYER_1_ID.toString()) {
          img1.alpha = alpha
          img1.isEnabled = !data.selected
          tvStatus1.setBackgroundResource(bgRes)
        } else {
          img2.alpha = alpha
          img2.isEnabled = !data.selected
          tvStatus2.setBackgroundResource(bgRes)
        }
      } catch (e: Exception) {
        e.printStackTrace()
      }
    }
  }

  private var isStarting: Boolean = false
  private fun onPlayersReady() {
    socketProcessor.onPlayersReady = {
      if (selectedPlayerId != null && !isStarting) {
        isStarting = true
        socketProcessor.disconnect()
        runOnUiThread {
          goToGame(selectedPlayerId!!, img1, img2)
        }
      }
    }
  }

  private fun playerSelected(playerId: Int) {
    selectedPlayerId = playerId
    val playerInfo =
      Json.encodeToString(
        PlayerInfo(
          playerId = playerId.toString(),
          deviceId = getDeviceId()
        )
      )
    println("json_ ${playerInfo}")
    socketProcessor.sendData(Events.Request.PLAYER_SELECTED, playerInfo)
  }

  private fun goToGame(playerId: Int, v1: View, v2: View) {
    println("__START_GAME")
    val intent = Intent(this, CarCrashActivity::class.java)
    intent.putExtra("SELECTED_PLAYER_ID", playerId)
    val element1 = androidx.core.util.Pair(v1, v1.transitionName)
    val element2 = androidx.core.util.Pair(v2, v2.transitionName)
    val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
      this, element1, element2,
    )
    startActivity(intent, options.toBundle())
    finish()
  }
}