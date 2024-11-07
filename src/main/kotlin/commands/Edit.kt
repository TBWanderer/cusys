package commands

import structs.Command
import structs.Config
import structs.Info

class Edit(
    name: String = "edit",
    description: String = "Edit info about user",
    level: Int = 0,
    manual: String = "Command not ready",
) : Command(name, description, level, manual) {
    override fun run(info: Info): Info {
        if (info.inputArgs.size > 2) {
            if (info.currentUser!!.level < Config().adminLevel) {
                println("Operation does not permitted")
                return info
            } else {
                println("Working on command")
            }
        } else if (info.inputArgs.size == 2) {
            println("Working on command")
        } else {
            println("Too few arguments")
        }
        return info
    }
}