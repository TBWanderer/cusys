//package main.kotlin.projects.simplesystem
//
//import main.kotlin.projects.simplesystem.structs.*
//
//class System {
//    val commands = mutableListOf<Command>()
//    private val users = mutableListOf<User>()
//
//    fun addUser(user: User) {
//        if (users.any { it.email == user.email }) {
//            println("User with gotten email already exists")
//        } else {
//            users.add(user)
//            println("User ${user.firstName} ${user.lastName} added to the System")
//        }
//    }
//
//    fun getUser(email: String) : User? {
//        val user = users.find { it.email == email }
//        return user
//    }
//
//    fun logInUser(email: String, password: String): User? {
//        val user = users.find { it.email == email }
//        return if (user != null && !user.isAuthorized && user.checkPassword(password)) {
//            user.isAuthorized = true
//            println("User ${user.firstName} ${user.lastName} authorized")
//            user
//        } else if (user == null) {
//            println("Authorization Error. Gotten user does not exists")
//            null
//        } else if (user.isAuthorized){
//            println("Authorization Error. Gotten user already authorized")
//            null
//        } else {
//            println("Authorization Error. Wrong Password")
//            null
//        }
//    }
//
//    fun logOutUser(user: User) {
//        if (user.isAuthorized) {
//            user.isAuthorized = false
//            println("User ${user.firstName} ${user.lastName} logged out")
//        } else {
//            println("User ${user.firstName} ${user.lastName} not authorized")
//        }
//    }
//
//    fun addCommand(vararg commandList: Command) {
//        for (command in commandList) {
//            commands.add(command)
//        }
//    }
//
//    fun getCommand(name: String): Command? {
//        for (command in commands) {
//            if (command.name == name) {
//                return command
//            }
//        }
//        return null
//    }
//}

import structs.Command
import structs.User

import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.ResultSet

class System(private val dbUrl: String) {
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
            "INSERT INTO users (first_name, last_name, email, password, level) VALUES (?, ?, ?, ?, ?)",
            PreparedStatement.RETURN_GENERATED_KEYS
        )
        statement.setString(1, user.firstName)
        statement.setString(2, user.lastName)
        statement.setString(3, user.email)
        statement.setString(4, user.hashedPassword)
        statement.setInt(5, user.level)

        try {
            statement.executeUpdate()
            println("User ${user.firstName} ${user.lastName} added to the System")
        } catch (e: Exception) {
            println("Error adding user: ${e.message}")
        }
    }

    fun getUserID(email: String): Int? {
        val statement: PreparedStatement = connection.prepareStatement("SELECT id FROM users WHERE email = ?")
        statement.setString(1, email)
        val resultSet: ResultSet = statement.executeQuery()

        return if (resultSet.next()) {
            resultSet.getInt("id")
        } else {
            null
        }
    }

    fun getUserByID(userID: Int) : User? {
        val statement: PreparedStatement = connection.prepareStatement("SELECT (first_name, last_name, email, password, level, ) FROM users WHERE id = ?")
        statement.setInt(1, userID)
        val resultSet: ResultSet = statement.executeQuery()

        return if (resultSet.next()) {
            User(
                firstName = resultSet.getString("first_name"),
                lastName = resultSet.getString("last_name"),
                age = resultSet.getInt("age"),
                email = resultSet.getString("email"),
                hashedPassword = resultSet.getString("password"),
                level = resultSet.getInt("level"),
                isAuthorized = resultSet.getBoolean("is_authorized")
            )
        } else {
            null
        }
    }

    fun logInUser(email: String, password: String): User? {
        val userID = getUserID(email)
        return if (userID != null) {
            val user = getUserByID(userID)!!
            if (!user.isAuthorized && user.checkPassword(password)) {
                val statement: PreparedStatement = connection.prepareStatement(
                    "UPDATE users SET is_authorized = ? WHERE id = ?"
                )
                statement.setBoolean(1, true)
                statement.setInt(2, userID)
                statement.executeUpdate()

                println("User ${user.firstName} ${user.lastName} authorized")
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
            statement.setInt(2, getUserID(user.email)!!)
            statement.executeUpdate()

            user.isAuthorized = false
            println("User ${user.firstName} ${user.lastName} logged out")
        } else {
            println("User ${user.firstName} ${user.lastName} not authorized")
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
