package ge.dev.waroffingerskmm.socket

import dev.icerock.moko.socket.Socket
import dev.icerock.moko.socket.SocketEvent
import dev.icerock.moko.socket.SocketOptions

/**
 * @Author: Jemo Mgebrishvili
 * @Date: 05.03.22
 */

class SocketEventsProcessor {
  private val socket = Socket(
    endpoint = "http://192.168.0.102:9092",
    config = SocketOptions(
      queryParams = null,
      transport = SocketOptions.Transport.WEBSOCKET
    )
  ) {
    on(SocketEvent.Connect) {
      println("__SOCKET___ connect")
    }

    on(SocketEvent.Connecting) {
      println("__SOCKET___ connecting")
    }

    on(SocketEvent.Disconnect) {
      println("__SOCKET___ disconnect")
    }

    on(SocketEvent.Error) {
      println("__SOCKET___ error ${(it as Exception).stackTraceToString()}")
      (it as Exception).printStackTrace()
    }

    on(SocketEvent.Reconnect) {
      println("__SOCKET___ reconnect")
    }

    on(SocketEvent.ReconnectAttempt) {
      println("__SOCKET___ reconnect attempt $it")
    }

    on(SocketEvent.Ping) {
      println("__SOCKET___ ping")
    }

    on(SocketEvent.Pong) {
      println("__SOCKET___ pong")
    }

    on(Events.Response.ON_PLAYER_TAP_PROCESSED.eventName) { data ->
      onTapEventReceived?.invoke(data)
      println("__SOCKET___ response __ $data")
    }
  }

  var onTapEventReceived: ((String) -> Unit)? = null

  fun connect() {
    socket.connect()
  }

  fun sendData(eventRequest: Events.Request, data: String) {
    socket.emit(eventRequest.eventName, data)
  }

  fun disconnect() {
    socket.disconnect()
  }
}