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

package com.google.samples.apps.nowinandroid.core.testing.util

import com.google.samples.apps.nowinandroid.core.data.util.ErrorMonitor
import com.google.samples.apps.nowinandroid.core.model.data.MessageData
import com.google.samples.apps.nowinandroid.core.model.data.MessageType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class TestErrorMonitor() : ErrorMonitor {

    val testData: MessageData = MessageData(type = MessageType.MESSAGE("Test Message"))
    var count: Int = 0

    override fun addMessageByString(
        message: String,
    ): MessageData {
        count += 1
        return testData
    }

    override fun addMessageByData(message: MessageData) {
        count += 1
    }

    override fun clearMessage(message: MessageData) {
        count -= 1
    }

    override fun clearAllMessages() {
        count = 0
    }

    override val messages: Flow<List<MessageData?>> = flowOf(List(count) { testData })
}
