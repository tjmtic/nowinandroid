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

import com.google.samples.apps.nowinandroid.core.data.util.StateErrorMonitor
import com.google.samples.apps.nowinandroid.core.model.data.MessageData
import com.google.samples.apps.nowinandroid.core.model.data.MessageType
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

/**
 * Unit tests for [StateErrorMonitor].
 */

class StateErrorMonitorTest {

    // Subject under test.
    private lateinit var state: StateErrorMonitor

    private lateinit var messages: List<MessageData?>

    private val fakeString = "Test Error Message"
    private val fakeData = MessageData(MessageType.MESSAGE("Test Error Message 1"))
    private val fakeData2 = MessageData(MessageType.MESSAGE("Test Error Message 2"))

    @Before
    fun setup() {
        state = StateErrorMonitor()
        messages = emptyList()
    }

    @Test
    fun whenErrorIsNotAdded_NullIsPresent() = runTest(UnconfinedTestDispatcher()) {
        backgroundScope.launch {
            state.messages.collect {
                messages = it
            }
        }
        assertEquals(
            emptyList(),
            messages,
        )
    }

    @Test
    fun whenErrorIsAddedByString_ErrorMessageIsPresent() = runTest(UnconfinedTestDispatcher()) {
        backgroundScope.launch {
            state.messages.collect {
                messages = it
            }
        }

        val expect = state.addMessageByString(fakeString)

        assertEquals(
            expect,
            messages.firstOrNull(),
        )
    }

    @Test
    fun whenErrorIsAddedByData_ErrorMessageIsPresent() = runTest(UnconfinedTestDispatcher()) {
        backgroundScope.launch {
            state.messages.collect {
                messages = it
            }
        }

        state.addMessageByData(fakeData)

        assertEquals(
            fakeData,
            messages.firstOrNull(),
        )
    }

    @Test
    fun whenErrorsAreAdded_FirstErrorMessageIsPresent() =
        runTest(UnconfinedTestDispatcher()) {
            state.addMessageByData(fakeData)
            state.addMessageByString(fakeString)

            backgroundScope.launch {
                state.messages.collect {
                    messages = it
                }
            }

            assertEquals(
                fakeData,
                messages.firstOrNull(),
            )
        }

    @Test
    fun whenErrorIsCleared_ErrorMessageIsNotPresent() =
        runTest(UnconfinedTestDispatcher()) {
            backgroundScope.launch {
                state.messages.collect {
                    messages = it
                }
            }
            state.addMessageByData(fakeData)
            state.clearMessage(fakeData)

            assertEquals(
                emptyList(),
                messages,
            )
        }

    @Test
    fun whenErrorsAreCleared_NextErrorMessageIsPresent() =
        runTest(UnconfinedTestDispatcher()) {
            backgroundScope.launch {
                state.messages.collect {
                    messages = it
                }
            }
            state.addMessageByData(fakeData)
            state.addMessageByData(fakeData2)

            state.clearMessage(fakeData)

            assertEquals(
                fakeData2,
                messages.firstOrNull(),
            )
        }
}
