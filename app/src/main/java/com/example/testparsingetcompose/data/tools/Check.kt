package com.example.testparsingetcompose.data.tools

object Check {
    fun isValidEmail(email: String): Boolean {
        return email.matches("^[\\w\\-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$".toRegex())
    }

    fun containsUpperCase(mdp: String): Boolean {
        return mdp.contains("[A-Z]".toRegex())
    }

    fun containsLowerCase(mdp: String): Boolean {
        return mdp.contains("[a-z]".toRegex())
    }

    fun containsNumber(mdp: String): Boolean {
        return mdp.contains("[0-9]".toRegex())
    }

    fun containsSpecialCharacter(mdp: String): Boolean {
        return mdp.contains("[@\\[\\]\\^\\-!\"#\$%&'\\(\\)\\*\\+,\\.\\/:;{}<>=\\|~\\?]".toRegex())
    }

    fun containsOnlyValidCharacter(mdp: String): Boolean {
        return mdp.matches("^[a-zA-Z0-9@\\[\\]\\^\\-!\"#\$%&'\\(\\)\\*\\+,\\.\\/:;{}<>=\\|~\\?]*\$".toRegex())
    }

    fun passwordContainsAllCharacteristics(mdp: String): Boolean {
        return (containsOnlyValidCharacter(mdp) && containsUpperCase(mdp) && containsNumber(mdp) && containsLowerCase(mdp) && containsSpecialCharacter(mdp) && mdp.length>=8)
    }
}