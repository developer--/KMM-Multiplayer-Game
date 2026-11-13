package ge.dev.waroffingerskmm.android.app.utils

import android.content.Context
import ge.dev.waroffingerskmm.android.getDeviceId

/**
 * @Author: Jemo Mgebrishvili
 * @Date: 11.03.22
 */
class DeviceIdRetrieverImpl(private val context: Context) : DeviceIdRetriever{
  override fun getDeviceId(): String {
    return context.getDeviceId()
  }
}