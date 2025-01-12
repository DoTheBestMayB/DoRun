package com.dothebestmayb.run.presentation.active_run

import com.dothebestmayb.core.presentation.ui.UiText

sealed interface ActiveRunEvent {
    data class Error(val error: UiText): ActiveRunEvent
    data object RunSaved: ActiveRunEvent
}
