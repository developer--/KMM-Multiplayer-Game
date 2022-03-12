package ge.dev.waroffingerskmm.android.app.di

import ge.dev.waroffingerskmm.android.app.utils.DeviceIdRetriever
import ge.dev.waroffingerskmm.android.app.utils.DeviceIdRetrieverImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 * @Author: Jemo Mgebrishvili
 * @Date: 11.03.22
 */
object UtilsModule {
  val module = module {
    single { DeviceIdRetrieverImpl(context = androidContext()) as DeviceIdRetriever}
  }
}