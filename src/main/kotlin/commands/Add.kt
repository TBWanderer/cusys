package commands

import utils.Get
import utils.Hash
import structs.Command
import structs.Config
import structs.Info
import structs.User
import java.util.*

class Add(
    name: String = "add",
    description: String = "Add something new",
    level: Int = Config().adminLevel,
    manual: String = "add [user] -> then enter form and new user creating finished",
) : Command(name, description, level, manual) {
    override fun run(info: Info) : Info {
        if (info.inputArgs.size == 2) {
            if (info.inputArgs[1] == "user") {
                val nickname = Get().str("Enter nickname: ").trim().lowercase(Locale.getDefault())
                val age = Get().int("Enter age: ", min = 0, max = 127)
                val email = Get().strCorrect("Enter email: ", Config().emailRegex).trim()
                val password = Get().str("Enter password for new user: ")
                var passwordAgain = Get().str("Enter password again: ")
                while (passwordAgain != password) {
                    passwordAgain = Get().str("Incorrect! Enter password again: ")
                }
                val userLevel = Get().int("Enter user level: ", min = 0, max = Config().adminLevel)
                val newUser = User(nickname, email, Hash().string(Hash().string(password)!!)!!, userLevel)
                info.system.addUser(newUser)
            } else {
                println("I can't do this =/")
            }
        } else if (info.inputArgs.size > 2) {
            println("There are too many arguments")
        } else {
            println("There are too few arguments")
        }
        return info
    }
}
