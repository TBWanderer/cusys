package structs

import Cusys
import java.security.MessageDigest

data class User(
    var nickname: String,
    var email: String,
    val hashedPassword: String,
    val level: Int,
    var isAuthorized: Boolean = false,
) {
    fun checkPassword(password: String): Boolean {
        return hashedPassword == hashPassword(hashPassword(password))
    }

    fun exists(system: Cusys) : Boolean {
        return system.getUserID(nickname) != null
    }

    private fun hashPassword(password: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hashedBytes = digest.digest(password.toByteArray())
        return hashedBytes.joinToString("") { String.format("%02x", it) }
    }
}