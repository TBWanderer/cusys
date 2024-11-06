package commands

import utils.Get
import structs.Command
import structs.Info

class Login(
    name: String = "login",
    description: String = "Authenticate user access",
    level: Int = -1
) : Command(name, description, level) {
    override fun run(info: Info) : Info {
        if (info.currentUser != null) {
            println("Firstly log out from account")
        } else {
            val email = Get().str("Enter email: ")
            val password = Get().str("Enter password: ")
            info.currentUser = info.system.logInUser(email, password)
        }
        return info
    }
}