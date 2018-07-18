package json.chao.com.wanandroid.test;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import json.chao.com.wanandroid.R;


/**
 * @author quchao
 * @date 2018/6/7
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class TestActivityTest {

    @Rule
    public ActivityTestRule<TestActivity> mActivityTestRule
            = new ActivityTestRule<>(TestActivity.class);

    @Test
    public void ActivityTestRule() {
        TestActivity mTestActivity = mActivityTestRule.getActivity();
    }

    @Test
    public void ViewMatchers() {
        Espresso.onView(ViewMatchers.withId(json.chao.com.wanandroid.R.id.button));
        //onView内部最好不要使用withText()断言处理
        Espresso.onView(Matchers.allOf(ViewMatchers.withId(json.chao.com.wanandroid.R.id.button), ViewMatchers.withText("HaHa")));
        Espresso.onView(Matchers.allOf(ViewMatchers.withId(json.chao.com.wanandroid.R.id.button), Matchers.not(ViewMatchers.withText("HaHa"))));
    }

    @Test
    public void ViewActions() {
        Espresso.onView(ViewMatchers.withId(json.chao.com.wanandroid.R.id.button)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(json.chao.com.wanandroid.R.id.button)).perform(ViewActions.typeText("HaHa"), ViewActions.click());
        Espresso.onView(ViewMatchers.withId(json.chao.com.wanandroid.R.id.button)).perform(ViewActions.scrollTo(), ViewActions.click());
    }

    @Test
    public void ViewAssertions() {
        Espresso.onView(ViewMatchers.withId(json.chao.com.wanandroid.R.id.button)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(json.chao.com.wanandroid.R.id.button2)).check(ViewAssertions.matches(ViewMatchers.withText("HaHa")));
    }

    @Test
    public void adapterViewSimpleTest() {
        Espresso.onView(ViewMatchers.withId(json.chao.com.wanandroid.R.id.button3)).perform(ViewActions.click());
        Espresso.onData(Matchers.allOf(Matchers
                .is(Matchers.instanceOf(String.class)), Matchers.is("HaHa")))
                .perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(json.chao.com.wanandroid.R.id.button))
                .check(ViewAssertions.matches(ViewMatchers.withText(Matchers.containsString("HaHa"))));
    }

    @Test
    public void hasSibling() {
        Espresso.onView(Matchers.allOf(ViewMatchers.withText("7"),
                ViewMatchers.hasSibling(ViewMatchers.withText("item: 1"))));
    }

    @Test
    public void matcherViewIsInActionBarInternal() {
        Espresso.openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        Espresso.openContextualActionModeOverflowMenu();
    }

    @Test
    public void notDisplayed() {
        Espresso.onView(ViewMatchers.withId(json.chao.com.wanandroid.R.id.button)).
                check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())));
    }

    @Test
    public void checkNotExist() {
        Espresso.onView(ViewMatchers.withId(R.id.button)).check(ViewAssertions.doesNotExist());
    }

    @Test
    public void assertAdapterHasItem() {

    }

}
