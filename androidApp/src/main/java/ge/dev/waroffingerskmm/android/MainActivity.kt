package ge.dev.waroffingerskmm.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import ge.dev.waroffingerskmm.socket.Events
import ge.dev.waroffingerskmm.socket.SocketEventsProcessor


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tv: TextView = findViewById(R.id.text_view)

        val socketEventsProcessor = SocketEventsProcessor()
        socketEventsProcessor.connect()
        socketEventsProcessor.sendData(Events.Request.ON_TAP, "Hello from Android")
        socketEventsProcessor.onTapEventReceived = {
            runOnUiThread {
                tv.text = it
            }
        }
    }
}
