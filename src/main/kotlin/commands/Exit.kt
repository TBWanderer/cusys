package commands

import kotlin.system.exitProcess
import structs.Command
import structs.Info

class Exit(
    name: String = "exit",
    description: String = "Exiting app",
    level: Int = -1
) : Command(name, description, level) {
    override fun run(info: Info): Info {
        if (info.currentUser != null) {
            info.system.logOutUser(info.currentUser!!)
        }
        exitProcess(0)
    }
}