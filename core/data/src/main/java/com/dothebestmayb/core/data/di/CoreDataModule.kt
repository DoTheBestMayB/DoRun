package com.dothebestmayb.core.data.di

import com.dothebestmayb.core.data.auth.EncryptedSessionStorage
import com.dothebestmayb.core.data.networking.HttpClientFactory
import com.dothebestmayb.core.domain.SessionStorage
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val coreDataModule = module {
    single {
        HttpClientFactory().build()
    }

    singleOf(::EncryptedSessionStorage).bind<SessionStorage>()
}
