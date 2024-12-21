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

import com.google.samples.apps.nowinandroid.core.data.util.ErrorMessage
import com.google.samples.apps.nowinandroid.core.data.util.ErrorType
import com.google.samples.apps.nowinandroid.core.data.util.StateErrorMonitor
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

/**
 * Unit tests for [SnackbarErrorMonitor].
 */

class SnackbarErrorMonitorTest {


    // Subject under test.
    private lateinit var state: StateErrorMonitor

    private lateinit var messages: List<ErrorMessage?>

    @Before
    fun setup() {
        state = StateErrorMonitor()
        messages = emptyList()
    }

    @Test
    fun whenErrorIsNotAdded_NullIsPresent() = runTest(UnconfinedTestDispatcher()) {
        backgroundScope.launch { state.errorMessages.collect() }
        assertEquals(
            emptyList(),
            messages,
        )
    }

    @Test
    fun whenErrorIsAdded_ErrorMessageIsPresent() = runTest(UnconfinedTestDispatcher()) {
        backgroundScope.launch {
            state.errorMessages.collect {
                messages = it
            }
        }

        val id = state.addErrorMessage(ErrorType.MESSAGE("Test Error Message"))

        assertEquals(
            id,
            messages.firstOrNull()?.id,
        )
    }

    @Test
    fun whenErrorsAreAdded_FirstErrorMessageIsPresent() =
        runTest(UnconfinedTestDispatcher()) {
            val id1 = state.addErrorMessage(ErrorType.MESSAGE("Test Error Message 1"))
            state.addErrorMessage(ErrorType.MESSAGE("Test Error Message 2"))

            backgroundScope.launch {
                state.errorMessages.collect {
                    messages = it
                }
            }

            assertEquals(
                id1,
                messages.firstOrNull()?.id,
            )
        }

    @Test
    fun whenErrorIsCleared_ErrorMessageIsNotPresent() =
        runTest(UnconfinedTestDispatcher()) {
            backgroundScope.launch {
                state.errorMessages.collect {
                    messages = it
                }
            }
            val id = state.addErrorMessage(ErrorType.MESSAGE("Test Error Message"))
            state.clearErrorMessage(id)

            assertEquals(
                emptyList(),
                messages,
            )
        }

    @Test
    fun whenErrorsAreCleared_NextErrorMessageIsPresent() =
        runTest(UnconfinedTestDispatcher()) {
            backgroundScope.launch {
                state.errorMessages.collect {
                    messages = it
                }
            }
            val id1 = state.addErrorMessage(ErrorType.MESSAGE("Test Error Message 1"))
            val id2 = state.addErrorMessage(ErrorType.MESSAGE("Test Error Message 2"))

            state.clearErrorMessage(id1)

            assertEquals(
                    id2,
                    messages.firstOrNull()?.id,
                )
        }
}
