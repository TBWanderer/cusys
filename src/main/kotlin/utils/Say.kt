package utils

class Say {
    fun line(prompt: String, level: Int = 0) {
        var line = ""
        if (level > 0) {
            for (stick in 1..<level) {
                line += "   "
            }
            line += " |- "
        }
        line += prompt
        println(line)
    }
}