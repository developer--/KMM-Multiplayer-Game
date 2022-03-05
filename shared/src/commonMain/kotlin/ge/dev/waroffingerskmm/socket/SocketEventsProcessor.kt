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
      onConnect?.invoke()
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
      it.printStackTrace()
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

    on(Events.Response.PLAYER_SELECTED.eventName) { data ->
      onPlayerSelected?.invoke(data)
    }
    on(Events.Response.ON_PLAYER_TAP_PROCESSED.eventName) { data ->
      onTapEventReceived?.invoke(data)
    }
    on(Events.Response.ON_CAR_POSITION_PROCESSED.eventName) { data ->
      onCarPositionReceived?.invoke(data)
    }
    on(Events.Response.PLAYERS_READY.eventName) { data ->
      onPlayersReady?.invoke()
    }
  }

  var onConnect: (() -> Unit)? = null
  var onTapEventReceived: ((String) -> Unit)? = null
  var onCarPositionReceived: ((String) -> Unit)? = null
  var onPlayerSelected: ((String) -> Unit)? = null
  var onPlayersReady:(() -> Unit)? = null

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