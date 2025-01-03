package com.dothebestmayb.core.data.di

import com.dothebestmayb.core.data.auth.EncryptedSessionStorage
import com.dothebestmayb.core.data.networking.HttpClientFactory
import com.dothebestmayb.core.domain.SessionStorage
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

/**
 * get을 호출하면 bind된 객체를 주입해준다.
 */
val coreDataModule = module {
    single {
        HttpClientFactory(get()).build()
    }

    singleOf(::EncryptedSessionStorage).bind<SessionStorage>()
}
