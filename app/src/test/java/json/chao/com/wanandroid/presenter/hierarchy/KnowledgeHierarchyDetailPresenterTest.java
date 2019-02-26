package json.chao.com.wanandroid.presenter.hierarchy;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import json.chao.com.wanandroid.BasePresenterTest;
import json.chao.com.wanandroid.BuildConfig;
import json.chao.com.wanandroid.component.RxBus;
import json.chao.com.wanandroid.contract.hierarchy.KnowledgeHierarchyDetailContract;
import json.chao.com.wanandroid.core.event.SwitchNavigationEvent;
import json.chao.com.wanandroid.core.event.SwitchProjectEvent;

import static org.junit.Assert.*;

/**
 * @author quchao
 * @date 2018/6/12
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 23)
public class KnowledgeHierarchyDetailPresenterTest extends BasePresenterTest {

    @Mock
    private KnowledgeHierarchyDetailContract.View mView;

    private KnowledgeHierarchyDetailPresenter mKnowledgeHierarchyDetailPresenter;

    @Before
    public void setUp() {
        super.setUp();
        mKnowledgeHierarchyDetailPresenter = new KnowledgeHierarchyDetailPresenter(mDataManager);
        mKnowledgeHierarchyDetailPresenter.attachView(mView);
    }

    @Test
    public void switchProject() {
        RxBus.getDefault().post(new SwitchProjectEvent());
        Mockito.verify(mView).showSwitchProject();
    }

    @Test
    public void switchNavigation() {
        RxBus.getDefault().post(new SwitchNavigationEvent());
        Mockito.verify(mView).showSwitchNavigation();
    }

}