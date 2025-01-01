package com.dothebestmayb.core.data.networking

import com.dothebestmayb.core.data.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
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

class HttpClientFactory {

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
        }
    }
}
