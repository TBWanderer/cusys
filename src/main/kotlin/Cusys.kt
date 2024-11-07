import structs.Command
import structs.User

import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.ResultSet

class Cusys(private val dbUrl: String) {
    private val connection: Connection = DriverManager.getConnection(dbUrl)
    val commands = mutableListOf<Command>()

    init {
        // Initialize the database (create tables if they don't exist)
        connection.createStatement().executeUpdate(
            """
            CREATE TABLE IF NOT EXISTS users (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nickname TEXT NOT NULL,
                email TEXT UNIQUE NOT NULL,
                password TEXT NOT NULL,
                level INTEGER NOT NULL DEFAULT 0,
                is_authorized BOOLEAN NOT NULL DEFAULT 0
            );
            """
        )
    }

    fun addUser(user: User) {
        val statement: PreparedStatement = connection.prepareStatement(
            "INSERT INTO users (nickname, email, password, level) VALUES (?, ?, ?, ?)",
            PreparedStatement.RETURN_GENERATED_KEYS
        )
        statement.setString(1, user.nickname)
        statement.setString(2, user.email)
        statement.setString(3, user.hashedPassword)
        statement.setInt(4, user.level)

        try {
            statement.executeUpdate()
            println("User ${user.nickname} added to the System")
        } catch (e: Exception) {
            println("Error adding user: ${e.message}")
        }
    }

    fun getUserID(nickname: String): Int? {
        val statement: PreparedStatement = connection.prepareStatement("SELECT id FROM users WHERE nickname = ?")
        statement.setString(1, nickname)
        val resultSet: ResultSet = statement.executeQuery()

        return if (resultSet.next()) {
            resultSet.getInt("id")
        } else {
            null
        }
    }

    fun getUserByID(userID: Int) : User? {
        val statement: PreparedStatement = connection.prepareStatement("SELECT nickname, email, password, level, is_authorized FROM users WHERE id = ?")
        statement.setInt(1, userID)
        val resultSet: ResultSet = statement.executeQuery()

        return if (resultSet.next()) {
            User(
                nickname = resultSet.getString("nickname"),
                email = resultSet.getString("email"),
                hashedPassword = resultSet.getString("password"),
                level = resultSet.getInt("level"),
                isAuthorized = resultSet.getBoolean("is_authorized")
            )
        } else {
            null
        }
    }

    fun logInUser(nickname: String, password: String): User? {
        val userID = getUserID(nickname)
        return if (userID != null) {
            val user = getUserByID(userID)!!
            if (!user.isAuthorized && user.checkPassword(password)) {
                val statement: PreparedStatement = connection.prepareStatement(
                    "UPDATE users SET is_authorized = ? WHERE id = ?"
                )
                statement.setBoolean(1, true)
                statement.setInt(2, userID)
                statement.executeUpdate()

                println("User ${user.nickname} authorized")
                user.isAuthorized = true
                user
            } else {
                println("Authorization Error.")
                null
            }
        } else {
            println("Gotten user does not exists")
            null
        }
    }

    fun logOutUser(user: User) {
        if (user.isAuthorized) {
            val statement: PreparedStatement = connection.prepareStatement(
                "UPDATE users SET is_authorized = ? WHERE id = ?"
            )
            statement.setBoolean(1, false)
            statement.setInt(2, getUserID(user.nickname)!!)
            statement.executeUpdate()

            user.isAuthorized = false
            println("User ${user.nickname} logged out")
        } else {
            println("User ${user.nickname} not authorized")
        }
    }

    fun addCommand(vararg commandList: Command) {
        for (command in commandList) {
            commands.add(command)
        }
    }

    fun getCommand(name: String): Command? {
        for (command in commands) {
            if (command.name == name) {
                return command
            }
        }
        return null
    }
}
