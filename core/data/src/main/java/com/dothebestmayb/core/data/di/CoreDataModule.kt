package com.dothebestmayb.core.data.di

import com.dothebestmayb.core.data.networking.HttpClientFactory
import org.koin.dsl.module

val coreDataModule = module {
    single {
        HttpClientFactory().build()
    }
}
