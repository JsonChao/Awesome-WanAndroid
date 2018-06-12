package json.chao.com.wanandroid.presenter.main;

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
import json.chao.com.wanandroid.contract.main.MainContract;
import json.chao.com.wanandroid.core.event.AutoLoginEvent;
import json.chao.com.wanandroid.core.event.LoginEvent;
import json.chao.com.wanandroid.core.event.NightModeEvent;
import json.chao.com.wanandroid.core.event.SwitchNavigationEvent;
import json.chao.com.wanandroid.core.event.SwitchProjectEvent;


/**
 * @author quchao
 * @date 2018/6/11
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 23)
public class MainPresenterTest extends BasePresenterTest{

    @Mock
    private MainContract.View mView;

    private MainPresenter mMainPresenter;

    @Before
    public void setUp() {
        super.setUp();
        mMainPresenter = new MainPresenter(mDataManager);
        mMainPresenter.attachView(mView);
    }

    @Test
    public void nightModeBus() {
        RxBus.getDefault().post(new NightModeEvent(true));
        Mockito.verify(mView).useNightMode(true);
        RxBus.getDefault().post(new NightModeEvent(false));
        Mockito.verify(mView).useNightMode(false);
    }

    @Test
    public void loginBus() {
        RxBus.getDefault().post(new LoginEvent(true));
        Mockito.verify(mView).showLoginView();
    }

    @Test
    public void logoutBus() {
        RxBus.getDefault().post(new LoginEvent(false));
        Mockito.verify(mView).showLogoutView();
    }

    @Test
    public void autoLoginBus() {
        RxBus.getDefault().post(new AutoLoginEvent());
        Mockito.verify(mView).showAutoLoginView();
    }

    @Test
    public void switchProjectBus() {
        RxBus.getDefault().post(new SwitchProjectEvent());
        Mockito.verify(mView).showSwitchProject();
    }

    @Test
    public void switchNavigation() {
        RxBus.getDefault().post(new SwitchNavigationEvent());
        Mockito.verify(mView).showSwitchNavigation();
    }

}