package commands

import structs.Info
import structs.Command

class Help(
    name: String = "help",
    description: String = "Show available commands",
    level: Int = -1,
    manual: String = "help -> list of commands",
) : Command(name, description, level, manual) {
    override fun run(info: Info) : Info {
        for (commandObject in info.system.commands) {
            if (commandObject.level <= info.currentLevel) {
                val someBeauty = 10
                var line = commandObject.name + " "
                for (temp in 1..someBeauty - commandObject.name.length) {
                    line += "."
                }
                line += " " + commandObject.description
                println(line)
            }
        }
        return info
    }
}