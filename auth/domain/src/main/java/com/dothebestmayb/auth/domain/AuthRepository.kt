package com.dothebestmayb.auth.domain

import com.dothebestmayb.core.domain.util.DataError
import com.dothebestmayb.core.domain.util.EmptyResult

interface AuthRepository {

    suspend fun register(email: String, password: String): EmptyResult<DataError.Network>
}