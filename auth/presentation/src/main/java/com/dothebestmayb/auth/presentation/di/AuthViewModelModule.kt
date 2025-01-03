package com.dothebestmayb.auth.presentation.di

import com.dothebestmayb.auth.presentation.login.LoginViewModel
import com.dothebestmayb.auth.presentation.register.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val authViewModelModule = module {
    viewModelOf(::RegisterViewModel)
    viewModelOf(::LoginViewModel)
}
