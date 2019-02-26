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
import json.chao.com.wanandroid.contract.project.ProjectContract;


/**
 * @author quchao
 * @date 2018/6/12
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 23)
public class ProjectPresenterTest extends BasePresenterTest {

    @Mock
    private ProjectContract.View mView;

    private ProjectPresenter mProjectPresenter;

    @Before
    public void setUp() {
        super.setUp();
        mProjectPresenter = new ProjectPresenter(mDataManager);
        mProjectPresenter.attachView(mView);
    }

    @Test
    public void getProjectClassifyData() {
        mProjectPresenter.getProjectClassifyData();
        Mockito.verify(mView).showProjectClassifyData(ArgumentMatchers.any());
    }

}