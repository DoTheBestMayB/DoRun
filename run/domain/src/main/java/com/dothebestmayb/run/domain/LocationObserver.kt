package com.dothebestmayb.run.domain

import com.dothebestmayb.core.domain.location.LocationWithAltitude
import kotlinx.coroutines.flow.Flow

interface LocationObserver {
    fun observeLocation(interval: Long): Flow<LocationWithAltitude>
}
