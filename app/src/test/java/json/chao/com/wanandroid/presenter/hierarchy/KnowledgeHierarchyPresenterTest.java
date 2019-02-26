package json.chao.com.wanandroid.presenter.hierarchy;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import json.chao.com.wanandroid.BasePresenterTest;
import json.chao.com.wanandroid.BuildConfig;
import json.chao.com.wanandroid.contract.hierarchy.KnowledgeHierarchyContract;

import static org.junit.Assert.*;

/**
 * @author quchao
 * @date 2018/6/12
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 23)
public class KnowledgeHierarchyPresenterTest extends BasePresenterTest {

    @Mock
    private KnowledgeHierarchyContract.View mView;
    private KnowledgeHierarchyPresenter mKnowledgeHierarchyPresenter;

    @Before
    public void setUp() {
        super.setUp();
        mKnowledgeHierarchyPresenter = new KnowledgeHierarchyPresenter(mDataManager);
        mKnowledgeHierarchyPresenter.attachView(mView);
    }

    @Test
    public void getKnowledgeHierarchyData() {
        mKnowledgeHierarchyPresenter.getKnowledgeHierarchyData(true);
        Mockito.verify(mView).showKnowledgeHierarchyData(ArgumentMatchers.any());
    }

}