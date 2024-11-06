package utils

class Get {
    fun int(prompt: String, min: Int? = null, max: Int? = null) : Int {
        print(prompt)
        val res = readln().toIntOrNull()
        if (res != null) {
            if (max != null && res > max || min != null && res < min) {
                println("Out of limits! Limits: $min (min), $max (max)")
                return int(prompt, min = min, max = max)
            }
            return res
        } else {
            println("Input must be an integer")
            return int(prompt, min = min, max = max)
        }
    }

    fun float(prompt: String) : Float {
        print(prompt)
        val res = readln().toFloatOrNull()

        if (res != null) {
            return res
        } else {
            println("Input must be floating point number")
            return float(prompt)
        }
    }

    fun str(prompt: String) : String {
        print(prompt)
        return readln()
    }

    fun strCorrect(prompt: String, regex: Regex) : String {
        val input = str(prompt)
        if (!regex.matches(input)) {
            println("Incorrect input!")
            return strCorrect(prompt, regex)
        }
        return input
    }
}