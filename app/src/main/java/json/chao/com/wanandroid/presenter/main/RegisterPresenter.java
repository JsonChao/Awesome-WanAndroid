package json.chao.com.wanandroid.presenter.main;

import android.text.TextUtils;

import javax.inject.Inject;

import json.chao.com.wanandroid.R;
import json.chao.com.wanandroid.app.WanAndroidApp;
import json.chao.com.wanandroid.base.presenter.BasePresenter;
import json.chao.com.wanandroid.contract.main.RegisterContract;
import json.chao.com.wanandroid.core.DataManager;
import json.chao.com.wanandroid.core.bean.main.login.LoginData;
import json.chao.com.wanandroid.utils.CommonUtils;
import json.chao.com.wanandroid.utils.RxUtils;
import json.chao.com.wanandroid.widget.BaseObserver;

/**
 * @author quchao
 * @date 2018/5/4
 */
public class RegisterPresenter extends BasePresenter<RegisterContract.View> implements RegisterContract.Presenter {

    private DataManager mDataManager;

    @Inject
    public RegisterPresenter(DataManager dataManager) {
        super(dataManager);
        this.mDataManager = dataManager;
    }

    @Override
    public void getRegisterData(String username, String password, String rePassword) {
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password) || TextUtils.isEmpty(rePassword)) {
            mView.showSnackBar(WanAndroidApp.getInstance().getString(R.string.account_password_null_tint));
            return;
        }
        if (!password.equals(rePassword)) {
            mView.showSnackBar(WanAndroidApp.getInstance().getString(R.string.password_not_same));
            return;
        }
        addSubscribe(mDataManager.getRegisterData(username, password, rePassword)
                .compose(RxUtils.rxSchedulerHelper())
                .compose(RxUtils.handleResult())
                .filter(loginResponse -> !TextUtils.isEmpty(username)
                        && !TextUtils.isEmpty(password)
                        && !TextUtils.isEmpty(rePassword))
                .subscribeWith(new BaseObserver<LoginData>(mView,
                        WanAndroidApp.getInstance().getString(R.string.register_fail)) {
                    @Override
                    public void onNext(LoginData loginData) {
                        mView.showRegisterSuccess();
                    }
                }));
    }
}
