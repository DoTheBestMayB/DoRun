package com.dothebestmayb.run.presentation.run_overview.mapper

import com.dothebestmayb.core.domain.run.Run
import com.dothebestmayb.core.presentation.ui.formatted
import com.dothebestmayb.core.presentation.ui.toFormattedKm
import com.dothebestmayb.core.presentation.ui.toFormattedKmh
import com.dothebestmayb.core.presentation.ui.toFormattedMeters
import com.dothebestmayb.core.presentation.ui.toFormattedPace
import com.dothebestmayb.run.presentation.run_overview.model.RunUi
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun Run.toRunUi(): RunUi {
    // 사용자의 현재 타임존으로 시간을 변환
    val dateTimeInLocalTime = dateTimeUtc
        .withZoneSameInstant(ZoneId.systemDefault())

    val formattedDateTime = DateTimeFormatter
        .ofPattern("MMM dd, yyyy - hh:mma") // a : AM or PM
        .format(dateTimeInLocalTime)

    val distanceKm = distanceMeters / 1000.0

    return RunUi(
        id = id!!,
        duration = duration.formatted(),
        dateTime = formattedDateTime,
        distance = distanceKm.toFormattedKm(),
        avgSpeed = avgSpeedKmh.toFormattedKmh(),
        maxSpeed = maxSpeedKmh.toFormattedKmh(),
        pace = duration.toFormattedPace(distanceKm),
        totalElevation = totalElevationMeters.toFormattedMeters(),
        mapPictureUrl = mapPictureUrl,
    )
}
