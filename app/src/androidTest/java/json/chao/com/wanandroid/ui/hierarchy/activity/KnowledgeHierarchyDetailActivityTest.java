package json.chao.com.wanandroid.ui.hierarchy.activity;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author quchao
 * @date 2018/6/15
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class KnowledgeHierarchyDetailActivityTest {

    @Rule
    public ActivityTestRule<KnowledgeHierarchyDetailActivity> mActivityTestRule =
            new ActivityTestRule<>(KnowledgeHierarchyDetailActivity.class);

    private IdlingResource mCountingIdlingResource;

    @Before
    public void setUp() {
        mCountingIdlingResource = mActivityTestRule.getActivity().getCountingIdlingResource();
    }



    @After
    public void testFinish() {
        Espresso.unregisterIdlingResources(mCountingIdlingResource);
    }

}