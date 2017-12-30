package pes.agorapp;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import pes.agorapp.activities.LanguageActivity;
import pes.agorapp.activities.MainActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by marc on 29/12/17.
 */


@RunWith(AndroidJUnit4.class)
@LargeTest
public class LanguageTest {

    private String  cat_CAT, esp_CAT, eng_CAT,
                    cat_ESP, esp_ESP, eng_ESP,
                    cat_ENG, esp_ENG, eng_ENG;

    @Rule
    public ActivityTestRule<LanguageActivity> mActivityRule =
            new ActivityTestRule<>(LanguageActivity.class);

    @Before
    public void initStrings() {
        cat_CAT = "Català";
        esp_CAT = "Espanyol";
        eng_CAT = "Anglès";
        cat_ESP = "Catalán";
        esp_ESP = "Español";
        eng_ESP = "Inglés";
        cat_ENG = "Catalan";
        esp_ENG = "Spanish";
        eng_ENG = "English";
    }

    @Test
    public void test_btn_catala() {
        onView(withId(R.id.btn_catala_lang))
                .perform(click());

        onView(withId(R.id.btn_catala_lang))
                .check(matches(withText(cat_CAT)));
        onView(withId(R.id.btn_espanol_lang))
                .check(matches(withText(esp_CAT)));
        onView(withId(R.id.btn_english_lang))
                .check(matches(withText(eng_CAT)));
    }

    @Test
    public void test_btn_espanol() {
        onView(withId(R.id.btn_espanol_lang)).perform(click());

        onView(withId(R.id.btn_catala_lang))
                .check(matches(withText(cat_ESP)));
        onView(withId(R.id.btn_espanol_lang))
                .check(matches(withText(esp_ESP)));
        onView(withId(R.id.btn_english_lang))
                .check(matches(withText(eng_ESP)));
    }

    @Test
    public void test_btn_english() {
        onView(withId(R.id.btn_english_lang))
                .perform(click());

        onView(withId(R.id.btn_catala_lang))
                .check(matches(withText(cat_ENG)));
        onView(withId(R.id.btn_espanol_lang))
                .check(matches(withText(esp_ENG)));
        onView(withId(R.id.btn_english_lang))
                .check(matches(withText(eng_ENG)));
    }

}
