package json.chao.com.wanandroid.presenter.main;

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
import json.chao.com.wanandroid.contract.main.SearchListContract;
import json.chao.com.wanandroid.core.bean.main.collect.FeedArticleData;
import json.chao.com.wanandroid.core.bean.main.collect.FeedArticleListData;
import json.chao.com.wanandroid.core.event.CollectEvent;
import json.chao.com.wanandroid.core.http.cookies.CookiesManager;


/**
 * @author quchao
 * @date 2018/6/11
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 23)
public class SearchListPresenterTest extends BasePresenterTest {

    @Mock
    private SearchListContract.View mView;
    private SearchListPresenter mSearchListPresenter;

    @Before
    public void setUp() {
        super.setUp();
        mSearchListPresenter = new SearchListPresenter(mDataManager);
        mSearchListPresenter.attachView(mView);
    }

    @Test
    public void collectBus() {
        RxBus.getDefault().post(new CollectEvent(false));
        Mockito.verify(mView).showCollectSuccess();
    }

    @Test
    public void cancelCollectBus() {
        RxBus.getDefault().post(new CollectEvent(true));
        Mockito.verify(mView).showCancelCollectSuccess();
    }

    @Test
    public void getSearchListSuccess() {
        mSearchListPresenter.getSearchList(0, "单元测试", true);
        Mockito.verify(mView).showSearchList(ArgumentMatchers.any(FeedArticleListData.class));
    }

    @Test
    public void addCollectFail() {
        CookiesManager.clearAllCookies();
        FeedArticleData feedArticleData = new FeedArticleData();
        feedArticleData.setCollect(false);
        mSearchListPresenter.addCollectArticle(0, feedArticleData);
        Mockito.verify(mView).showErrorMsg(mApplication.getString(R.string.collect_fail));
        Mockito.verify(mView).showError();
    }

    @Test
    public void addCollectSuccess() {
        login();
        FeedArticleData feedArticleData = new FeedArticleData();
        feedArticleData.setCollect(false);
        mSearchListPresenter.addCollectArticle(0, feedArticleData);
        Mockito.verify(mView).showCollectArticleData(ArgumentMatchers.eq(0),
                ArgumentMatchers.eq(feedArticleData),
                ArgumentMatchers.any(FeedArticleListData.class));
    }

    @Test
    public void cancelCollectFail() {
        FeedArticleData feedArticleData = new FeedArticleData();
        feedArticleData.setCollect(true);
        mSearchListPresenter.cancelCollectArticle(0, feedArticleData);
        Mockito.verify(mView).showErrorMsg(mApplication.getString(R.string.cancel_collect_fail));
        Mockito.verify(mView).showError();
    }

    @Test
    public void cancelCollectSuccess() {
        login();
        FeedArticleData feedArticleData = new FeedArticleData();
        feedArticleData.setCollect(false);
        mSearchListPresenter.cancelCollectArticle(0, feedArticleData);
        Mockito.verify(mView).showCancelCollectArticleData(ArgumentMatchers.eq(0),
                ArgumentMatchers.eq(feedArticleData),
                ArgumentMatchers.any(FeedArticleListData.class));
    }

}