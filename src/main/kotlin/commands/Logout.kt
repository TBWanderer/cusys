package commands

import structs.Command
import structs.Info

class Logout(
    name: String = "logout",
    description: String = "End user session",
    level: Int = 0,
    manual: String = "logout -> you logged out",
) : Command(name, description, level, manual) {
    override fun run(info: Info) : Info {
        if (info.currentUser != null) {
            info.system.logOutUser(info.currentUser!!)
            info.currentUser = null
        } else {
            println("You are not authorized =(")
        }
        return info
    }
}