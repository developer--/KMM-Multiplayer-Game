package ge.dev.waroffingerskmm.android.app.di

import ge.dev.waroffingerskmm.android.screens.rally.RallyViewModel
import ge.dev.waroffingerskmm.android.screens.selectplayer.SelectPlayerViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * @Author: Jemo Mgebrishvili
 * @Date: 11.03.22
 */
object ViewModels {
  val module = module {
    viewModel { SelectPlayerViewModel(socketEventsProcessor = get(), deviceIdRetriever = get()) }
    viewModel { RallyViewModel(socketEventsProcessor = get()) }
  }
}