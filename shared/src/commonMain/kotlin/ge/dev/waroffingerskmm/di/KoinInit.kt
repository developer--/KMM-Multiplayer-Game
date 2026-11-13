package ge.dev.waroffingerskmm.di

import ge.dev.waroffingerskmm.network.NetworkConfig
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

/**
 * @Author: Jemo Mgebrishvili
 * @Date: 11.03.22
 */

fun initKoin(appDeclaration: KoinAppDeclaration = {}, networkConfig: NetworkConfig) = startKoin {
  appDeclaration()
  modules(
    NetworkModule.getNetworkModule(networkConfig),
  )
}