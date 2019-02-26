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
import json.chao.com.wanandroid.contract.main.CollectContract;
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
public class CollectPresenterTest extends BasePresenterTest {

    @Mock
    private CollectContract.View mView;

    private CollectPresenter mCollectPresenter;

    @Before
    public void setUp() {
        super.setUp();
        mCollectPresenter = new CollectPresenter(mDataManager);
        mCollectPresenter.attachView(mView);
    }

    @Test
    public void collectBus() {
        RxBus.getDefault().post(new CollectEvent(false));
        Mockito.verify(mView).showRefreshEvent();
    }

    @Test
    public void getCollectListError() {
        //未登录（清空Cookies）获取收藏列表
        CookiesManager.clearAllCookies();
        mCollectPresenter.getCollectList(0, false);
        Mockito.verify(mView).showErrorMsg(mApplication.getString(R.string.failed_to_obtain_collection_data));
    }

    @Test
    public void getCollectListErrorShow() {
        CookiesManager.clearAllCookies();
        mCollectPresenter.getCollectList(0, true);
        Mockito.verify(mView).showErrorMsg(mApplication.getString(R.string.failed_to_obtain_collection_data));
        Mockito.verify(mView).showError();
    }

    @Test
    public void getCollectListSuccess() {
        //获取Collect列表前需先登陆获取Cookie
        login();
        mCollectPresenter.getCollectList(0, true);
        Mockito.verify(mView).showCollectList(ArgumentMatchers.any(FeedArticleListData.class));
    }

    @Test
    public void cancelCollectFail() {
        CookiesManager.clearAllCookies();
        FeedArticleData feedArticleData = new FeedArticleData();
        feedArticleData.setCollect(true);
        mCollectPresenter.cancelCollectPageArticle(0, feedArticleData);
        Mockito.verify(mView).showErrorMsg(mApplication.getString(R.string.cancel_collect_fail));
        Mockito.verify(mView).showError();
    }

    @Test
    public void cancelCollectSuccess() {
        login();
        FeedArticleData feedArticleData = new FeedArticleData();
        feedArticleData.setCollect(true);
        mCollectPresenter.cancelCollectPageArticle(0, feedArticleData);
        //当使用eq()将对象转化为Equals参数匹配器时，里面的值可能与原来的不对应，因此此时只能验证类型，不能验证其中的值
        feedArticleData.setCollect(false);
        //使用verify验证时，要么都是用原始值，要么都是用匹配器，eq()用于将原始值转化为匹配器值
        Mockito.verify(mView).showCancelCollectPageArticleData(ArgumentMatchers.eq(0),
                ArgumentMatchers.eq(feedArticleData),
                ArgumentMatchers.any(FeedArticleListData.class));
    }

}