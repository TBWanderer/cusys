package structs

import java.security.MessageDigest

data class User(
    var firstName: String,
    var lastName: String,
    var age: Int,
    var email: String,
    val hashedPassword: String,
    val level: Int,
    var isAuthorized: Boolean = false,
) {
    fun checkPassword(password: String): Boolean {
        return hashedPassword == hashPassword(hashPassword(password))
    }

    private fun hashPassword(password: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hashedBytes = digest.digest(password.toByteArray())
        return hashedBytes.joinToString("") { String.format("%02x", it) }
    }
}