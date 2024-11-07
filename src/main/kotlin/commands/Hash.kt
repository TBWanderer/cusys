package commands

import structs.Command
import structs.Info
import utils.Hash
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class Hash(
    name: String = "hash",
    description: String = "Hash functions to use",
    level: Int = -1,
    manual: String = "1. hash <data> -> <hashed-by-sha256-data>\n2. hash <algorithm> <data> -> <hashed-by-sha256-data>",
) : Command(name, description, level, manual) {
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

            val hash = Hash().string(data, algorithm)
            if (hash != null ) { println("Hashed string: $hash") }
        }
        return info
    }
}