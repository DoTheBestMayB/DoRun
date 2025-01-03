package com.dothebestmayb.core.data.auth

import android.content.SharedPreferences
import com.dothebestmayb.core.domain.AuthInfo
import com.dothebestmayb.core.domain.SessionStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * DataStore를 사용하지 않는 이유 : 간단한 Token을 저장하기 위해
 * 복잡한 DataStore 설정을 하고 싶지 않아서
 */
class EncryptedSessionStorage(
    private val sharedPreferences: SharedPreferences,
) : SessionStorage {

    override suspend fun get(): AuthInfo? {
        return withContext(Dispatchers.IO) {
            val json = sharedPreferences.getString(KEY_AUTH_INFO, null)
            json?.let {
                Json.decodeFromString<AuthInfoSerializable>(it).toAuthInfo()
            }
        }
    }

    override suspend fun set(info: AuthInfo?) {
        withContext(Dispatchers.IO) {
            if (info == null) {
                // IO 쓰레드로 전환했기 때문에 commit을 호출해도 상관 없음
                sharedPreferences.edit().remove(KEY_AUTH_INFO).commit()
                return@withContext
            }

            // json string으로 변환하기 위해 데이터 클래스는 serializable 해야 함
            val json = Json.encodeToString(info.toAuthInfoSerializable())
            sharedPreferences
                .edit()
                .putString(KEY_AUTH_INFO, json)
                .commit()
        }
    }

    companion object {
        private const val KEY_AUTH_INFO = "KEY_AUTH_INFO"
    }
}
