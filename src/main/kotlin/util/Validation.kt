package util

import java.io.InputStream

class Validation {
    companion object {
        fun String.validatePlayerName() {
            this.apply {
                checkAllowedLength()
                checkNotForbidden()
                containsOnlyLetters()
            }
        }

        fun String.validateSearchName() {
            this.checkAllowedLength()
        }

        fun String.validatePage() {
            this.apply {
                checkIsNumber()
                checkIsPositive()
            }
        }

        fun String.validateDifference(anotherStr: String) {
            if (this.equals(anotherStr, ignoreCase = true)) {
                throw IllegalArgumentException("Имена игроков должны быть разными")
            }
        }

        private fun String.checkIsNumber() {
            try {
                this.toInt()
            } catch (e: NumberFormatException) {
                throw IllegalArgumentException("Номер страницы должен быть числом")
            }
        }

        private fun String.checkIsPositive() {
            if (this.toInt() <= 0) {
                throw IllegalArgumentException("Номер страницы должен быть больше 0")
            }
        }

        private fun String.checkAllowedLength() {
            if (this.length >= 20) {
                throw IllegalArgumentException("Имя может содержать до 20 символов")
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
