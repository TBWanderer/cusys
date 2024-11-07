package structs

abstract class Command(
     val name: String,
     val description: String,
     val level: Int,
     val manual: String, ) {
     abstract fun run(info: Info) : Info
}
