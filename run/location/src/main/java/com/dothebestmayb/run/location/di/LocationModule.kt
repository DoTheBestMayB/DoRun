package com.dothebestmayb.run.location.di

import com.dothebestmayb.run.domain.LocationObserver
import com.dothebestmayb.run.location.AndroidLocationObserver
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val locationModule = module {
    singleOf(::AndroidLocationObserver).bind<LocationObserver>()

}
