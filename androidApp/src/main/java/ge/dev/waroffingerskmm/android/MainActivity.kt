package ge.dev.waroffingerskmm.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.view.isVisible
import ge.dev.waroffingerskmm.model.SelectionResult
import ge.dev.waroffingerskmm.socket.Events
import ge.dev.waroffingerskmm.socket.SocketEventsProcessor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.json.JSONObject


class MainActivity : AppCompatActivity() {

    private var progress1 = 0
    private var progress2 = 0

    private val socketProcessor: SocketEventsProcessor by lazy { SocketEventsProcessor() }

    private fun startTimer() {
        val timerView = findViewById<TextView>(R.id.tvTimer)
        val startButton = findViewById<View>(R.id.btnTap)
        timerView.isVisible = true
        CoroutineScope(Dispatchers.Main).launch {
            var k = 3
            while (k != 0) {
                timerView.text = k.toString()
                delay(1000)
                k--
            }
            startButton.isEnabled = true
            timerView.isVisible = false
        }
    }

    private fun animateRoad() {
        val imgRoad1 = findViewById<View>(R.id.ivRoad1)
        val imgRoad2 = findViewById<View>(R.id.ivRoad2)
        fun showRoad() {
            imgRoad1.animate()
                .translationY(0f)
                .setDuration(500)
                .withEndAction {
                    startTimer()
                }
                .start()
            imgRoad2.animate()
                .setDuration(500)
                .translationY(0f)
                .start()
        }
        imgRoad1.postDelayed({
            val screenHeight = window.decorView.display?.height ?: 0
            imgRoad1.animate()
                .withEndAction {
                    imgRoad1.isVisible = true
                    imgRoad2.isVisible = true
                    showRoad()
                }
                .translationY(screenHeight.toFloat())
                .start()
            imgRoad2.animate()
                .translationY(screenHeight.toFloat())
                .start()
        }, 300)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fullscreen()
        socketProcessor.connect()
        val carId = intent.getIntExtra("SELECTED_PLAYER_ID", PlayerConstants.PLAYER_1_ID)
        val screenHeight = window.decorView.display?.height ?: 1000
        val finishTap = 100
        val prog = screenHeight / finishTap

        animateRoad()
        val imgP1 = findViewById<View>(R.id.ivPlayer1)
        val imgP2 = findViewById<View>(R.id.ivPlayer2)

        socketProcessor.onTapEventReceived = { data -> String
            val result = Json.decodeFromString<SelectionResult>(data)
            val isP1 = result.p1
            if (isP1) {
                progress1 += prog
                imgP1.translationY = progress1.toFloat() * -1
            } else {
                progress2 += prog
                imgP2.translationY = progress2.toFloat() * -1
            }
        }

        findViewById<View>(R.id.btnTap).setOnClickListener {
            socketProcessor.sendData(Events.Request.ON_TAP, carId.toString())
        }
    }
}