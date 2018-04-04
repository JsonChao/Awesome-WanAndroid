package json.chao.com.wanandroid.presenter.main;

import android.text.TextUtils;

import javax.inject.Inject;

import json.chao.com.wanandroid.core.DataManager;
import json.chao.com.wanandroid.base.presenter.BasePresenter;
import json.chao.com.wanandroid.contract.main.LoginContract;
import json.chao.com.wanandroid.core.bean.BaseResponse;
import json.chao.com.wanandroid.core.bean.main.login.LoginData;
import json.chao.com.wanandroid.utils.RxUtils;
import json.chao.com.wanandroid.widget.BaseObserver;

/**
 * @author quchao
 * @date 2018/2/26
 */

public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter {

    private DataManager mDataManager;

    @Inject
    LoginPresenter(DataManager dataManager) {
        super(dataManager);
        mDataManager = dataManager;
    }

    @Override
    public void getLoginData(String username, String password) {
        addSubscribe(mDataManager.getLoginData(username, password)
                        .compose(RxUtils.rxSchedulerHelper())
                        .subscribeWith(new BaseObserver<BaseResponse<LoginData>>(mView) {
                            @Override
                            public void onNext(BaseResponse<LoginData> loginResponse) {
                                if (loginResponse.getErrorCode() == BaseResponse.SUCCESS) {
                                    mView.showLoginData(loginResponse);
                                } else {
                                    mView.showLoginFail();
                                }
                            }
                        }));
    }

    @Override
    public void getRegisterData(String username, String password, String rePassword) {
        addSubscribe(mDataManager.getRegisterData(username, password, rePassword)
                        .compose(RxUtils.rxSchedulerHelper())
                        .filter(loginResponse -> !TextUtils.isEmpty(username)
                                && !TextUtils.isEmpty(password)
                                && !TextUtils.isEmpty(rePassword))
                        .subscribeWith(new BaseObserver<BaseResponse<LoginData>>(mView) {
                            @Override
                            public void onNext(BaseResponse<LoginData> loginResponse) {
                                if (loginResponse.getErrorCode() == BaseResponse.SUCCESS) {
                                    mView.showRegisterData(loginResponse);
                                } else {
                                    mView.showRegisterFail();
                                }
                            }
                        }));
    }


}
