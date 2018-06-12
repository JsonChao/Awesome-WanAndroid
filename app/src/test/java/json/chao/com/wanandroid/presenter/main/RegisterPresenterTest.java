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
import json.chao.com.wanandroid.R;
import json.chao.com.wanandroid.app.WanAndroidApp;
import json.chao.com.wanandroid.contract.main.RegisterContract;


/**
 * @author quchao
 * @date 2018/6/11
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 23)
public class RegisterPresenterTest extends BasePresenterTest {

    @Mock
    private RegisterContract.View mView;

    private RegisterPresenter mRegisterPresenter;

    @Before
    public void setUp() {
        super.setUp();
        mRegisterPresenter = new RegisterPresenter(mDataManager);
        mRegisterPresenter.attachView(mView);
    }

    @Test
    public void noInputAllInfo() {
        mRegisterPresenter.getRegisterData("JsonChao", "", "123");
        Mockito.verify(mView).showSnackBar(mApplication.getString(R.string.account_password_null_tint));
    }

    @Test
    public void inputPasswordMismatching() {
        mRegisterPresenter.getRegisterData("JsonChao", "qaz", "qaz123");
        Mockito.verify(mView).showSnackBar(mApplication.getString(R.string.password_not_same));
    }

    @Test
    public void inputCorrectInfo() {
        //注意：注册账号密码只能成功注册一次
        mRegisterPresenter.getRegisterData("13458524151", "qaz123", "qaz123");
        Mockito.verify(mView).showRegisterSuccess();
    }

    @Test
    public void inputExistInfo() {
        mRegisterPresenter.getRegisterData("2243963927", "qaz123", "qaz123");
        Mockito.verify(mView).showErrorMsg(mApplication.getString(R.string.register_fail));
        Mockito.verify(mView).showError();
    }

}