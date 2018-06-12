package json.chao.com.wanandroid;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import json.chao.com.wanandroid.test.TestActivity;


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
    public void testToast() {
        Espresso.onView(ViewMatchers.withId(R.id.button2))
                .perform(ViewActions.click())
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

}
