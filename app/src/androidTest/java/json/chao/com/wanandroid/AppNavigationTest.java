package json.chao.com.wanandroid;

import android.support.annotation.IdRes;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.espresso.contrib.DrawerMatchers;
import android.support.test.espresso.contrib.NavigationViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.Gravity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import json.chao.com.wanandroid.ui.main.activity.MainActivity;

/**
 * @author quchao
 * @date 2018/6/15
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class AppNavigationTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule
            = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void clickAppNavigationIconOpenNavigation() {
        Espresso.onView(ViewMatchers.withId(R.id.drawer_layout))
                .check(ViewAssertions.matches(DrawerMatchers.isClosed(Gravity.LEFT)));

        Espresso.onView(ViewMatchers.withContentDescription(
                TestUtils.getToolBarNavigationContentDescription(
                        mActivityTestRule.getActivity(),
                        R.id.common_toolbar
                ))).perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.drawer_layout))
                .check(ViewAssertions.matches(DrawerMatchers.isOpen(Gravity.LEFT)));
    }

    @Test
    public void clickNavigationLoginShowLoginScreen() {
        openLoginPage();

        Espresso.onView(ViewMatchers.withId(R.id.login_group))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void clickNavigationWanAndroidItemShowMainPage() {
        openNavigationItem(R.id.nav_item_wan_android);

        Espresso.onView(ViewMatchers.withId(R.id.main_pager_recycler_view))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void clickNavigationCollectItemShowCollectPage() {
        openNavigationItem(R.id.nav_item_my_collect);

        Espresso.onView(ViewMatchers.withId(R.id.login_group))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    private void openNavigationItem(@IdRes int itemId) {
        Espresso.onView(ViewMatchers.withId(R.id.drawer_layout))
                .check(ViewAssertions.matches(DrawerMatchers.isClosed(Gravity.LEFT)))
                .perform(DrawerActions.open());

        Espresso.onView(ViewMatchers.withId(R.id.nav_view))
                .perform(NavigationViewActions.navigateTo(itemId));
    }

    private void openLoginPage() {
        Espresso.onView(ViewMatchers.withId(R.id.drawer_layout))
                .check(ViewAssertions.matches(DrawerMatchers.isClosed(Gravity.LEFT)))
                .perform(DrawerActions.open());

        Espresso.onView(ViewMatchers.withId(R.id.nav_header_login_tv))
                .perform(ViewActions.click());
    }

}
