package rainmekka.andela.com.bakingreciepeapp;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.RunWith;

import rainmekka.andela.com.bakingreciepeapp.ui.ReciepeListActivity;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;
import static rainmekka.andela.com.bakingreciepeapp.TestUtils.withRecyclerView;

/**
 * Created by Oluleke on 7/24/2017.
 */
@RunWith(AndroidJUnit4.class)
public class BakingAppTest {

    @Rule
    public ActivityTestRule<ReciepeListActivity> mBakingAppTestRule
            = new ActivityTestRule<>(ReciepeListActivity.class);

    @Test
    public void confirmNumberOfreciepes_openRecipeDetailsUi() throws Exception{
        //if this test passes,It means the test is wrong
        onView(withId(R.id.reciepe_list)).perform(RecyclerViewActions.scrollToPosition(20));

    }

    @Test
    public void clickReciepeButton_openRecipeDetailsUi() throws Exception{
        //UI test to display list of ReciepeDetails Page for Item 2
//
//        onView(withId(R.id.reciepe_list)).perform(RecyclerViewActions.scrollToPosition(1));
//
//        onView(withId(R.id.reciepe_list)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
        onView(withRecyclerView(R.id.reciepe_list).atPosition(0)).perform(click());

        onView(withId(R.id.reciepe_detail_container))
                .check(matches(isDisplayed()));
    }
}
