package json.chao.com.wanandroid.ui;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import json.chao.com.wanandroid.BasePageTest;
import json.chao.com.wanandroid.R;
import json.chao.com.wanandroid.ui.main.activity.MainActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


/**
 * @author quchao
 * @date 2018/6/19
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class SearchPageTest extends BasePageTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

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
    public void clickSearchPage() {
        onView(withId(R.id.action_search))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(withId(R.id.search_coordinator_group))
                .check(matches(isDisplayed()));
    }

    @Test
    public void inputInfoAndClickSearch() {
        clickSearchPage();

        IdlingRegistry.getInstance().register(mCountingIdlingResource);

        onView(withId(R.id.search_edit))
                .check(matches(isDisplayed()))
                .perform(replaceText("RxJava"))
                .check(matches(withText("RxJava")));

        onView(withId(R.id.search_tv))
                .check(matches(isDisplayed()))
                .perform(click());

        IdlingRegistry.getInstance().register(mCountingIdlingResource);

        onView(withId(R.id.search_list_refresh_layout))
                .check(matches(isDisplayed()));
    }

    @Test
    public void clickHotSearchItem() {
        clickSearchPage();

        IdlingRegistry.getInstance().register(mCountingIdlingResource);

        onView(withText("RxJava"))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(withId(R.id.search_list_refresh_layout))
                .check(matches(isDisplayed()));
    }

    @Test
    public void clickHistoryItem() {
        inputInfoAndClickSearch();

        pressBack();

        clickSearchPage();

        onView(withText("RxJava"))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(withId(R.id.search_list_refresh_layout))
                .check(matches(isDisplayed()));
    }

    @Test
    public void clearAllHistory() {
        inputInfoAndClickSearch();

        pressBack();

        clickSearchPage();

        onView(withText("RxJava"))
                .check(matches(isDisplayed()));

        onView(withId(R.id.search_history_clear_all_tv))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(withId(R.id.search_history_null_tint_tv))
                .check(matches(isDisplayed()));
    }

    @Test
    public void clickSearchListBack() {
        inputInfoAndClickSearch();

        pressBack();
    }

    @Test
    public void pullToRefresh() throws InterruptedException {
        inputInfoAndClickSearch();

        IdlingRegistry.getInstance().register(mCountingIdlingResource);
        
        pullToSmartRefresh(R.id.search_list_refresh_layout);
     
        Thread.sleep(DELAY_TIME);
    }

    @Test
    public void scrollSearchList() {
        inputInfoAndClickSearch();

        IdlingRegistry.getInstance().register(mCountingIdlingResource);

        swipeUpRecyclerViewToPosition(R.id.normal_view, 10);
    }

    @Test
    public void storeScrollSearchList() throws InterruptedException {
        scrollSearchList();

        swipeDownRecyclerViewToPosition(R.id.normal_view, 6);

        clickView(R.id.search_list_floating_action_btn);

        Thread.sleep(DELAY_TIME);
    }

    @Test
    public void clickSearchListItem() {
        inputInfoAndClickSearch();

        clickRecyclerViewItem(R.id.normal_view, 0);

        onView(withId(R.id.article_detail_web_view))
                .check(matches(isDisplayed()));
    }

    @Test
    public void clickSearchListLikeIv() {
        inputInfoAndClickSearch();

        IdlingRegistry.getInstance().register(mCountingIdlingResource);

        clickRecyclerViewItemChildView(R.id.normal_view,
                0,
                R.id.item_search_pager_like_iv);
    }

    @Test
    public void clickSearchListItemTag() {
        inputInfoAndClickSearch();

        IdlingRegistry.getInstance().register(mCountingIdlingResource);

        clickRecyclerViewItemChildView(R.id.normal_view,
                0,
                R.id.item_search_pager_chapterName);

        onView(withId(R.id.knowledge_hierarchy_detail_viewpager))
                .check(matches(isDisplayed()));
    }

    @Test
    public void clickListItemTagBack() {
        clickSearchListItemTag();

        pressBack();
    }

    @Test
    public void clickListItemTagPageItemLike() {
        clickSearchListItemTag();

        IdlingRegistry.getInstance().register(mCountingIdlingResource);

        clickRecyclerViewItemChildView(R.id.knowledge_hierarchy_list_recycler_view,
                0,
                R.id.item_search_pager_like_iv);
    }

    @Test
    public void pullToRefreshListItemTagPageList() throws InterruptedException {
        clickSearchListItemTag();

        IdlingRegistry.getInstance().register(mCountingIdlingResource);

        pullToSmartRefresh(R.id.knowledge_hierarchy_list_recycler_view);

        Thread.sleep(DELAY_TIME);
    }

    @Test
    public void scrollListItemTagPageList() {
        clickSearchListItemTag();

        IdlingRegistry.getInstance().register(mCountingIdlingResource);

        scrollRecyclerViewToPosition(R.id.knowledge_hierarchy_list_recycler_view, 5);
    }

    @Test
    public void storeScrollListItemTagPageList() throws InterruptedException {
        scrollListItemTagPageList();

        swipeDownRecyclerViewToPosition(R.id.knowledge_hierarchy_list_recycler_view, 3);

        clickView(R.id.knowledge_floating_action_btn);

        Thread.sleep(DELAY_TIME);
    }

    @Test
    public void clickListItemBack() {
        clickSearchListItem();

        pressBack();
    }

    @Test
    public void clickListItemPageLike() {
        clickSearchListItem();

        clickView(R.id.item_collect);
    }

    @Test
    public void clickListItemPageShare() {
        clickSearchListItem();

        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());

        clickView("分享");
    }

    @Test
    public void clickListItemPageBrowser() {
        clickSearchListItem();

        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());

        clickView("用系统浏览器打开");
    }

}
