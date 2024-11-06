import commands.*
import utils.Get
import structs.Config
import structs.Info
import structs.User

class App {
    fun run() {
        var system = System(Config().dbUrl)
        val get = Get()

        var currentUser: User? = null

        system.addCommand(Help(), Hash(), Fetch(), Login(), Logout(), Add(), Exit())

        val admin = User("General", "Admin", -1, "admin@system.com", "39278de48feed8cbb96fbd597ed7dfab714d839c62922df79f55292941143c71", Config().adminLevel)
        system.addUser(admin)

        while (true) {
            var prompt = "> "
            if (currentUser != null) {
                prompt = "$ "
                if (currentUser.level >= Config().adminLevel) {
                    prompt = "# "
                }
            }

            val inputArgs = get.str(prompt).split(" ")
            val command = system.getCommand(inputArgs[0])

            if (command != null) {
                var currentLevel = -1
                if (currentUser != null) {
                    currentLevel = currentUser.level
                }

                if (currentLevel >= command.level) {
                    var info = Info(
                        system, currentUser, currentLevel, inputArgs, command
                    )
                    info = command.run(info)
                    system = info.system
                    currentUser = info.currentUser
                } else {
                    println("Operation does not permitted")
                }
            } else {
                println("This command does not exists")
            }
        }
    }
}

