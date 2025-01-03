package com.dothebestmayb.core.data.networking

import com.dothebestmayb.core.data.BuildConfig
import com.dothebestmayb.core.domain.AuthInfo
import com.dothebestmayb.core.domain.SessionStorage
import com.dothebestmayb.core.domain.util.Result
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import timber.log.Timber

class HttpClientFactory(
    private val sessionStorage: SessionStorage,
) {

    fun build(): HttpClient {
        return HttpClient(CIO) {
            // parsing 관련 설정
            install(ContentNegotiation) {
                json(
                    json = Json {
                        // 응답을 parsing하는 과정에서 data class에 없는 field가 있으면 무시한다.
                        // runtime crash 방지
                        ignoreUnknownKeys = true
                    }
                )
            }
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Timber.d(message)
                    }
                }
                level = LogLevel.ALL
            }
            // request의 기본 값 설정 ex) header, contentType
            defaultRequest {
                contentType(ContentType.Application.Json)
                header("x-api-key", BuildConfig.API_KEY)
            }

            install(Auth) {
                bearer {
                    loadTokens {
                        val info = sessionStorage.get()
                        BearerTokens(
                            accessToken = info?.accessToken ?: "",
                            refreshToken = info?.refreshToken ?: "",
                        )
                    }
                    // ktor는 accessToken에 대한 응답 코드를 보고 만료되었는지를 판단해서 refreshToken으로 accessToken을 갱신한다.
                    // cf) 401 Unauthorized Error
                    refreshTokens {
                        val info = sessionStorage.get()

                        // refreshToken이 없으면 accessToken을 갱신할 수 없으므로 skip
                        if (info?.refreshToken.isNullOrEmpty()) {
                            return@refreshTokens BearerTokens(
                                accessToken = "",
                                refreshToken = "",
                            )
                        }

                        val response = client.post<AccessTokenRequest, AccessTokenResponse>(
                            route = "/accessToken",
                            body = AccessTokenRequest(
                                refreshToken = info?.refreshToken ?: "",
                                userId = info?.userId ?: "",
                            )
                        )

                        if (response is Result.Success) {
                            val newAuthInfo = AuthInfo(
                                accessToken = response.data.accessToken,
                                refreshToken = info?.refreshToken ?: "",
                                userId = info?.userId ?: ""
                            )
                            sessionStorage.set(newAuthInfo)

                            BearerTokens(
                                accessToken = newAuthInfo.accessToken,
                                refreshToken = newAuthInfo.refreshToken,
                            )
                        } else {
                            BearerTokens(
                                accessToken = "",
                                refreshToken = "",
                            )
                        }
                    }
                }
            }
        }
    }
}
