package ge.dev.waroffingerskmm.model

import kotlinx.serialization.Serializable

/**
 * @Author: Jemo Mgebrishvili
 * @Date: 05.03.22
 */
@Serializable
data class SelectedPlayer(val id: String, val selected: Boolean)