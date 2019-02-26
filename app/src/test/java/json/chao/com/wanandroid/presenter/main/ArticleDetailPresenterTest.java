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
import json.chao.com.wanandroid.contract.main.ArticleDetailContract;
import json.chao.com.wanandroid.core.bean.main.collect.FeedArticleListData;
import json.chao.com.wanandroid.core.http.cookies.CookiesManager;


/**
 * @author quchao
 * @date 2018/6/11
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 23)
public class ArticleDetailPresenterTest extends BasePresenterTest {

    @Mock
    private ArticleDetailContract.View mView;

    private ArticleDetailPresenter mArticleDetailPresenter;

    @Before
    public void setUp() {
        super.setUp();
        mArticleDetailPresenter = new ArticleDetailPresenter(mDataManager);
        mArticleDetailPresenter.attachView(mView);
    }

    @Test
    public void collectFail() {
        CookiesManager.clearAllCookies();
        mArticleDetailPresenter.addCollectArticle(0);
        Mockito.verify(mView).showErrorMsg(mApplication.getString(R.string.collect_fail));
        Mockito.verify(mView).showError();
    }

    @Test
    public void collectSuccess() {
        login();
        mArticleDetailPresenter.addCollectArticle(0);
        Mockito.verify(mView).showCollectArticleData(ArgumentMatchers.any(FeedArticleListData.class));
    }

    @Test
    public void unCollectFail() {
        CookiesManager.clearAllCookies();
        mArticleDetailPresenter.cancelCollectArticle(0);
        Mockito.verify(mView).showErrorMsg(mApplication.getString(R.string.cancel_collect_fail));
        Mockito.verify(mView).showError();
    }

    @Test
    public void unCollectSuccess() {
        login();
        mArticleDetailPresenter.cancelCollectArticle(0);
        Mockito.verify(mView).showCancelCollectArticleData(ArgumentMatchers.any(FeedArticleListData.class));
    }

    @Test
    public void unCollectPagerFail() {
        CookiesManager.clearAllCookies();
        mArticleDetailPresenter.cancelCollectPageArticle(0);
        Mockito.verify(mView).showErrorMsg(mApplication.getString(R.string.cancel_collect_fail));
        Mockito.verify(mView).showError();
    }

    @Test
    public void unCollectPagerSuccess() {
        login();
        mArticleDetailPresenter.cancelCollectPageArticle(0);
        Mockito.verify(mView).showCancelCollectArticleData(ArgumentMatchers.any(FeedArticleListData.class));
    }

}