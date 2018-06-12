package json.chao.com.wanandroid.presenter.mainpager;

import android.support.annotation.NonNull;

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
import json.chao.com.wanandroid.contract.mainpager.MainPagerContract;
import json.chao.com.wanandroid.core.bean.main.collect.FeedArticleData;
import json.chao.com.wanandroid.core.bean.main.collect.FeedArticleListData;
import json.chao.com.wanandroid.core.event.CollectEvent;
import json.chao.com.wanandroid.core.event.LoginEvent;
import json.chao.com.wanandroid.core.http.cookies.CookiesManager;


/**
 * @author quchao
 * @date 2018/6/12
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 23)
public class MainPagerPresenterTest extends BasePresenterTest {

    @Mock
    private MainPagerContract.View mView;
    private MainPagerPresenter mMainPagerPresenter;

    @Before
    public void setUp() {
        super.setUp();
        mMainPagerPresenter = new MainPagerPresenter(mDataManager);
        mMainPagerPresenter.attachView(mView);
    }

    @Test
    public void collect() {
        CollectEvent collectEvent = new CollectEvent(false);
        RxBus.getDefault().post(collectEvent);
        Mockito.verify(mView).showCollectSuccess();
    }

    @Test
    public void cancelCollect() {
        CollectEvent collectEvent = new CollectEvent(true);
        RxBus.getDefault().post(collectEvent);
        Mockito.verify(mView).showCancelCollectSuccess();
    }

    @Test
    public void loginEvent() {
        LoginEvent loginEvent = new LoginEvent(true);
        RxBus.getDefault().post(loginEvent);
        Mockito.verify(mView).showLoginView();
    }

    @Test
    public void logoutEvent() {
        LoginEvent loginEvent = new LoginEvent(false);
        RxBus.getDefault().post(loginEvent);
        Mockito.verify(mView).showLogoutView();
    }

    @Test
    public void loadMainPagerDataSuccess() {
        login();
        mMainPagerPresenter.loadMainPagerData();
        Mockito.verify(mView).showAutoLoginSuccess();
        Mockito.verify(mView).showBannerData(ArgumentMatchers.any());
        Mockito.verify(mView).showArticleList(ArgumentMatchers.any(FeedArticleListData.class),
                ArgumentMatchers.eq(true));
    }

    @Test
    public void loadMainPagerDataFail() {
        mDataManager.setLoginAccount("");
        mDataManager.setLoginPassword("");
        mMainPagerPresenter.loadMainPagerData();
        Mockito.verify(mView).showAutoLoginFail();
        Mockito.verify(mView).showBannerData(ArgumentMatchers.any());
        Mockito.verify(mView).showArticleList(ArgumentMatchers.any(FeedArticleListData.class),
                ArgumentMatchers.eq(true));
    }

    @Test
    public void getBannerData() {
        mMainPagerPresenter.getBannerData(true);
        Mockito.verify(mView).showBannerData(ArgumentMatchers.any());
    }

    @Test
    public void getFeedArticleList() {
        mMainPagerPresenter.getFeedArticleList(true);
        Mockito.verify(mView).showArticleList(ArgumentMatchers.any(FeedArticleListData.class),
                ArgumentMatchers.eq(true));
    }

    @Test
    public void loadMoreData() {
        mMainPagerPresenter.loadMoreData();
        Mockito.verify(mView).showArticleList(ArgumentMatchers.any(FeedArticleListData.class),
                ArgumentMatchers.eq(true));
    }

    @Test
    public void addCollectArticleSuccess() {
        login();
        FeedArticleData feedArticleData = getFeedArticleData();
        mMainPagerPresenter.addCollectArticle(0, feedArticleData);
        Mockito.verify(mView).showCollectArticleData(ArgumentMatchers.eq(0),
                ArgumentMatchers.eq(feedArticleData),
                ArgumentMatchers.any(FeedArticleListData.class));
    }

    @Test
    public void addCollectArticleFail() {
        CookiesManager.clearAllCookies();
        FeedArticleData feedArticleData = getFeedArticleData();
        mMainPagerPresenter.addCollectArticle(0, feedArticleData);
        Mockito.verify(mView).showErrorMsg(mApplication.getString(R.string.collect_fail));
        Mockito.verify(mView).showError();
    }

    @Test
    public void cancelCollectArticleSuccess() {
        login();
        FeedArticleData feedArticleData = getFeedArticleData();
        mMainPagerPresenter.cancelCollectArticle(0, feedArticleData);
        Mockito.verify(mView).showCancelCollectArticleData(ArgumentMatchers.eq(0),
                ArgumentMatchers.eq(feedArticleData),
                ArgumentMatchers.any(FeedArticleListData.class));
    }

    @Test
    public void cancelCollectArticleFail() {
        CookiesManager.clearAllCookies();
        FeedArticleData feedArticleData = getFeedArticleData();
        mMainPagerPresenter.cancelCollectArticle(0, feedArticleData);
        Mockito.verify(mView).showErrorMsg(mApplication.getString(R.string.cancel_collect_fail));
        Mockito.verify(mView).showError();
    }

    @NonNull
    public FeedArticleData getFeedArticleData() {
        FeedArticleData feedArticleData = new FeedArticleData();
        feedArticleData.setId(1100);
        feedArticleData.setCollect(false);
        return feedArticleData;
    }

}