package ge.dev.waroffingerskmm.model

import kotlinx.serialization.Serializable

@Serializable
data class CarPosition(val playerId: Int, val position: Int)