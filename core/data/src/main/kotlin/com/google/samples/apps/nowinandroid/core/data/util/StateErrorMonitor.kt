/*
 * Copyright 2024 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.samples.apps.nowinandroid.core.data.util

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import java.util.UUID
import javax.inject.Inject

/**
 * Interface implementation for handling general errors.
 */

class StateErrorMonitor @Inject constructor(): ErrorMonitor {
    /**
     * List of [ErrorMessage] to be shown to the user
     */
    override val errorMessages = MutableStateFlow<List<ErrorMessage>>(emptyList())

    /**
     * Creates an [ErrorMessage] from String value and adds it to the list.
     *
     * @param error: String value of the error message.
     *
     * Returns the UUID of the new [ErrorMessage] if success
     * Returns null if [error] is Blank
     */
    override fun addErrorMessage(type: ErrorType,
        label: String?,
        onConfirm: (() -> Unit)?,
        onDelay: (() -> Unit)?
    ): UUID {

        val newError = ErrorMessage(
                type = type,
                label = label,
                onConfirm = onConfirm,
                onDelay = onDelay
            )

        errorMessages.update { it + newError }

        return newError.id
    }

    /**
     * Removes the [ErrorMessage] with the specified [id] from the list.
     */
    override fun clearErrorMessage(id: UUID) {
        errorMessages.update { it.filter { item -> item.id != id } }
    }

    override fun clearMessages(){
        errorMessages.update { emptyList() }
    }
}

/**
 * Models the data needed for an error message to be displayed and tracked.
 */
data class ErrorMessage(
    val type: ErrorType,
    val id: UUID = UUID.randomUUID(),
    val label: String? = null,
    val onConfirm: (() -> Unit)? = null,
    val onDelay: (() -> Unit)? = null,
)
