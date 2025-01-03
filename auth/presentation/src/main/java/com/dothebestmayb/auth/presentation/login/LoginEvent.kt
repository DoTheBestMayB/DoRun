package com.dothebestmayb.auth.presentation.login

import com.dothebestmayb.core.presentation.ui.UiText

sealed interface LoginEvent {
    data class Error(val error: UiText): LoginEvent
    data object LoginSuccess: LoginEvent
}
