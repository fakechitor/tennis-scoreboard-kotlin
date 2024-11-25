package util

import java.io.InputStream

class Validation {

    fun validateMatchesAttributes(page: String, name : String) {
        checkPageValueIsNumber(page)
        checkPageValueIsPositive(page)
        checkNameLengthIsGood(name)
    }

    fun validateNewMatchAttributes(name1: String, name2: String) {
        checkNamesIsDifferent(name1, name2)
        checkNameLengthIsGood(name1)
        checkNameLengthIsGood(name2)
        checkNameContainsOnlyLetters(name1)
        checkNameContainsOnlyLetters(name2)
        checkForbiddenWord(name1)
        checkForbiddenWord(name2)
    }

    private fun checkNameLengthIsGood(name: String) {
        if (name.length <= 3 || name.length >= 20) {
            throw IllegalArgumentException("Имя должно содержать от 3 до 20 символов")
        }
    }
    private fun checkNamesIsDifferent(name1: String, name2: String) {
        if (name1.lowercase() == name2.lowercase()) {
            throw IllegalArgumentException("Имена игроков должны быть разными")
        }
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
        if (name.lowercase() in forbiddenWords) {
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