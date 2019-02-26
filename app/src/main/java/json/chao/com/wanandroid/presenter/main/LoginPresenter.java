package json.chao.com.wanandroid.presenter.main;

import android.text.TextUtils;

import javax.inject.Inject;

import json.chao.com.wanandroid.R;
import json.chao.com.wanandroid.app.WanAndroidApp;
import json.chao.com.wanandroid.component.RxBus;
import json.chao.com.wanandroid.core.DataManager;
import json.chao.com.wanandroid.base.presenter.BasePresenter;
import json.chao.com.wanandroid.contract.main.LoginContract;
import json.chao.com.wanandroid.core.bean.main.login.LoginData;
import json.chao.com.wanandroid.core.event.LoginEvent;
import json.chao.com.wanandroid.utils.RxUtils;
import json.chao.com.wanandroid.widget.BaseObserver;

/**
 * @author quchao
 * @date 2018/2/26
 */
public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter {

    private DataManager mDataManager;

    @Inject
    public LoginPresenter(DataManager dataManager) {
        super(dataManager);
        mDataManager = dataManager;
    }

    @Override
    public void getLoginData(String username, String password) {
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            mView.showSnackBar(WanAndroidApp.getInstance().getString(R.string.account_password_null_tint));
            return;
        }
        addSubscribe(mDataManager.getLoginData(username, password)
                .compose(RxUtils.rxSchedulerHelper())
                .compose(RxUtils.handleResult())
                .subscribeWith(new BaseObserver<LoginData>(mView,
                        WanAndroidApp.getInstance().getString(R.string.login_fail)) {
                    @Override
                    public void onNext(LoginData loginData) {
                        setLoginAccount(loginData.getUsername());
                        setLoginPassword(loginData.getPassword());
                        setLoginStatus(true);
                        RxBus.getDefault().post(new LoginEvent(true));
                        mView.showLoginSuccess();
                    }
                }));
    }
}
