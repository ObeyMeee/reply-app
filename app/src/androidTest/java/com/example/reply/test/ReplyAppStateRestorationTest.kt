package com.example.reply.test

import androidx.activity.ComponentActivity
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.ui.test.assertAny
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasAnyDescendant
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.StateRestorationTester
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onChild
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.reply.data.local.LocalEmailsDataProvider
import com.example.reply.ui.ReplyApp
import com.example.reply.R
import org.junit.Rule
import org.junit.Test

class ReplyAppStateRestorationTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    @TestCompactWidth
    fun compactDevice_selectedEmailRetained_afterConfigurationChange() {
        val stateRestorationTester = StateRestorationTester(composeTestRule)
        stateRestorationTester.setContent {
            ReplyApp(windowSize = WindowWidthSizeClass.Compact)
        }
        val thirdEmail = LocalEmailsDataProvider.allEmails[2]
        val thirdEmailBody = composeTestRule.activity.getString(thirdEmail.body)
        val thirdEmailSubject = composeTestRule.activity.getString(thirdEmail.subject)
        isNodeWithTextDisplayed(thirdEmailBody)
        composeTestRule.onNodeWithText(thirdEmailSubject).performClick()
        backButtonExists()
        isNodeWithTextDisplayed(thirdEmailBody)
        stateRestorationTester.emulateSavedInstanceStateRestore()
        backButtonExists()
        isNodeWithTextDisplayed(thirdEmailBody)
    }

    private fun backButtonExists() {
        composeTestRule.onNodeWithContentDescriptionForStringId(R.string.navigation_back)
            .assertExists()
    }

    private fun isNodeWithTextDisplayed(text: String) {
        composeTestRule.onNodeWithText(text).assertIsDisplayed()
    }

    @Test
    @TestExpandedWidth
    fun expandedDevice_selectedEmailRetained_afterConfigurationChange() {
        val stateRestorationTester = StateRestorationTester(composeTestRule)
        composeTestRule.setContent {
            ReplyApp(windowSize = WindowWidthSizeClass.Expanded)
        }
        val thirdEmail = LocalEmailsDataProvider.allEmails[2]
        val thirdEmailBody = composeTestRule.activity.getString(thirdEmail.body)
        isNodeWithTextDisplayed(thirdEmailBody)
        composeTestRule.onNodeWithStringId(thirdEmail.subject).performClick()
        composeTestRule.onNodeWithTagForStringId(R.string.details_screen)
            .onChildren()
            .assertAny(
                hasAnyDescendant(
                    hasText(thirdEmailBody)
                )
            )
        stateRestorationTester.emulateSavedInstanceStateRestore()
        composeTestRule.onNodeWithTagForStringId(R.string.details_screen)
            .onChildren()
            .assertAny(
                hasAnyDescendant(
                    hasText(thirdEmailBody)
                )
            )

    }

}