package com.dothebestmayb.auth.domain

class UserDataValidator(
    private val patternValidator: PatternValidator,
) {

    fun isValidEmail(email: String): Boolean {
        return patternValidator.matches(email.trim())
    }

    // regex를 사용하면 더 빠르게 검증할 수 있다. ( 1번만 탐색 vs 5번 탐색 )
    // 그런데 regex는 봤을 때 어떤 로직인지 바로 알기 어려워서, philip은 아래와 같이 작성하는 것을 선호
    fun validatePassword(password: String): PasswordValidationState {
        val hasMinLength = password.length >= MIN_PASSWORD_LENGTH
        val hasDigit = password.any { it.isDigit() }
        val hasLowerCaseCharacter = password.any { it.isLowerCase() }
        val hasUpperCaseCharacter = password.any { it.isUpperCase() }

        return PasswordValidationState(
            hasMinLength = hasMinLength,
            hasNumber = hasDigit,
            hasLowerCaseCharacter = hasLowerCaseCharacter,
            hasUpperCaseCharacter = hasUpperCaseCharacter,
        )
    }

    companion object {
        const val MIN_PASSWORD_LENGTH = 9
    }
}
