package com.example.photos.ui.activity;

import androidx.test.espresso.action.ViewActions;

import com.example.photos.R;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

class PhotosActivityTest {

    @Rule
    public ActivityTestRule

    @Test
    public void validateButtonOpenMediaChooser() {
        onView(withId(R.id.empty_content)).perform(ViewActions.click());
    }

}
