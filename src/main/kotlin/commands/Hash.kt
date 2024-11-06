package commands

import structs.Command
import structs.Info
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class Hash(
    name: String = "hash",
    description: String = "Hash functions to use",
    level: Int = -1
) : Command(name, description, level) {
    override fun run(info: Info): Info {
        if (info.inputArgs.size == 1) {
            println("Too few arguments")
        } else {
            var algorithm = "SHA-256"
            var data = info.inputArgs[1]
            if (info.inputArgs.size >= 3) {
                algorithm = info.inputArgs[1]
                data = info.inputArgs[2]
                if (info.inputArgs.size > 3) {
                    for (idx in 3..<info.inputArgs.size) {
                        data +=  " " + info.inputArgs[idx]
                    }
                }
            }
            println("Using hashing algorithm $algorithm")
            println("Hashing data: '$data'")

            try {
                val digest = MessageDigest.getInstance(algorithm)
                val hashedBytes = digest.digest(data.toByteArray())
                val hashString = hashedBytes.joinToString("") { String.format("%02x", it) }
                println("Hashed string: $hashString")
            } catch (e: NoSuchAlgorithmException) {
                println("Algorithm $algorithm not found!")
            }

        }
        return info
    }
}