package util

import java.io.InputStream

class Validation {
    companion object {
        fun String.validatePlayerName() {
            this.apply {
                throwIfLonger(20)
                checkNotForbidden()
                containsOnlyLetters()
            }
        }

        fun String.validateDifference(anotherStr: String) {
            if (this.equals(anotherStr, ignoreCase = true)) {
                throw IllegalArgumentException("Имена игроков должны быть разными")
            }
        }

        fun String.toIntOrThrow(): Int {
            try {
                val number = this.toInt()
                return number
            } catch (e: NumberFormatException) {
                throw IllegalArgumentException("Номер страницы должен быть числом")
            }
        }

        fun Int.checkIsPositive() {
            if (this <= 0) {
                throw IllegalArgumentException("Номер страницы должен быть больше 0")
            }
        }

        fun String.throwIfLonger(length: Int) {
            if (this.length >= length) {
                throw IllegalArgumentException("Имя может содержать до $length символов")
            }
        }

        private fun String.containsOnlyLetters() {
            if (!this.lowercase().all { it in 'a'..'z' || it in 'а'..'я' }) {
                throw IllegalArgumentException("Имя может содержать только буквы")
            }
        }

        private fun String.checkNotForbidden() {
            if (this in getForbiddenWords()) {
                throw IllegalArgumentException("Вы используете запрещенное имя. Попробуйте другое =)")
            }
        }

        private fun getForbiddenWords(): List<String> {
            val inputStream: InputStream? = this::class.java.classLoader.getResourceAsStream("ForbiddenWords")
            return inputStream?.bufferedReader().use { reader ->
                reader?.readText()?.split("\n")?.map { it.trim() } ?: emptyList()
            }
        }
    }
}
