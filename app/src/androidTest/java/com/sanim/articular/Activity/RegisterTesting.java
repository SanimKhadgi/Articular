package com.sanim.articular.Activity;

import androidx.test.espresso.Espresso;
import androidx.test.rule.ActivityTestRule;

import com.sanim.articular.R;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class RegisterTesting { @Rule
public ActivityTestRule<RegisterActivity> testRule=new ActivityTestRule<>(RegisterActivity.class);

    @Test
    public void RegisterTesting(){

        onView(withId(R.id.etFullName))
                .perform(typeText("Sanim Khadgi"));

        Espresso.closeSoftKeyboard();

        onView(withId(R.id.etEmail)).perform(typeText("testing1@gmail.com"));

        Espresso.closeSoftKeyboard();

        onView(withId(R.id.etPassword)).perform(typeText("testing"));

        Espresso.closeSoftKeyboard();

        onView(withId(R.id.etPhone)).perform(typeText("98215487630"));

        Espresso.closeSoftKeyboard();
        onView(withId(R.id.button_register)).perform(click());
    }
}
