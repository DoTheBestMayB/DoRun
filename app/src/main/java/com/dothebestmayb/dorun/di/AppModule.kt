package com.dothebestmayb.dorun.di

import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

// dynamic feature module에서 koin을 사용하면 flexibility가 좋다고 한다.
// 예를 들어, 컴파일 타임에 없는 의존 관계로 빌드가 안 되는데, Dagger2를 이용하면 할 수 있다.
// 그런데 비용이 많이 든다. koin을 이용하면 간단하게 해결된다.

val appModule = module {
    single<SharedPreferences> {
        EncryptedSharedPreferences(
            androidApplication(),
            "auth_pref",
            MasterKey(androidApplication()),
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }
}
