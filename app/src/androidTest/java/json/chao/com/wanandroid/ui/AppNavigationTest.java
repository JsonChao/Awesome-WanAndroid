package json.chao.com.wanandroid.ui;

import android.support.annotation.IdRes;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.espresso.contrib.DrawerMatchers;
import android.support.test.espresso.contrib.NavigationViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.Gravity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import json.chao.com.wanandroid.BasePageTest;
import json.chao.com.wanandroid.R;
import json.chao.com.wanandroid.ui.main.activity.MainActivity;
import json.chao.com.wanandroid.uitls.TestUtils;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


/**
 * @author quchao
 * @date 2018/6/15
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class AppNavigationTest extends BasePageTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule
            = new ActivityTestRule<>(MainActivity.class);

    private IdlingResource mCountingIdlingResource;

    @Before
    public void setUp() {
        mCountingIdlingResource = mActivityTestRule.getActivity().getCountingIdlingResource();
    }

    @After
    public void testFinish() {
        IdlingRegistry.getInstance().unregister(mCountingIdlingResource);
    }

    @Test
    public void clickAppNavigationIconOpenNavigation() {
        onView(ViewMatchers.withId(R.id.drawer_layout))
                .check(matches(DrawerMatchers.isClosed(Gravity.LEFT)));

        onView(withContentDescription(
                TestUtils.getToolBarNavigationContentDescription(
                        mActivityTestRule.getActivity(),
                        R.id.common_toolbar
                ))).perform(ViewActions.click());

        onView(withId(R.id.drawer_layout))
                .check(matches(DrawerMatchers.isOpen(Gravity.LEFT)));
    }

    @Test
    public void clickNavigationLoginShowLoginScreen() {
        openLoginPage();

        checkVisible(R.id.login_group);
    }

    @Test
    public void clickItemShowMainPage() {
        openNavigationItem(R.id.nav_item_wan_android);

        checkVisible(R.id.main_pager_recycler_view);
    }

//    @Test
//    public void clickItemNotShowCollectPage() {
//        openNavigationItem(R.id.nav_item_my_collect);
//
//        checkVisible(R.id.login_group);
//    }

    @Test
    public void clickItemShowCollectPage() {
        clickNavigationLoginShowLoginScreen();

        onView(withId(R.id.login_account_edit))
                .perform(ViewActions.typeText("2243963927"));

        onView(withId(R.id.login_password_edit))
                .perform(ViewActions.typeText("qaz123"),
                        ViewActions.closeSoftKeyboard());

        clickView(R.id.login_btn);

        //将异步请求转换为同步执行
        IdlingRegistry.getInstance().register(mCountingIdlingResource);

        checkVisible(R.id.nav_view);

        onView(withId(R.id.nav_view))
                .perform(NavigationViewActions.navigateTo(R.id.nav_item_my_collect));

        checkVisible(R.id.collect_recycler_view);
    }

    @Test
    public void clickItemShowSettingPage() {
        openNavigationItem(R.id.nav_item_setting);

        checkVisible(R.id.setting_usage_tv);
    }

    @Test
    public void clickItemShowAboutUsPage() {
        openNavigationItem(R.id.nav_item_about_us);

        checkVisible(R.id.about_us_refresh_layout);
    }

    @Test
    public void clickItemShowLogoutDialog() {
        openNavigationItem(R.id.nav_item_logout);

        checkVisible(R.id.dialog_content);
    }

    private void openNavigationItem(@IdRes int itemId) {
        onView(withId(R.id.drawer_layout))
                .check(matches(DrawerMatchers.isClosed(Gravity.LEFT)))
                .perform(DrawerActions.open());

        onView(withId(R.id.nav_view))
                .perform(NavigationViewActions.navigateTo(itemId));
    }

    private void openLoginPage() {
        onView(withId(R.id.drawer_layout))
                .check(matches(DrawerMatchers.isClosed(Gravity.LEFT)))
                .perform(DrawerActions.open());

        clickView(R.id.nav_header_login_tv);
    }

}
