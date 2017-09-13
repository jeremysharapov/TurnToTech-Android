package rajat.turntotech.com.espressosample;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.app.Activity;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.LargeTest;
import android.util.Log;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsNull.notNullValue;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class ApplicationTest {

    public static final String STRING_TO_BE_TYPED = "TurnToTech";

    public static final String BIO_TO_BE_TYPED = "Some text here. bla bla bla..";
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);


    @Test
    public void changeText_sameActivity() {
        // Type text and then press the button.
        onView(withId(R.id.editTextUserInput)).perform(typeText(STRING_TO_BE_TYPED), closeSoftKeyboard());
        onView(withId(R.id.changeTextBt)).perform(click());

        // Check that the text was changed.
        onView(withId(R.id.textToBeChanged)).check(matches(withText(STRING_TO_BE_TYPED)));
    }

    @Test
    public void changeText_newActivity() {
        // Type text and then press the button.
        onView(withId(R.id.editTextUserInput)).perform(typeText(STRING_TO_BE_TYPED), closeSoftKeyboard());

        onView(withId(R.id.userBio)).perform(typeText(BIO_TO_BE_TYPED), closeSoftKeyboard());
        onView(withId(R.id.activityChangeTextBtn)).perform(click());

        // This view is in a different Activity, no need to tell Espresso.
        String expectedString = STRING_TO_BE_TYPED+"\'s Bio - \n"+BIO_TO_BE_TYPED;
        Log.d("TestEspresso",expectedString);
        onView(withId(R.id.show_text_view)).check(matches(withText(expectedString)));
    }


    //Unit Test

    @Test
    public void unitTestForButtons() {
        // check that the change test button is there with proper name
        onView(withId(R.id.changeTextBt)).check(matches(notNullValue()));
        onView(withId(R.id.changeTextBt)).check(matches(withText(R.string.change)));


        // check that the details button is there with proper name
        onView(withId(R.id.activityChangeTextBtn)).check(matches(notNullValue()));
        onView(withId(R.id.activityChangeTextBtn)).check(matches(withText(R.string.go_to_detail)));
        onView(withId(R.id.activityChangeTextBtn)).perform(click());
    }
}