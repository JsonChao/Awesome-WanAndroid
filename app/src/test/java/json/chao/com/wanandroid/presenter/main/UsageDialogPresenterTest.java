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
import json.chao.com.wanandroid.contract.main.UsageDialogContract;


/**
 * @author quchao
 * @date 2018/6/12
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 23)
public class UsageDialogPresenterTest extends BasePresenterTest {

    @Mock
    private UsageDialogContract.View mView;

    private UsageDialogPresenter mUsageDialogPresenter;

    @Before
    public void setUp() {
        super.setUp();
        mUsageDialogPresenter = new UsageDialogPresenter(mDataManager);
        mUsageDialogPresenter.attachView(mView);
    }

    @Test
    public void getUsageSites() {
        mUsageDialogPresenter.getUsefulSites();
        Mockito.verify(mView).showUsefulSites(ArgumentMatchers.any());
    }

}