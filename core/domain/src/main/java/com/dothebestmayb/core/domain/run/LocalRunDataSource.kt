package com.dothebestmayb.core.domain.run

import com.dothebestmayb.core.domain.util.DataError
import com.dothebestmayb.core.domain.util.Result
import kotlinx.coroutines.flow.Flow

/**
 * 1. typealias를 이용하면 긴 패키지 네임을 없앨 수 있다.
 * 2. 아래에서 RunId를 반환하기 위해 String을 적는 것보다 RunId라고 적는 것이 더 명확하다.
 */
typealias RunId = String

interface LocalRunDataSource {

    fun getRuns(): Flow<List<Run>>

    suspend fun upsertRun(run: Run): Result<RunId, DataError.Local>

    suspend fun upsertRuns(runs: List<Run>): Result<List<RunId>, DataError.Local>

    suspend fun deleteRun(id: String)

    suspend fun deleteAllRuns()
}
