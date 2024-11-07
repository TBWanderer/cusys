package commands

import kotlin.system.exitProcess
import structs.Command
import structs.Info

class Exit(
    name: String = "exit",
    description: String = "Exiting app",
    level: Int = -1,
    manual: String = "'exit' -> closing app. IDK why I must to write this manual",
) : Command(name, description, level, manual) {
    override fun run(info: Info): Info {
        if (info.currentUser != null) {
            info.system.logOutUser(info.currentUser!!)
        }
        exitProcess(0)
    }
}