package json.chao.com.wanandroid.ui.main.activity;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import json.chao.com.wanandroid.R;
import json.chao.com.wanandroid.app.WanAndroidApp;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


/**
 * @author quchao
 * @date 2018/6/14
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class SplashActivityTest {

    @Rule
    public ActivityTestRule<SplashActivity> mActivityTestRule =
            new ActivityTestRule<>(SplashActivity.class);

    @Test
    public void testJumpMainActivity() throws InterruptedException {
        if (!WanAndroidApp.isFirstRun) {
            return;
        }
        Thread.sleep(2100);

         onView(withId(R.id.one_animation)).check(doesNotExist());
    }

}