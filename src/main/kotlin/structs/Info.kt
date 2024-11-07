package structs

import Cusys

data class Info(
    val system: Cusys,
    var currentUser: User? = null,
    val currentLevel: Int = -1,
    val inputArgs: List<String>,
    val command: Command?
)
