package com.dothebestmayb.auth.presentation.register

import androidx.compose.foundation.text.input.TextFieldState
import com.dothebestmayb.auth.domain.PasswordValidationState

/**
 * canRegister를 아래와 같이 default로 구현했을 때
 * passwordValidationState.isValidPassword와 isRegistering가 변경되어 canRegister 변수 값이 바껴도 recomposition이 발생하지 않는다.
 *
 * ```kotlin
 *  val canRegister: Boolean = passwordValidationState.isValidPassword && !isRegistering,
 *  ```
 *
 *  그래서 canRegister 값을 직접 변경 하도록 로직을 수정함
 */
data class RegisterState(
    val email: TextFieldState = TextFieldState(),
    val isEmailValid: Boolean = false,
    val password: TextFieldState = TextFieldState(),
    val isPasswordVisible: Boolean = false,
    val passwordValidationState: PasswordValidationState = PasswordValidationState(),
    val isRegistering: Boolean = false,
    val canRegister: Boolean = false,
)
