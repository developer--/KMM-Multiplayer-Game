package ge.dev.waroffingerskmm.network;

import io.ktor.client.features.logging.*

enum class KtorLogger {
  ALL,
  BODY,
  HEADERS,
  NONE;

  fun map(): LogLevel {
    return when (this) {
      ALL -> LogLevel.ALL
      BODY -> LogLevel.BODY
      HEADERS -> LogLevel.HEADERS
      NONE -> LogLevel.NONE
    }
  }
}