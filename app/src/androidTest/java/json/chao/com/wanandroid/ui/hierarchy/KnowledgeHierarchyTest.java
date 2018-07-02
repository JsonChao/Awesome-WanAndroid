package json.chao.com.wanandroid.ui.hierarchy;

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
public class KnowledgeHierarchyTest extends BasePageTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    private IdlingResource mCountingIdlingResource;

    @Before
    public void setUp() {
        mCountingIdlingResource = mActivityTestRule.getActivity().getCountingIdlingResource();
    }

    @After
    public void finishTest() {
        IdlingRegistry.getInstance().unregister(mCountingIdlingResource);
    }

    @Test
    public void showKnowledgePage() throws InterruptedException {
        clickView(R.id.tab_knowledge_hierarchy);

        Thread.sleep(DELAY_TIME);

        checkVisible(R.id.knowledge_hierarchy_recycler_view);
    }

    @Test
    public void pullToRefresh() throws InterruptedException {
        showKnowledgePage();

        pullToSmartRefresh(R.id.knowledge_hierarchy_recycler_view);

        Thread.sleep(DELAY_TIME);
    }

    @Test
    public void scrollKnowledgePageList() throws InterruptedException {
        showKnowledgePage();

        swipeUpRecyclerViewToPosition(R.id.knowledge_hierarchy_recycler_view,
                10);

        Thread.sleep(DELAY_TIME);
    }

    @Test
    public void storeScrollKnowledgePageList() throws InterruptedException {
        scrollKnowledgePageList();

        swipeDownRecyclerViewToPosition(R.id.knowledge_hierarchy_recycler_view,
                6);

        clickView(R.id.main_floating_action_btn);

        Thread.sleep(DELAY_TIME);
    }

    @Test
    public void clickKnowledgeItem() throws InterruptedException {
        showKnowledgePage();

        clickRecyclerViewItem(R.id.knowledge_hierarchy_recycler_view, 2);
    }

    @Test
    public void clickKnowledgeListItemBack() throws InterruptedException {
        clickKnowledgeItem();

        pressBack();
    }

    @Test
    public void pullToRefreshListItemPageList() throws InterruptedException {
        clickKnowledgeItem();

        Thread.sleep(DELAY_TIME);

        pullToSmartRefresh(R.id.knowledge_hierarchy_list_recycler_view);

        Thread.sleep(DELAY_TIME);
    }

    @Test
    public void scrollListItemPageList() throws InterruptedException {
        clickKnowledgeItem();

        Thread.sleep(DELAY_TIME);

        scrollRecyclerViewToPosition(R.id.knowledge_hierarchy_list_recycler_view, 5);
    }

    @Test
    public void storeScrollListItemPageList() throws InterruptedException {
        scrollListItemPageList();

        swipeDownRecyclerViewToPosition(R.id.knowledge_hierarchy_list_recycler_view, 3);

        clickView(R.id.knowledge_hierarchy_detail_tab_layout);

        Thread.sleep(DELAY_TIME);
    }

    @Test
    public void clickListItemPageItemLike() throws InterruptedException {
        clickKnowledgeItem();

        Thread.sleep(DELAY_TIME);

        clickRecyclerViewItemChildView(R.id.knowledge_hierarchy_list_recycler_view,
                0,
                R.id.item_search_pager_like_iv);
    }

    @Test
    public void clickListItem() throws InterruptedException {
        clickKnowledgeItem();

        Thread.sleep(DELAY_TIME);

        clickRecyclerViewItem(R.id.knowledge_hierarchy_list_recycler_view, 0);
    }

    @Test
    public void clickListItemBack() throws InterruptedException {
        clickListItem();

        pressBack();
    }

    @Test
    public void clickListItemPageLike() throws InterruptedException {
        clickListItem();

        clickView(R.id.item_collect);
    }

    @Test
    public void clickListItemPageShare() throws InterruptedException {
        clickListItem();

        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());

        clickView("分享");
    }

    @Test
    public void clickListItemPageBrowser() throws InterruptedException {
        clickListItem();

        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());

        clickView("用系统浏览器打开");
    }

}