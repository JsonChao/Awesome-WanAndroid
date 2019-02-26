package json.chao.com.wanandroid.ui.mainpager;

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

import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.Espresso.pressBack;


/**
 * @author quchao
 * @date 2018/6/25
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainPagerTest extends BasePageTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    private IdlingResource mCountingIdlingResource;

    @Before
    public void setUp() {
        mCountingIdlingResource = mActivityTestRule.getActivity().getCountingIdlingResource();
    }

    @After
    public void tearDown() {
        IdlingRegistry.getInstance().unregister(mCountingIdlingResource);
    }

    @Test
    public void mainPagerIfShow() throws InterruptedException {
        Thread.sleep(DELAY_TIME + DELAY_TIME);

        checkVisible(R.id.main_pager_recycler_view);
    }

    @Test
    public void bannerClick() throws InterruptedException {
        mainPagerIfShow();

        clickRecyclerViewItem(R.id.main_pager_recycler_view,
                0);

        Thread.sleep(DELAY_TIME);
    }

    @Test
    public void pullToRefresh() throws InterruptedException {
        mainPagerIfShow();

        pullToSmartRefresh(R.id.normal_view);

        Thread.sleep(DELAY_TIME);
    }

    @Test
    public void scrollMainPagerList() throws InterruptedException {
        mainPagerIfShow();

        swipeDownRecyclerViewToPosition(R.id.main_pager_recycler_view, 10);

        Thread.sleep(DELAY_TIME);
    }

    @Test
    public void storeScrollMainPagerList() throws InterruptedException {
        scrollMainPagerList();

        clickView(R.id.main_floating_action_btn);

        Thread.sleep(DELAY_TIME);
    }

    @Test
    public void clickMainPagerItem() throws InterruptedException {
        mainPagerIfShow();

        clickRecyclerViewItem(R.id.main_pager_recycler_view, 1);

        Thread.sleep(DELAY_TIME);
    }

    @Test
    public void clickMainPagerItemLikeIv() throws InterruptedException {
        mainPagerIfShow();

        clickRecyclerViewItemChildView(R.id.main_pager_recycler_view,
                1,
                R.id.item_search_pager_like_iv);

        Thread.sleep(DELAY_TIME);
    }

    @Test
    public void clickMainPagerItemTag() throws InterruptedException {
        mainPagerIfShow();

        clickRecyclerViewItemChildView(R.id.main_pager_recycler_view,
                1,
                R.id.item_search_pager_chapterName);

        Thread.sleep(DELAY_TIME);
    }

    @Test
    public void clickListItemTagBack() throws InterruptedException {
        clickMainPagerItemTag();

        pressBack();
    }

    @Test
    public void clickListItemTagPageItemLike() throws InterruptedException {
        clickMainPagerItemTag();

        IdlingRegistry.getInstance().register(mCountingIdlingResource);

        clickRecyclerViewItemChildView(R.id.knowledge_hierarchy_list_recycler_view,
                0,
                R.id.item_search_pager_like_iv);
    }

    @Test
    public void pullToRefreshListItemTagPageList() throws InterruptedException {
        clickMainPagerItemTag();

        IdlingRegistry.getInstance().register(mCountingIdlingResource);

        pullToSmartRefresh(R.id.normal_view);

        Thread.sleep(DELAY_TIME);
    }

    @Test
    public void scrollListItemTagPageList() throws InterruptedException {
        clickMainPagerItemTag();

        IdlingRegistry.getInstance().register(mCountingIdlingResource);

        scrollRecyclerViewToPosition(R.id.knowledge_hierarchy_list_recycler_view, 5);
    }

    @Test
    public void storeScrollListItemTagPageList() throws InterruptedException {
        scrollListItemTagPageList();

        swipeDownRecyclerViewToPosition(R.id.knowledge_hierarchy_list_recycler_view, 3);

        clickView(R.id.knowledge_hierarchy_detail_tab_layout);

        Thread.sleep(DELAY_TIME);
    }

    @Test
    public void clickListItemBack() throws InterruptedException {
        clickMainPagerItem();

        pressBack();
    }

    @Test
    public void clickListItemPageLike() throws InterruptedException {
        clickMainPagerItem();

        clickView(R.id.item_collect);
    }

    @Test
    public void clickListItemPageShare() throws InterruptedException {
        clickMainPagerItem();

        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());

        clickView("分享");
    }

    @Test
    public void clickListItemPageBrowser() throws InterruptedException {
        clickMainPagerItem();

        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());

        clickView("用系统浏览器打开");
    }



}