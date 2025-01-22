package com.dothebestmayb.run.domain

import com.dothebestmayb.core.domain.location.LocationTimestamp
import kotlin.time.Duration

/**
 * 2차원 배열로 location을 기록하는 이유는 사용자가 run tracking을 pause한 후, 다시 시작했을 때
 * 이전 위치와 현재 위치를 연결하지 않고(연결하면 텔레포트 하는 것처럼 보임)
 * 새로운 tracking 라인을 그리도록 하기 위함
 */
data class RunData(
    val distanceMeters: Int = 0,
    val pace: Duration = Duration.ZERO,
    val locations: List<List<LocationTimestamp>> = emptyList(),
)
