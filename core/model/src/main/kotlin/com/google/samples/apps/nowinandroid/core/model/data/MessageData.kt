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

package com.google.samples.apps.nowinandroid.core.model.data


data class MessageData(
    //val id: UUID = UUID.randomUUID(),
    val type: MessageType,
    val label: String? = null,
    val onConfirm: (() -> Unit)? = null,
    val onDelay: (() -> Unit)? = null
)

/**
 * Specified Errors
 */
sealed class MessageType {
    data object OFFLINE : MessageType()
    data class MESSAGE(val value: String) : MessageType()
    data object UNKNOWN : MessageType()
   // data class Error(val type: ErrorType) : MessageType()
    //data class Data(val value: Any) : MessageType()
}
/*
sealed class ErrorType {
    data object OFFLINE : ErrorType()
    data class MESSAGE(val value: String) : ErrorType()
    data object UNKNOWN : ErrorType()
}*/