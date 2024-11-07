package commands

import structs.Command
import structs.Info
import utils.Say
import java.util.Locale

class Man(
    name: String = "man",
    description: String = "Getting manual",
    level: Int = -1,
    manual: String = "man <command1> <command2> <command3> ... -> get commands manual (howto use)",
    ) : Command(name, description, level, manual)
    {
        override fun run(info: Info): Info {
            for (commandIdx in 1..<info.inputArgs.size) {
                val commandName = info.inputArgs[commandIdx]
                println("[${commandName.uppercase(Locale.getDefault())}]")

                val commandObj = info.system.getCommand(commandName)
                if (commandObj != null) {
                    Say().line("Usage:", level = 1)
                    for (line in commandObj.manual.split("\n")) {
                        Say().line(line, level = 2)
                    }
                } else {
                    Say().line("Command not found!", level = 1)
                }
            }
            return info
        }
    }