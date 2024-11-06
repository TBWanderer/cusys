package commands

import structs.Command
import structs.Info

class Fetch(
    name: String = "fetch",
    description: String = "Fetching info about authorized  user",
    level: Int = -1
) : Command(name, description, level) {
    override fun run(info: Info): Info {
        if (info.currentUser != null) {
            val me = info.currentUser!!
            println("[Info]")
            println("| Name: ${me.firstName} ${me.lastName}")
            println("| Age: ${me.age} yo")
            println("| Email: ${me.email}")
            println("| Level: ${me.level}")
        } else {
            println("You are not authorized! Use 'login' to authorize")
        }
        return info
    }
}