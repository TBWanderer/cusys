package structs

import System

data class Info(
    val system: System,
    var currentUser: User?,
    val currentLevel: Int,
    val inputArgs: List<String>,
    val command: Command?
)
