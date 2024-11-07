package commands

import utils.Get
import structs.Command
import structs.Info
import java.util.*

class Login(
    name: String = "login",
    description: String = "Authenticate user access",
    level: Int = -1,
    manual: String = "login -> fill the form and you logged in",
) : Command(name, description, level, manual) {
    override fun run(info: Info) : Info {
        if (info.currentUser != null) {
            println("Firstly log out from account")
        } else {
            val nickname = Get().str("Enter nickname: ").lowercase(Locale.getDefault())
            val password = Get().str("Enter password: ")
            info.currentUser = info.system.logInUser(nickname, password)
        }
        return info
    }
}