package com.example.internationalmodule1

import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performImeAction
import androidx.compose.ui.test.performScrollToNode
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

@RunWith(AndroidJUnit4::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class startTesting {
    @get:Rule
    val rule = createAndroidComposeRule<MainActivity>()


    private val delayTime = 4000L

    private fun step() {
        Thread.sleep(delayTime)
    }

    @Test
    fun `Step No 1 Application startup`() {
        step()
    }

    @Test
    fun `Step No 2 Scroll to the bottom of the hotel list`() {
        rule.onNodeWithTag(TestTag.Home.hotelList)
            .performScrollToNode(hasTestTag(TestTag.Home.hotelListBottom))
        step()
    }

    @Test
    fun `Step No 3 Perform a search after entering the search term in the search bar`() {
        rule.onNodeWithTag(TestTag.Home.searchBar).performTextInput("Méribel")
        rule.onNodeWithTag(TestTag.Home.searchBar).performImeAction()
        step()
    }

    @Test
    fun `Step No 4 Click the listing item Appartement Méribel`() {
        rule.onNodeWithTag(TestTag.Home.hotelList)
            .performScrollToNode(hasTestTag("Appartement Méribel"))
        rule.onNodeWithTag("Appartement Méribel").performClick()
        rule.onNodeWithTag(TestTag.Details.detailsScreen).assertExists()
        step()
    }

    @Test
    fun `Step No 5`() {

    }
}