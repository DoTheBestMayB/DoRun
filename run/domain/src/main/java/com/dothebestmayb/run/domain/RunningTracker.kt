package com.dothebestmayb.run.domain

import com.dothebestmayb.core.domain.Timer
import com.dothebestmayb.core.domain.location.LocationTimestamp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combineTransform
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.zip
import kotlin.math.roundToInt
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

/**
 * 이 로직을 ViewModel이 아닌 별도의 class로 선언하고, viewModelScope가 아닌 applicationScope을 사용하는 이유는
 * process가 강제로 종료되더라도 데이터가 유지되도록하기 위함이다.
 *
 * viewModel에 위치한다고 했을 때, 사용자가 최근 앱 목록에서 swipe out 하면 데이터가 모두 날아간다.
 * 반면 foreground service와 함께 동작하는 RunningTracker는 swipe out 하더라도 데이터가 날아가지 않는다.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class RunningTracker(
    private val locationObserver: LocationObserver,
    private val applicationScope: CoroutineScope,
) {
    private val _runData = MutableStateFlow(RunData())
    val runData = _runData.asStateFlow()

    private val _isTracking = MutableStateFlow(false)
    val isTracking = _isTracking.asStateFlow()

    private val isObservingLocation = MutableStateFlow(false)

    private val _elapsedTime = MutableStateFlow(Duration.ZERO)
    val elapsedTime = _elapsedTime.asStateFlow()

    val currentLocation = isObservingLocation
        .flatMapLatest { isObservingLocation ->
            if (isObservingLocation) {
                locationObserver.observeLocation(1000L)
            } else {
                flowOf()
            }
        }
        .stateIn(
            applicationScope,
            SharingStarted.Lazily,
            null,
        )

    init {
        _isTracking
            .onEach { isTracking ->
                if (!isTracking) {
                    val newList = buildList {
                        addAll(runData.value.locations)
                        add(emptyList<LocationTimestamp>())
                    }.toList()
                    _runData.update { it.copy(
                        locations = newList
                    ) }
                }
            }
            .flatMapLatest { isTracking ->
                if (isTracking) {
                    Timer.timeAndEmit()
                } else {
                    flowOf()
                }
            }.onEach {
                _elapsedTime.value += it
            }.launchIn(applicationScope)

        currentLocation
            .filterNotNull()
            .combineTransform(_isTracking) { location, isTracking ->
                if (isTracking) {
                    emit(location)
                }
            }.zip(_elapsedTime) { location, elapsedTime ->
                LocationTimestamp(
                    location = location,
                    durationTimestamp = elapsedTime,
                )
            }.onEach { location ->
                _runData.update {
                    val currentLocations = runData.value.locations
                    val lastLocationsList = if (currentLocations.isNotEmpty()) {
                        currentLocations.last() + location
                    } else {
                        listOf(location)
                    }
                    val newLocationsList = currentLocations.replaceLast(lastLocationsList)

                    val distanceMeters = LocationDataCalculator.getTotalDistanceMeters(
                        locations = newLocationsList
                    )
                    val distanceKm = distanceMeters / 1000.0
                    val currentDuration = location.durationTimestamp

                    val avgSecondsPerKm = if (distanceKm == 0.0) {
                        0
                    } else {
                        (currentDuration.inWholeSeconds / distanceKm).roundToInt()
                    }

                    RunData(
                        distanceMeters = distanceMeters,
                        pace = avgSecondsPerKm.seconds,
                        locations = newLocationsList
                    )
                }
            }.launchIn(applicationScope)
    }

    fun setIsTracking(isTracking: Boolean) {
        this._isTracking.value = isTracking
    }

    fun startObservingLocation() {
        isObservingLocation.value = true
    }

    fun stopObservingLocation() {
        isObservingLocation.value = false
    }
}

private fun <T> List<List<T>>.replaceLast(replacement: List<T>): List<List<T>> {
    if (this.isEmpty()) {
        return listOf(replacement)
    }
    return this.dropLast(1) + listOf(replacement)
}
