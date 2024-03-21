package ge.dev.waroffingerskmm.di

import ge.dev.waroffingerskmm.network.NetworkConfig
import ge.dev.waroffingerskmm.socket.SocketEventsProcessor
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.features.observer.*
import io.ktor.client.statement.*
import kotlinx.serialization.json.Json
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * @Author: Jemo Mgebrishvili
 * @Date: 11.03.22
 */
object NetworkModule {
  fun getNetworkModule(networkConfig: NetworkConfig): Module {
    return module {
      single {
        HttpClient(CIO) {
          ResponseObserver { response ->
            val status = response.status.value
            println("HTTP status: $status - ${response.request.url}")
          }
          install(JsonFeature) {
            val json: Json = kotlinx.serialization.json.Json {
              ignoreUnknownKeys = true
              coerceInputValues = true
              useArrayPolymorphism = true
            }
            serializer = KotlinxSerializer(json)
          }
          install(Logging) {
            logger = Logger.DEFAULT
            level = networkConfig.networkLogLevel.map()
          }
          install(HttpTimeout) {
            val timeout = 30_000L
            requestTimeoutMillis = timeout
            connectTimeoutMillis = timeout

          }
        }
      }

      single { SocketEventsProcessor() }
    }
  }
}