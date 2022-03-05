package ge.dev.waroffingerskmm.android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class SelectGameActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_select_game)
    fullscreen()

    findViewById<View>(R.id.btnCrash).setOnClickListener {
      val intent = Intent(this, SelectCarCrashPlayerActivity::class.java)
      startActivity(intent)
    }

    findViewById<View>(R.id.btnRally).setOnClickListener {
      val intent = Intent(this, SelectPlayerActivity::class.java)
      startActivity(intent)
    }
  }
}