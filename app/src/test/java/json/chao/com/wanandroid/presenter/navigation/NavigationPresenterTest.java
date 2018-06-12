package json.chao.com.wanandroid.presenter.navigation;

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
import json.chao.com.wanandroid.contract.navigation.NavigationContract;

import static org.junit.Assert.*;

/**
 * @author quchao
 * @date 2018/6/12
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 23)
public class NavigationPresenterTest extends BasePresenterTest {

    @Mock
    private NavigationContract.View mView;
    private NavigationPresenter mNavigationPresenter;

    @Before
    public void setUp() {
        super.setUp();
        mNavigationPresenter = new NavigationPresenter(mDataManager);
        mNavigationPresenter.attachView(mView);
    }

    @Test
    public void getNavigationListData() {
        mNavigationPresenter.getNavigationListData(true);
        Mockito.verify(mView).showNavigationListData(ArgumentMatchers.any());
    }

}