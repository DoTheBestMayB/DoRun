package com.dothebestmayb.auth.presentation.register

sealed interface RegisterAction {

    data object OnTogglePasswordVisibilityCheck: RegisterAction
    data object OnLoginClick: RegisterAction
    data object OnRegisterClick: RegisterAction
}