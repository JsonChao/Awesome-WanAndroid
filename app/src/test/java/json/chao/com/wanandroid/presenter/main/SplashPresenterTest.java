package json.chao.com.wanandroid.presenter.main;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import json.chao.com.wanandroid.BasePresenterTest;
import json.chao.com.wanandroid.BuildConfig;
import json.chao.com.wanandroid.contract.main.SplashContract;

import static org.junit.Assert.*;

/**
 * @author quchao
 * @date 2018/6/12
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 23)
public class SplashPresenterTest extends BasePresenterTest {

    @Mock
    private SplashContract.View mView;

    private SplashPresenter mSplashPresenter;

    @Before
    public void setUp() {
        super.setUp();
        mSplashPresenter = new SplashPresenter(mDataManager);
        mSplashPresenter.attachView(mView);
    }

    @Test
    public void jumpToMain() throws InterruptedException {
        Thread.sleep(3000);
        Mockito.verify(mView).jumpToMain();
    }

}