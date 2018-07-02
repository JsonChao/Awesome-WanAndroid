package json.chao.com.wanandroid;

import android.support.annotation.IdRes;
import android.support.test.espresso.matcher.ViewMatchers;

import json.chao.com.wanandroid.uitls.TestUtils;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeDown;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static android.support.test.espresso.matcher.ViewMatchers.Visibility.VISIBLE;
import static android.support.test.espresso.matcher.ViewMatchers.hasFocus;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

/**
 * @author quchao
 * @date 2018/6/22
 */
public class BasePageTest {

    protected static final long DELAY_TIME = 1500;

    /**
     * Check view visible
     *
     * @param viewId IdRes to View
     */
    protected void checkVisible(@IdRes int viewId) {
        onView(withId(viewId))
                .check(matches(isDisplayed()));
    }

    /**
     * Click view
     *
     * @param viewId IdRes to view
     */
    protected void clickView(@IdRes int viewId) {
        onView(withId(viewId))
                .check(matches(isDisplayed()))
                .perform(click());
    }

    /**
     * Click view
     *
     * @param content Content to view
     */
    protected void clickView(String content) {
        onView(withText(content))
                .check(matches(isDisplayed()))
                .perform(click());
    }

    /**
     * Click the item of the recyclerView
     *
     * @param recyclerViewId IdRes to recyclerView
     * @param itemPosition RecyclerView item position
     */
    protected void clickRecyclerViewItem(@IdRes int recyclerViewId, int itemPosition) {
        onView(allOf(withId(recyclerViewId), hasFocus()))
                .check(matches(isDisplayed()))
                .perform(actionOnItemAtPosition(itemPosition, click()));
    }

    /**
     * Click the item child view of the recyclerView
     *
     * @param recyclerViewId IdRes to recyclerView
     * @param itemPosition Recycler item position
     * @param itemChildViewId IdRes to item child view of the RecyclerView
     */
    protected void clickRecyclerViewItemChildView(@IdRes int recyclerViewId,
                                                  int itemPosition,
                                                  @IdRes int itemChildViewId) {
        onView(TestUtils.withRecyclerView(recyclerViewId)
                .atPositionOnView(itemPosition, itemChildViewId))
                .perform(click());
    }

    /**
     * Pull to refresh layout
     *
     * @param refreshLayoutId IdRes to refresh layout / recyclerView
     */
    protected void pullToSmartRefresh(@IdRes int refreshLayoutId) {
        onView(allOf(withId(refreshLayoutId), hasFocus()))
                .check(matches(isDisplayed()))
                .perform(swipeDown());
    }

    /**
     * Scroll recyclerView to specify position
     *
     * @param recyclerViewId IdRes to recyclerView
     * @param position specify position
     */
    protected void scrollRecyclerViewToPosition(@IdRes int recyclerViewId, int position) {
        onView(allOf(withId(recyclerViewId), hasFocus()))
                .check(matches(isDisplayed()))
                .perform(scrollToPosition(position));
    }

    /**
     * Swipe up recyclerView to specify position
     *
     * @param recyclerViewId IdRes to recyclerView
     * @param position specify position
     */
    protected void swipeUpRecyclerViewToPosition(@IdRes int recyclerViewId, int position) {
        onView(allOf(withId(recyclerViewId), hasFocus()))
                .check(matches(isDisplayed()))
                .perform(scrollToPosition(position))
                .perform(swipeUp());
    }

    /**
     * Swipe Down recyclerView to specify position
     *
     * @param recyclerViewId IdRes to recyclerView
     * @param position specify position
     */
    protected void swipeDownRecyclerViewToPosition(@IdRes int recyclerViewId, int position) {
        onView(allOf(withId(recyclerViewId), hasFocus()))
                .check(matches(isDisplayed()))
                .perform(scrollToPosition(position))
                .perform(swipeDown());
    }

}
