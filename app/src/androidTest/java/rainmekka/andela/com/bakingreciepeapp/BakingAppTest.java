package rainmekka.andela.com.bakingreciepeapp;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
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

/**
 * Created by Oluleke on 7/24/2017.
 */
@RunWith(AndroidJUnit4.class)
public class BakingAppTest {

    @Rule
    public ActivityTestRule<ReciepeListActivity> mBakingAppTestRule
            = new ActivityTestRule<>(ReciepeListActivity.class);

    @Test
    public void clickReciepeButton_openRecipeDetailsUi() throws Exception{
        onView(withId(R.id.reciepe_list)).perform(RecyclerViewActions.scrollToPosition(2));

        onView(withId(R.id.content))
                .check(matches(withText("Yello cake")))
                .check(matches((isDisplayed())));


//        onView(withId(R.id.reciepe_list)).perform(RecyclerViewActions.actionOnItemAtPosition(2,click()));
//        onView(withId(R.id.reciepe_detail_container))
//                .check(matches(isDisplayed()));

//        onData(anything()).inAdapterView(withId(R.id.tea_grid_view)).atPosition(1).perform(click());
//
//        // Checks that the OrderActivity opens with the correct tea name displayed
//        onView(withId(R.id.tea_name_text_view)).check(matches(withText(TEA_NAME)));
    }


}
