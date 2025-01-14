package com.dothebestmayb.run.location

import android.location.Location
import com.dothebestmayb.core.domain.location.LocationWithAltitude

fun Location.toLocationWithAltitude(): LocationWithAltitude {
    return LocationWithAltitude(
        location = com.dothebestmayb.core.domain.location.Location(
            lat = latitude,
            long = longitude,
        ),
        altitude = altitude,
    )
}
