package com.example.internationalmodule1

import android.util.Log
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performImeAction
import androidx.compose.ui.test.performScrollToNode
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

const val delayTime = 4000L

const val isTesting = true
val currentTestingList = 19..100

@RunWith(AndroidJUnit4::class)
class startTesting {
    @get:Rule
    val rule = createAndroidComposeRule<MainActivity>()

    private fun step(step: Int, info: String, test: () -> Unit) {
        val msg = "Step No $step, $info"
        Log.i("startTesting", msg)
        test()
        if (step in currentTestingList || !isTesting) Thread.sleep(delayTime)
    }

    private fun tag(tag: String): SemanticsNodeInteraction {
        return rule.onNodeWithTag(tag, useUnmergedTree = true)
    }

    private fun text(text: String): SemanticsNodeInteraction {
        return rule.onNodeWithText(text)
    }

    @Test
    fun test() {
        step(1, "Application startup") {}
        step(2, "Scroll to the bottom of the hotel list") {
            tag(TestTag.Home.hotelList)
                .performScrollToNode(hasTestTag(TestTag.Home.hotelListBottom))
        }
        step(3, "Perform a search after entering the search term in the search bar") {
            tag(TestTag.Home.searchBar).performTextInput("Méribel")
            tag(TestTag.Home.searchBar).performImeAction()
        }
        step(4, "Click the listing item Appartement Méribel") {
            tag(TestTag.Home.hotelList)
                .performScrollToNode(hasTestTag("Appartement Méribel"))
            tag("Appartement Méribel").performClick()
            tag(TestTag.Details.screen).assertExists()
        }
        step(5, "Click the Guest reviews tab button") {
            tag(TestTag.Details.guestReviewsTabBtn).performClick()
            tag(TestTag.Details.guestReviewsTab).assertExists()
        }
        step(6, "Scroll down the Reviews list to the 4th Review published by Miles") {
            tag(TestTag.Details.guestReviewsTab)
                .performScrollToNode(hasTestTag(TestTag.Details.reviewsList))
            tag(TestTag.Details.reviewsList).performScrollToNode(hasText("Miles"))
        }
        step(7, "Click the Room selection label button") {
            tag(TestTag.Details.roomSelectionTabBtn).performClick()
            tag(TestTag.Details.roomSelectionTab).assertExists()
        }
        step(8, "Scroll down the room list to the \"Great Family Room\" item") {
            tag(TestTag.Details.roomSelectionTab)
                .performScrollToNode(hasText("Great Family Room", ignoreCase = true))
            rule.onNodeWithText("Great Family Room", ignoreCase = true).assertExists()
        }
        step(9, "Click the \"Great Family Room\" item") {
            rule.onNodeWithText("Great Family Room", ignoreCase = true).performClick()
            rule.onNodeWithTag(TestTag.Confirm.screen).assertExists()
        }
        step(10, "Enter First Name") {
            tag(TestTag.Confirm.firstName).performTextInput("Taylor")
        }
        step(11, "Enter Last Name") {
            tag(TestTag.Confirm.lastName).performTextInput("Hutchinson")
        }
        step(12, "Enter check-in date") {
            tag(TestTag.Confirm.checkIn).performTextInput("Oct 15 2024")
        }
        step(13, "Enter check-out date") {
            tag(TestTag.Confirm.checkOut).performTextInput("2024/2/10")
        }
        step(14, "Enter the number of adults") {
            tag(TestTag.Confirm.adults).performTextClearance()
            tag(TestTag.Confirm.adults).performTextInput("4")
        }
        step(15, "Enter the number of children") {
            tag(TestTag.Confirm.children).performTextClearance()
            tag(TestTag.Confirm.children).performTextInput("3")
        }
        step(16, "Click the E-Pay radio button") {
            tag(TestTag.Confirm.form).performScrollToNode(hasTestTag(TestTag.Confirm.ePay))
            tag(TestTag.Confirm.ePay).performClick()
        }
        step(17, "Click the \"Book now\" button") {
            tag(TestTag.Confirm.bookNowBtn).performClick()
            tag(TestTag.Confirm.errorDialog).assertExists()
        }
        step(18, "Change check-out date") {
            tag(TestTag.Confirm.errorDialogYes).performClick()
            tag(TestTag.Confirm.screen).assertExists()
            tag(TestTag.Confirm.checkOut).apply {
                performTextClearance()
                performTextInput("10-19-2024")
            }
        }
        step(19, "Click the \"Book now\" button") {
            tag(TestTag.Confirm.bookNowBtn).performClick()
            tag(TestTag.Confirm.confirmDialog).assertExists()
        }
        step(20, "Click \"Yes\" in the pop-up window to confirm your reservation.") {
            tag(TestTag.Confirm.confirmDialogYes).performClick()
            tag(TestTag.MyBooking.screen).assertExists()
        }
    }
}