package ge.dev.waroffingerskmm.socket

/**
 * @Author: Jemo Mgebrishvili
 * @Date: 05.03.22
 */
object Events {
  enum class Request(val eventName: String) {
    ON_TAP("onTap"),
    PLAYER_SELECTED("PLAYER_SELECTED"),
    RESET_CURRENT_PLAYER("RESET_CURRENT_PLAYER"),
    ON_CAR_POSITION_CHANGED("ON_CAR_POSITION_CHANGED"),
  }

  enum class Response(val eventName: String) {
    ON_PLAYER_TAP_PROCESSED("onProcessed"),
    PLAYER_SELECTED("PLAYER_SELECTED"),
    ON_CAR_POSITION_PROCESSED("ON_CAR_POSITION_PROCESSED"),
    PLAYERS_READY("PLAYERS_READY")
  }
}