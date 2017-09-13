package io.tutorial.turntotech.infoOrganizerSample;

import android.app.Activity;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import org.hamcrest.Matcher;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;


import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.StringStartsWith.startsWith;
import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {


    @Rule
    public ActivityTestRule<StartActivity> mActivityRule = new ActivityTestRule<>(
            StartActivity.class);
    Activity activity;
    public int count;




    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("io.tutorial.turntotech.listwithrrecyclerview", appContext.getPackageName());
    }


    @Test
    public void goToProductViewForaCompany() throws Exception{
        Thread.sleep(2000);
        // tap recyclerView cell
        // Click item at position 1 - APPLE and check Apple have Iphone or not
        onView(withId(R.id.vertical_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click())).check(matches(hasDescendant(withText("iPhone"))));

        Thread.sleep(2000);

        // Go to webview
        onView(withId(R.id.vertical_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        Thread.sleep(2000);

        // going back to productList
        onView(withId(R.id.imageButton)).perform(click());

        Thread.sleep(2000);


        // check that Product list has Iphone or not??
        onView(withId(R.id.vertical_recycler_view))
                .check(matches(hasDescendant(withText("iPhone"))));

        Thread.sleep(2000);

        // Going back to CompanyList
        onView(withId(R.id.imageButton)).perform(click());

        Thread.sleep(5000);

        // Checking that is APPLE Company is present or not?
        onView(withId(R.id.vertical_recycler_view))
                .check(matches(hasDescendant(withText("Apple"))));

        Thread.sleep(2000);


    }

    @Test
    public void createNewCompany() throws Exception{
        // Create a company and Chceck that company is present in the recyclerView or not

        // Click to Add button
        onView(withId(R.id.imageButton2)).perform(click());



        // filling Form for adding a new Company
        Espresso.onView(ViewMatchers.withId((R.id.CompanyName)))
                .perform(ViewActions.typeText("RajatTest"),closeSoftKeyboard());


        Espresso.onView(ViewMatchers.withId((R.id.StockTicker))).perform(typeText("MSFT"),
                closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId((R.id.CompLogoURL))).perform(typeText("http://www.3dollarlogos.com/images/entries/220x140/logo9.png"),
                closeSoftKeyboard());


        Thread.sleep(2000);

        // Click to Submit Button
        Espresso.onView(ViewMatchers.withId((R.id.Finish))).perform(click());

        // Now Check new Company is present or not
        onView(withId(R.id.vertical_recycler_view)).perform(RecyclerViewActions.scrollToPosition(DAO.getInstance().getcompanyList().size()-1));

        Thread.sleep(2000);

        onView(withId(R.id.vertical_recycler_view))
                .check(matches(hasDescendant(withText("RajatTest"))));
        Thread.sleep(2000);
    }


    @Test
    public void createNewProductForNewCompany() throws Exception{


        // Checking that is RajatTest Company is present or not?
        onView(withId(R.id.vertical_recycler_view)).perform(RecyclerViewActions.scrollToPosition(DAO.getInstance().getcompanyList().size()-1));
        // onView(withRecyclerView(recyclerViewId).atPosition(position)).perform(click());

        Thread.sleep(2000);

        onView(withId(R.id.vertical_recycler_view))
                .check(matches(hasDescendant(withText("RajatTest"))));
        Thread.sleep(2000);


        // If it is present then

        // Click a item which has Content "RajatTest"
        onView(withText(startsWith("RajatTest"))).perform(click());
        Thread.sleep(2000);


        // Now we are in ProductView
        // Click to Add button
        onView(withId(R.id.imageButton2)).perform(click());



        // filling Form for adding a new Product
        Espresso.onView(ViewMatchers.withId((R.id.ProductName)))
                .perform(ViewActions.typeText("RajatProduct1"),closeSoftKeyboard());


        Espresso.onView(ViewMatchers.withId((R.id.ProductURL))).perform(typeText("http://turntotech.io/"),
                closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId((R.id.ProdLogoURL))).perform(typeText("http://www.canadiansolar.com/fileadmin/templates/webroot/img/xNewProduct.png.pagespeed.ic.nnjZnZDs7B.png"),
                closeSoftKeyboard());


        Thread.sleep(2000);

        // Click to Submit Button
        Espresso.onView(ViewMatchers.withId((R.id.button3))).perform(click());

        // Now Check new Company is present or not
        onView(withId(R.id.vertical_recycler_view))
                .check(matches(hasDescendant(withText("RajatProduct1"))));
        Thread.sleep(2000);

    }

    @Test
    public void editCompany() throws Exception{
        onView(withId(R.id.EditInfo)).perform(click());

        onView(withId(R.id.vertical_recycler_view)).perform(RecyclerViewActions.scrollToPosition(DAO.getInstance().getcompanyList().size()-1));

        Thread.sleep(2000);

        onView(withText(startsWith("RajatTest"))).perform(click());

        Espresso.onView(ViewMatchers.withId((R.id.CompanyName))).perform(ViewActions.clearText());

        Espresso.onView(ViewMatchers.withId((R.id.CompanyName)))
                .perform(ViewActions.typeText("RajatTester5"),closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId((R.id.StockTicker))).perform(ViewActions.clearText());

        Espresso.onView(ViewMatchers.withId((R.id.StockTicker))).perform(typeText("AAPL"),
                closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId((R.id.CompLogoURL))).perform(ViewActions.clearText());

        Espresso.onView(ViewMatchers.withId((R.id.CompLogoURL))).perform(typeText("http://cdn02.androidauthority.net/wp-content/uploads/2016/06/android-win-1-300x214.png"),
                closeSoftKeyboard());

        Thread.sleep(2000);

        Espresso.onView(ViewMatchers.withId((R.id.Finish))).perform(click());

        onView(withId(R.id.vertical_recycler_view)).perform(RecyclerViewActions.scrollToPosition(DAO.getInstance().getcompanyList().size()-1));

        Thread.sleep(2000);

        onView(withId(R.id.vertical_recycler_view))
                .check(matches(hasDescendant(withText("RajatTester5"))));

        Thread.sleep(2000);

        onView(withText(startsWith("RajatTester5"))).perform(click());

        Thread.sleep(2000);

        onView(withId(R.id.vertical_recycler_view))
                .check(matches(hasDescendant(withText("RajatProduct1"))));

    }



    @Test
    public void editProduct() throws Exception{
        onView(withId(R.id.vertical_recycler_view)).perform(RecyclerViewActions.scrollToPosition(DAO.getInstance().getcompanyList().size()-1));

        Thread.sleep(2000);

        onView(withId(R.id.vertical_recycler_view))
                .check(matches(hasDescendant(withText("RajatTester5"))));

        Thread.sleep(2000);

        onView(withText(startsWith("RajatTester5"))).perform(click());

        onView(withId(R.id.vertical_recycler_view))
                .check(matches(hasDescendant(withText("RajatProduct1"))));

        Thread.sleep(2000);

        onView(withId(R.id.EditInfo)).perform(click());

        onView(withText(startsWith("RajatProduct1"))).perform(click());

        Espresso.onView(ViewMatchers.withId((R.id.ProductName)))
                .perform(ViewActions.clearText());

        Espresso.onView(ViewMatchers.withId((R.id.ProductName)))
                .perform(ViewActions.typeText("RajatProduct2"),closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId((R.id.ProductURL))).perform(ViewActions.clearText());

        Espresso.onView(ViewMatchers.withId((R.id.ProductURL))).perform(typeText("https://www.apple.com/"),
                closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId((R.id.ProdLogoURL))).perform(ViewActions.clearText());

        Espresso.onView(ViewMatchers.withId((R.id.ProdLogoURL))).perform(typeText("https://crackberry.com/sites/crackberry.com/files/styles/large/public/topic_images/2013/ANDROID.png?itok=xhm7jaxS"),
                closeSoftKeyboard());

        Thread.sleep(2000);

        Espresso.onView(ViewMatchers.withId((R.id.button3))).perform(click());

        onView(withId(R.id.vertical_recycler_view))
                .check(matches(hasDescendant(withText("RajatProduct2"))));

        Thread.sleep(2000);

        onView(withText(startsWith("RajatProduct2"))).perform(click());

        Thread.sleep(2000);
    }

    @Test
    public void deleteProduct() throws Exception{
        // Checking that is RajatTest Company is present or not?
        onView(withId(R.id.vertical_recycler_view)).perform(RecyclerViewActions.scrollToPosition(DAO.getInstance().getcompanyList().size()-1));

        Thread.sleep(2000);

        onView(withId(R.id.vertical_recycler_view))
                .check(matches(hasDescendant(withText("RajatTester5"))));

        Thread.sleep(2000);

        // If it is present go to next View
        onView(withText(startsWith("RajatTester5"))).perform(click());

        Thread.sleep(2000);

        // then check Product

        // Checking that is RajatTest Company is present or not?
        onView(withId(R.id.vertical_recycler_view))
                .check(matches(hasDescendant(withText("RajatProduct2"))));

        Thread.sleep(2000);

        // Now Click delete
//        onView(withId(R.id.deleteButtonToolBar)).perform(click());
//        Thread.sleep(1000);

        // delete RajatTest1 Product
        Espresso.onView(ViewMatchers.withId((R.id.textProductName))).perform(longClick());
        Thread.sleep(1000);

        // AlertView popUp now you have to click OK
        onView(withId(android.R.id.button1)).perform(click());

        Thread.sleep(2000);

    }

    @Test
    public void deleteCompany() throws Exception{
        activity = mActivityRule.getActivity();

        // get number of companies

        count = DAO.getcompanyList().size();

        // Checking that is RajatTest Company is present or not?
        onView(withId(R.id.vertical_recycler_view)).perform(RecyclerViewActions.scrollToPosition(DAO.getInstance().getcompanyList().size()-1));

        Thread.sleep(2000);

        onView(withId(R.id.vertical_recycler_view))
                .check(matches(hasDescendant(withText("RajatTester5"))));

        Thread.sleep(2000);

        onView(withText(startsWith("RajatTester5"))).perform(longClick());
        Thread.sleep(1000);

        // AlertView popUp now you have to click OK
        onView(withId(android.R.id.button1)).perform(click());

        Thread.sleep(2000);
    }

    @Test
    public void justTest() throws Exception{
        onView(withId(R.id.vertical_recycler_view)).perform(RecyclerViewActions.scrollToPosition(DAO.getInstance().getcompanyList().size()-1));

        Thread.sleep(2000);

        onView(withId(R.id.vertical_recycler_view))
                .check(matches(hasDescendant(withText("HTC"))));
        Thread.sleep(2000);
    }
}