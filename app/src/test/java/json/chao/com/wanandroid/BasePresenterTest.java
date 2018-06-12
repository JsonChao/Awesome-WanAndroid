package json.chao.com.wanandroid;

import android.app.Application;

import org.junit.Before;
import org.junit.Rule;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.shadows.ShadowLog;

import json.chao.com.wanandroid.app.WanAndroidApp;
import json.chao.com.wanandroid.contract.main.LoginContract;
import json.chao.com.wanandroid.core.DataManager;
import json.chao.com.wanandroid.presenter.main.LoginPresenter;
import json.chao.com.wanandroid.rule.MyRuler;
import json.chao.com.wanandroid.rule.RxJavaRuler;
import json.chao.com.wanandroid.rule.RxJavaTestSchedulerRule;

/**
 * @author quchao
 * @date 2018/6/11
 */
public class BasePresenterTest {

    @Rule
    public MyRuler mMyRuler = new MyRuler();

    @Rule
    public RxJavaRuler mRxJavaRuler = new RxJavaRuler();

    @Rule
    public RxJavaTestSchedulerRule mRxJavaTestSchedulerRule = new RxJavaTestSchedulerRule();

    @Rule
    public MockitoRule mMockitoRule = MockitoJUnit.rule();

    protected Application mApplication;
    protected DataManager mDataManager;

    @Mock
    protected LoginContract.View mView;

    protected LoginPresenter mLoginPresenter;

    @Before
    public void setUp() {
        ShadowLog.stream = System.out;
        mApplication = RuntimeEnvironment.application;
        mDataManager = WanAndroidApp.getAppComponent().getDataManager();
        mLoginPresenter = new LoginPresenter(mDataManager);
        mLoginPresenter.attachView(mView);
    }

    protected void login() {
        mLoginPresenter.getLoginData("2243963927", "qaz123");
    }

}
