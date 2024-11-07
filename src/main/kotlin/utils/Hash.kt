package utils

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class Hash {
    fun string(data: String, algorithm: String = "SHA-256"): String? {
        try {
            val digest = MessageDigest.getInstance(algorithm)
            val hashedBytes = digest.digest(data.toByteArray())
            val hashString = hashedBytes.joinToString("") { String.format("%02x", it) }
            return hashString
        } catch (e: NoSuchAlgorithmException) {
            println("Algorithm $algorithm not found!")
            return null
        }
    }
}