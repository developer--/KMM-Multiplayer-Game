package ge.dev.waroffingerskmm.android

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import ge.dev.waroffingerskmm.android.view.OnSwipeTouchListener
import ge.dev.waroffingerskmm.model.CarPosition
import ge.dev.waroffingerskmm.model.PositionsEnum
import ge.dev.waroffingerskmm.socket.Events
import ge.dev.waroffingerskmm.socket.SocketEventsProcessor
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.*

class CarCrashActivity : AppCompatActivity() {

  private val socketProcessor: SocketEventsProcessor by lazy { SocketEventsProcessor() }

  private var selectedPlayerId = PlayerConstants.PLAYER_1_ID
  private lateinit var imgCar1: View
  private lateinit var imgCar2: View
  private var screenWidth: Int = 0
  private var stoneTimer: Timer? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_car_crash)
    selectedPlayerId = intent.getIntExtra("SELECTED_PLAYER_ID", PlayerConstants.PLAYER_1_ID)
    fullscreen()

    imgCar1 = findViewById(R.id.ivPlayer1)
    imgCar2 = findViewById(R.id.ivPlayer2)

    socketProcessor.connect()

    imgCar1.post {
      screenWidth = window.decorView.display?.width ?: return@post
      val roadWidth = screenWidth / 3

      stoneTimer = Timer()
      stoneTimer?.schedule(object : TimerTask() {
        override fun run() {
          val isFirstPlayer = selectedPlayerId == PlayerConstants.PLAYER_1_ID
          val road = getRandomRoad(isFirstPlayer)
          runOnUiThread { generateStone(road, roadWidth) }
        }
      }, 0, 4000)

      val isFirstPlayer = selectedPlayerId == PlayerConstants.PLAYER_1_ID
      val targetCar = if (isFirstPlayer) imgCar1 else imgCar2
      findViewById<View>(R.id.overlay).setOnTouchListener(object : OnSwipeTouchListener(this) {
        override fun onSwipeLeft() {
          val leftMargin = if (isFirstPlayer) targetCar.left else targetCar.right
          val imgWidth = targetCar.width
          val centerX = screenWidth / 2
          val leftOffsetX = centerX - leftMargin - (imgWidth / 2)

          if (targetCar.translationX == leftOffsetX.toFloat() && targetCar.translationX != 0f) {
            sendChanged(selectedPlayerId, PositionsEnum.LEFT)
          } else {
            sendChanged(selectedPlayerId, PositionsEnum.CENTER)
          }
        }

        override fun onSwipeRight() {
          val leftMargin = if (isFirstPlayer) targetCar.left else targetCar.right
          val imgWidth = targetCar.width
          val centerX = screenWidth / 2
          val leftOffsetX = centerX - leftMargin - (imgWidth / 2)

          if (targetCar.translationX == leftOffsetX.toFloat() && targetCar.translationX != 0f) {
            sendChanged(selectedPlayerId, PositionsEnum.CENTER)
          } else {
            sendChanged(selectedPlayerId, PositionsEnum.RIGHT)
          }
        }
      })
    }

    onPositionChanged()
  }

  private fun getRandomRoad(isFirstPlayer: Boolean): Int {
    return if (isFirstPlayer) (1..2).random() else (2..3).random()
  }

  private fun sendChanged(playerId: Int, position: PositionsEnum) {
    val data = Json.encodeToString(CarPosition(playerId, position.pos))
    socketProcessor.sendData(Events.Request.ON_CAR_POSITION_CHANGED, data)
  }

  private fun onPositionChanged() {
    socketProcessor.onCarPositionReceived = {
      val carPos: CarPosition = Json.decodeFromString(it)
      animatePositionChange(carPos)
    }
  }

  private fun generateStone(road: Int, roadWidth: Int) {
    val stoneImg = findViewById<View>(R.id.ivStone1)
    stoneImg.translationX = (road - 1) * roadWidth.toFloat()
    stoneImg.translationY = -1f * stoneImg.height
    stoneImg.isVisible = true
    stoneImg.animate()
      .setDuration(3500L)
      .translationY(window.decorView.display?.height?.toFloat() ?: (0f + stoneImg.height * 5))
  }

  private fun animatePositionChange(carPos: CarPosition) {
    val isPlayer_1 = carPos.playerId == PlayerConstants.PLAYER_1_ID
    val targetView = if (carPos.playerId == PlayerConstants.PLAYER_1_ID) imgCar1 else imgCar2
    val position = PositionsEnum.values().find { it.pos == carPos.position } ?: return

    runOnUiThread {
      when (position) {
        PositionsEnum.CENTER,
        PositionsEnum.LEFT -> {
          val leftMargin = if(isPlayer_1) targetView.left else targetView.right
          val imgWidth = targetView.width
          val centerX = screenWidth / 2
          val leftOffsetX = centerX - leftMargin - (imgWidth / 2)

          val finalTranslationX =
            if (targetView.translationX == leftOffsetX.toFloat() && targetView.translationX != 0f) {
              0
            } else {
              leftOffsetX
            }
          targetView.animate()
            .translationX(finalTranslationX.toFloat())
            .start()
        }
        PositionsEnum.RIGHT -> {
          val leftMargin = if(isPlayer_1) targetView.left else targetView.right
          val imgWidth = targetView.width
          val centerX = screenWidth / 2
          val leftOffsetX = centerX - leftMargin - (imgWidth / 2)

          val rightCenterX = screenWidth - leftMargin * 2

          val finalTranslationX =
            if (targetView.translationX == leftOffsetX.toFloat() && targetView.translationX != 0f) {
              rightCenterX - imgWidth
            } else {
              leftOffsetX
            }

          targetView.animate()
            .translationX((finalTranslationX).toFloat())
            .start()
        }
      }
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    stoneTimer?.cancel()
    stoneTimer = null
  }
}