package util

import java.io.InputStream

class Validation {

    fun validateMatchesAttributes(page: String, name: String) {
        checkNameContainsOnlyLetters(name)
        checkForbiddenWord(name)
        checkPageValueIsNumber(page)
        checkPageValueIsPositive(page)
    }

    fun validateNewMatchAttributes(name: String) {
        checkNameContainsOnlyLetters(name)
        checkForbiddenWord(name)
    }

    private fun checkPageValueIsPositive(page: String) {
        if (page.toInt() <= 0) {
            throw IllegalArgumentException("Номер страницы должен быть больше 0")
        }
    }

    private fun checkPageValueIsNumber(page: String) {
        try {
            page.toInt()
        } catch (e: NumberFormatException) {
            throw IllegalArgumentException("Номер страницы должен быть числом")
        }
    }

    private fun checkNameContainsOnlyLetters(name: String) {
        for (item in name.lowercase()) {
            if (item !in 'а'..'я' && item !in 'a'..'z') {
                throw IllegalArgumentException("Имя может содержать только буквы")
            }
        }
    }

    private fun checkForbiddenWord(name: String) {
        val forbiddenWords = getForbiddenWords()
        if (name in forbiddenWords) {
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