package com.google.samples.apps.nowinandroid.ui

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onChild
import androidx.compose.ui.test.performClick
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.VerificationModes.times
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import com.google.samples.apps.nowinandroid.core.testing.data.userNewsResourcesTestData
import com.google.samples.apps.nowinandroid.core.ui.NewsFeedUiState.Success
import com.google.samples.apps.nowinandroid.core.ui.newsFeed
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

@HiltAndroidTest
class NewsFeedUiTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)
    @BindValue
    @get:Rule(order = 1)
    val tmpFolder: TemporaryFolder = TemporaryFolder.builder().assureDeletion().build()
    @get:Rule(order = 2)
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val userNewsResources = userNewsResourcesTestData.take(1)

    @Before
    fun startUp() {
        hiltRule.inject()
        Intents.init()
    }
    @After
    fun tearDown() {
        Intents.release()
    }

    @Composable
    fun DefaultNewsFeed() {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.testTag("test")
        ) {
            newsFeed(feedState = Success(userNewsResources),
                onNewsResourcesCheckedChanged = { _, _ -> },
                onNewsResourceViewed = {},
                onTopicClick = {},
                )
        }
    }

    @Composable
    fun TestNewsFeed(openToChrome: Boolean) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.testTag("test")
        ) {
            newsFeed(feedState = Success(userNewsResources),
                onNewsResourcesCheckedChanged = { _, _ -> },
                onNewsResourceViewed = {},
                onTopicClick = {},
                openToChrome = openToChrome,
                )
        }
    }

    @Test
    fun testWillOpenChromeWhenDefault() {

        composeTestRule.setContent {
            DefaultNewsFeed()
        }

        val newsFeed = composeTestRule.onNode(hasTestTag("test"), useUnmergedTree = true)
        newsFeed.onChild().performClick()

        intended(hasAction(Intent.ACTION_VIEW))
    }
    @Test
    fun testWillOpenChromeWhenTrue() {

        composeTestRule.setContent {
            TestNewsFeed(true)
        }

        val newsFeed = composeTestRule.onNode(hasTestTag("test"), useUnmergedTree = true)
        newsFeed.onChild().performClick()

        intended(hasAction(Intent.ACTION_VIEW))
    }

    @Test
    fun testWillOpenChromeWhenFalse() {

        composeTestRule.setContent {
            TestNewsFeed(false)
        }

        val newsFeed = composeTestRule.onNode(hasTestTag("test"), useUnmergedTree = true)
        newsFeed.onChild().performClick()

        intended(hasAction(Intent.ACTION_VIEW), times(0))
    }
}