package json.chao.com.wanandroid.presenter.project;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import json.chao.com.wanandroid.R;
import json.chao.com.wanandroid.RxJavaRuler;
import json.chao.com.wanandroid.app.WanAndroidApp;
import json.chao.com.wanandroid.contract.project.ProjectContract;
import json.chao.com.wanandroid.core.DataManager;
import json.chao.com.wanandroid.core.bean.project.ProjectClassifyData;
import json.chao.com.wanandroid.utils.RxUtils;
import json.chao.com.wanandroid.widget.BaseObserver;

/**
 * @author quchao
 * @date 2018/6/5
 */

public class ProjectPresenterTest {

    private ProjectPresenter mProjectPresenter;

    @Mock
    DataManager mDataManager;

    @Mock
    ProjectContract.View mView;

    @Rule
    public MockitoRule mMockitoRule = MockitoJUnit.rule();

    @Rule
    public RxJavaRuler mRxJavaRuler = new RxJavaRuler();

    @Before
    public void setUp() {

    }

    @Test
    public void getProjectClassifyData() {

    }
}