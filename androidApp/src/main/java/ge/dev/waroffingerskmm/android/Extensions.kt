package ge.dev.waroffingerskmm.android

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Build
import android.provider.Settings
import android.view.WindowInsets
import android.view.WindowManager

/**
 * @Author: Jemo Mgebrishvili
 * @Date: 03.03.22
 */
fun Activity.fullscreen() {
  window.setFlags(
    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
    WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
  );
  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
    window.insetsController?.hide(WindowInsets.Type.statusBars())
  } else {
    @Suppress("DEPRECATION")
    window.setFlags(
      WindowManager.LayoutParams.FLAG_FULLSCREEN,
      WindowManager.LayoutParams.FLAG_FULLSCREEN
    )
  }
}

@SuppressLint("HardwareIds")
fun Context.getDeviceId(): String {
  return Settings.Secure.getString(
    contentResolver,
    Settings.Secure.ANDROID_ID
  )
}