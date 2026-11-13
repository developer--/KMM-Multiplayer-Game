package ge.dev.waroffingerskmm.android.app

import android.app.Application
import ge.dev.waroffingerskmm.android.app.di.UtilsModule
import ge.dev.waroffingerskmm.android.app.di.ViewModels
import ge.dev.waroffingerskmm.di.initKoin
import ge.dev.waroffingerskmm.network.NetworkConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level

/**
 * @Author: Jemo Mgebrishvili
 * @Date: 11.03.22
 */
class MyApplication : Application() {
  override fun onCreate() {
    super.onCreate()
    initKoin(appDeclaration = {
      androidLogger(Level.INFO)
      androidContext(this@MyApplication)
      modules(
        ViewModels.module,
        UtilsModule.module
      )
    }, NetworkConfig(baseUrl = ""))
  }
}