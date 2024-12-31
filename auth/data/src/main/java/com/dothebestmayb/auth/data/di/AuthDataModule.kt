package com.dothebestmayb.auth.data.di

import com.dothebestmayb.auth.data.EmailPatternValidator
import com.dothebestmayb.auth.domain.PatternValidator
import com.dothebestmayb.auth.domain.UserDataValidator
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val authDataModule = module {
    single<PatternValidator> {
        EmailPatternValidator
    }
    singleOf(::UserDataValidator)
}
