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
import json.chao.com.wanandroid.contract.main.LoginContract;


/**
 * @author quchao
 * @date 2018/6/8
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 23)
public class LoginPresenterTest extends BasePresenterTest {

    @Test
    public void noInputUsernameOrPassword() {
        mLoginPresenter.getLoginData("", "");
        Mockito.verify(mView).showSnackBar(mApplication.getString(R.string.account_password_null_tint));
    }

    @Test
    public void inputErrorUserNameOrPassword() {
        mLoginPresenter.getLoginData("2243963927", "qaz");
        Mockito.verify(mView).showErrorMsg(mApplication.getString(R.string.login_fail));
        Mockito.verify(mView).showError();
    }

    @Test
    public void inputCorrectUserNameAndPassword() {
        login();
        Mockito.verify(mView).showLoginSuccess();
    }

}