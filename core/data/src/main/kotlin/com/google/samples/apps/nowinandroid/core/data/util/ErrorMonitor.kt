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

import kotlinx.coroutines.flow.Flow
import java.util.UUID

/**
 * Interface for handling error messages.
 */
interface ErrorMonitor {
    fun addErrorMessage(type: ErrorType,
        label: String? = null,
        successAction: (() -> Unit)? = null,
        failureAction: (() -> Unit)? = null)
    : UUID?

    fun clearErrorMessage(id: UUID)

    fun clearMessages()

    val errorMessages: Flow<List<ErrorMessage?>>
}

/**
 * Specified Errors
 */
sealed class ErrorType {
    data object OFFLINE : ErrorType()
    data class MESSAGE(val value: String) : ErrorType()
    data object UNKNOWN : ErrorType()
}
