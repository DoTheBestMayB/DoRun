package com.dothebestmayb.auth.data

import com.dothebestmayb.auth.domain.AuthRepository
import com.dothebestmayb.core.data.networking.post
import com.dothebestmayb.core.domain.util.DataError
import com.dothebestmayb.core.domain.util.EmptyResult
import io.ktor.client.HttpClient

class AuthRepositoryImpl(
    private val httpClient: HttpClient,
): AuthRepository {

    override suspend fun register(email: String, password: String): EmptyResult<DataError.Network> {
        return httpClient.post<RegisterRequest, Unit>(
            route = "/register",
            body = RegisterRequest(
                email = email,
                password = password,
            )
        )
    }
}
