package com.example.photos.ui.activity

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.photos.R
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(AndroidJUnit4::class)
class PhotosActivityTest {

    @Rule
    @JvmField
    val activityRule: ActivityScenarioRule<PhotosActivity> = ActivityScenarioRule(PhotosActivity::class.java)
    lateinit var activity: PhotosActivity

    @get:Rule
    val activityAnotherRule: ActivityTestRule<>

    @Before
    fun setup() {
//        activity = activityRule.activity
    }

    @Test
    fun validateButtonOpenMediaChooser() {
        onView(withId(R.id.empty_content)).perform(click())
        activityRule.scenario
    }
}