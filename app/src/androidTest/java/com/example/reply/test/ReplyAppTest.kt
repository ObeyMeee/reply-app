package com.example.reply.test

import androidx.activity.ComponentActivity
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import org.junit.Rule
import org.junit.Test
import com.example.reply.R
import com.example.reply.ui.ReplyApp

class ReplyAppTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    @TestCompactWidth
    fun compactDevice_verifyUsingBottomNavigation() {
        composeTestRule.setContent { 
            ReplyApp(windowSize = WindowWidthSizeClass.Compact)
        }
        composeTestRule.onNodeWithTagForStringId(R.string.navigation_bottom).assertExists()
    }

    @Test
    @TestMediumWidth
    fun mediumDevice_verifyUsingNavigationRail() {
        composeTestRule.setContent {
            ReplyApp(windowSize = WindowWidthSizeClass.Medium)
        }
        composeTestRule.onNodeWithTagForStringId(R.string.navigation_rail).assertExists()
    }

}