package structs

data class Config(
    val adminLevel: Int = 7,
    val emailRegex: Regex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"),
    val dbUrl: String = "jdbc:sqlite:db.db"
)
