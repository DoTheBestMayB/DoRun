package com.dothebestmayb.dorun

import android.app.Application
import com.dothebestmayb.auth.data.di.authDataModule
import com.dothebestmayb.auth.presentation.di.authViewModelModule
import com.dothebestmayb.dorun.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class DoRunApp: Application() {

    override fun onCreate() {
        super.onCreate()

        // Timber 설정
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        // Koin 설정
        startKoin {
            androidLogger()
            androidContext(this@DoRunApp)
            modules(
                authDataModule,
                authViewModelModule,
                appModule,
            )
        }
    }
}
