package utils

import java.security.MessageDigest

class Hash {
    fun string(data: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hashedBytes = digest.digest(data.toByteArray())
        return hashedBytes.joinToString("") { String.format("%02x", it) }
    }
}