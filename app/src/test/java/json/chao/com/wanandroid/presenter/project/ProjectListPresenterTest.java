package json.chao.com.wanandroid.presenter.project;

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
import json.chao.com.wanandroid.contract.project.ProjectListContract;
import json.chao.com.wanandroid.core.bean.main.collect.FeedArticleData;
import json.chao.com.wanandroid.core.bean.main.collect.FeedArticleListData;
import json.chao.com.wanandroid.core.bean.project.ProjectListData;
import json.chao.com.wanandroid.core.event.JumpToTheTopEvent;
import json.chao.com.wanandroid.core.http.cookies.CookiesManager;


/**
 * @author quchao
 * @date 2018/6/12
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 23)
public class ProjectListPresenterTest extends BasePresenterTest {

    @Mock
    private ProjectListContract.View mView;
    private ProjectListPresenter mProjectListPresenter;

    @Before
    public void setUp() {
        super.setUp();
        mProjectListPresenter = new ProjectListPresenter(mDataManager);
        mProjectListPresenter.attachView(mView);
    }

    @Test
    public void JumpToTheTop() {
        JumpToTheTopEvent jumpToTheTopEvent = new JumpToTheTopEvent();
        RxBus.getDefault().post(jumpToTheTopEvent);
        Mockito.verify(mView).showJumpToTheTop();
    }

    @Test
    public void getProjectListData() {
        mProjectListPresenter.getProjectListData(0, 0, true);
        Mockito.verify(mView).showProjectListData(ArgumentMatchers.any(ProjectListData.class));
    }

    @Test
    public void addCollectOutsideArticleSuccess() {
        login();
        FeedArticleData feedArticleData = new FeedArticleData();
        feedArticleData.setTitle("个人博客");
        feedArticleData.setAuthor("JsonChao");
        feedArticleData.setLink("www.github.io");
        feedArticleData.setCollect(false);
        mProjectListPresenter.addCollectOutsideArticle(0, feedArticleData);
        Mockito.verify(mView).showCollectOutsideArticle(ArgumentMatchers.eq(0),
                ArgumentMatchers.eq(feedArticleData),
                ArgumentMatchers.any(FeedArticleListData.class));
    }

    @Test
    public void addCollectOutsideArticleFail() {
        CookiesManager.clearAllCookies();
        FeedArticleData feedArticleData = new FeedArticleData();
        feedArticleData.setId(0);
        feedArticleData.setCollect(false);
        mProjectListPresenter.addCollectOutsideArticle(0, feedArticleData);
        Mockito.verify(mView).showErrorMsg(mApplication.getString(R.string.collect_fail));
        Mockito.verify(mView).showError();
    }

    @Test
    public void cancelCollectArticleSuccess() {
        login();
        FeedArticleData feedArticleData = new FeedArticleData();
        feedArticleData.setId(0);
        feedArticleData.setCollect(true);
        mProjectListPresenter.cancelCollectArticle(0, feedArticleData);
        Mockito.verify(mView).showCancelCollectArticleData(ArgumentMatchers.eq(0),
                ArgumentMatchers.eq(feedArticleData),
                ArgumentMatchers.any(FeedArticleListData.class));
    }

    @Test
    public void cancelCollectArticleFail() {
        CookiesManager.clearAllCookies();
        FeedArticleData feedArticleData = new FeedArticleData();
        feedArticleData.setId(0);
        feedArticleData.setCollect(true);
        mProjectListPresenter.cancelCollectArticle(0, feedArticleData);
        Mockito.verify(mView).showErrorMsg(mApplication.getString(R.string.cancel_collect_fail));
        Mockito.verify(mView).showError();
    }

}