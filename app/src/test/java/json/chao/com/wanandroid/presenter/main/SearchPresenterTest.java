package json.chao.com.wanandroid.presenter.main;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.List;

import json.chao.com.wanandroid.BasePresenterTest;
import json.chao.com.wanandroid.BuildConfig;
import json.chao.com.wanandroid.contract.main.SearchContract;
import json.chao.com.wanandroid.core.bean.main.search.TopSearchData;


/**
 * @author quchao
 * @date 2018/6/12
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 23)
public class SearchPresenterTest extends BasePresenterTest {

    @Mock
    private SearchContract.View mView;

    private SearchPresenter mSearchPresenter;

    @Before
    public void setUp() {
        super.setUp();
        mSearchPresenter = new SearchPresenter(mDataManager);
        mSearchPresenter.attachView(mView);
    }

//    @Test
//    public void addHistoryDataSuccess() {
//        mSearchPresenter.clearHistoryData();
//        mSearchPresenter.addHistoryData("ok");
//        Mockito.verify(mView).judgeToTheSearchListActivity();
//    }

    @Test
    public void getTopSearchDataSuccess() {
        mSearchPresenter.getTopSearchData();
        Mockito.verify(mView).showTopSearchData(ArgumentMatchers.any());
    }

}