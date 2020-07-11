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

public class LoginActivityTest {

    @Rule
    public ActivityTestRule<LoginActivity> testRule = new ActivityTestRule<>(LoginActivity.class);


    @Test
    public void loginTest(){
        onView(withId(R.id.etEmail_Login))
                .perform(typeText("sanimtesting1@gmail.com"));

        Espresso.closeSoftKeyboard();

        onView(withId(R.id.etPassword_Login)).perform(typeText("testing1"));

        Espresso.closeSoftKeyboard();
        onView(withId(R.id.btnLoginL)).perform(click());

    }
}
