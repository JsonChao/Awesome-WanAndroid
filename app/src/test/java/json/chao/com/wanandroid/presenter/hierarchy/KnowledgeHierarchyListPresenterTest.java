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
import json.chao.com.wanandroid.R;
import json.chao.com.wanandroid.component.RxBus;
import json.chao.com.wanandroid.contract.hierarchy.KnowledgeHierarchyListContract;
import json.chao.com.wanandroid.core.bean.main.collect.FeedArticleData;
import json.chao.com.wanandroid.core.bean.main.collect.FeedArticleListData;
import json.chao.com.wanandroid.core.event.CollectEvent;
import json.chao.com.wanandroid.core.event.KnowledgeJumpTopEvent;
import json.chao.com.wanandroid.core.event.ReloadDetailEvent;
import json.chao.com.wanandroid.core.http.cookies.CookiesManager;


/**
 * @author quchao
 * @date 2018/6/12
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 23)
public class KnowledgeHierarchyListPresenterTest extends BasePresenterTest {

    @Mock
    private KnowledgeHierarchyListContract.View mView;

    private KnowledgeHierarchyListPresenter mKnowledgeHierarchyListPresenter;

    @Before
    public void setUp() {
        super.setUp();
        mKnowledgeHierarchyListPresenter = new KnowledgeHierarchyListPresenter(mDataManager);
        mKnowledgeHierarchyListPresenter.attachView(mView);
    }

    @Test
    public void collect() {
        RxBus.getDefault().post(new CollectEvent(false));
        Mockito.verify(mView).showCollectSuccess();
    }

    @Test
    public void cancelCollect() {
        RxBus.getDefault().post(new CollectEvent(true));
        Mockito.verify(mView).showCancelCollectSuccess();
    }

    @Test
    public void KnowledgeJumpTop() {
        RxBus.getDefault().post(new KnowledgeJumpTopEvent());
        Mockito.verify(mView).showJumpTheTop();
    }

    @Test
    public void reloadDetail() {
        RxBus.getDefault().post(new ReloadDetailEvent());
        Mockito.verify(mView).showReloadDetailEvent();
    }

    @Test
    public void getKnowledgeHierarchyDetailData() {
        mKnowledgeHierarchyListPresenter.getKnowledgeHierarchyDetailData(0, 1010, true);
        Mockito.verify(mView).showKnowledgeHierarchyDetailData(ArgumentMatchers.any(FeedArticleListData.class));
    }

    @Test
    public void addCollectArticleFail() {
        CookiesManager.clearAllCookies();
        FeedArticleData feedArticleData = new FeedArticleData();
        feedArticleData.setId(1100);
        feedArticleData.setCollect(false);
        mKnowledgeHierarchyListPresenter.addCollectArticle(0, feedArticleData);
        Mockito.verify(mView).showErrorMsg(mApplication.getString(R.string.collect_fail));
        Mockito.verify(mView).showError();
    }

    @Test
    public void addCollectArticleSuccess() {
        login();
        FeedArticleData feedArticleData = new FeedArticleData();
        feedArticleData.setId(1100);
        feedArticleData.setCollect(false);
        mKnowledgeHierarchyListPresenter.addCollectArticle(0, feedArticleData);
        Mockito.verify(mView).showCollectArticleData(ArgumentMatchers.eq(0),
                ArgumentMatchers.eq(feedArticleData),
                ArgumentMatchers.any(FeedArticleListData.class));
    }

    @Test
    public void cancelCollectArticleFail() {
        CookiesManager.clearAllCookies();
        FeedArticleData feedArticleData = new FeedArticleData();
        feedArticleData.setId(1100);
        feedArticleData.setCollect(true);
        mKnowledgeHierarchyListPresenter.cancelCollectArticle(0, feedArticleData);
        Mockito.verify(mView).showErrorMsg(mApplication.getString(R.string.cancel_collect_fail));
        Mockito.verify(mView).showError();
    }

    @Test
    public void cancelCollectArticleSuccess() {
        login();
        FeedArticleData feedArticleData = new FeedArticleData();
        feedArticleData.setId(1100);
        feedArticleData.setCollect(false);
        mKnowledgeHierarchyListPresenter.cancelCollectArticle(0, feedArticleData);
        Mockito.verify(mView).showCancelCollectArticleData(ArgumentMatchers.eq(0),
                ArgumentMatchers.eq(feedArticleData),
                ArgumentMatchers.any(FeedArticleListData.class));
    }

}