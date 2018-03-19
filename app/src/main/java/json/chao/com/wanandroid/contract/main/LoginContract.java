package json.chao.com.wanandroid.contract.main;

import json.chao.com.wanandroid.base.presenter.AbstractPresenter;
import json.chao.com.wanandroid.core.bean.BaseResponse;
import json.chao.com.wanandroid.core.bean.main.login.LoginData;
import json.chao.com.wanandroid.base.view.BaseView;

/**
 * @author quchao
 * @date 2018/2/26
 */

public interface LoginContract {

    interface View extends BaseView {

        /**
         * Show login data
         *
         * @param loginResponse BaseResponse<LoginData>
         */
        void showLoginData(BaseResponse<LoginData> loginResponse);

        /**
         * Show register data
         *
         * @param loginResponse BaseResponse<LoginData>
         */
        void showRegisterData(BaseResponse<LoginData> loginResponse);

        /**
         * Show login fail
         */
        void showLoginFail();

        /**
         * Show register fail
         */
        void showRegisterFail();

    }

    interface Presenter extends AbstractPresenter<View> {

        /**
         * Get Login data
         *
         * @param username user name
         * @param password password
         */
        void getLoginData(String username, String password);

        /**
         * 注册
         * http://www.wanandroid.com/user/register
         *
         * @param username user name
         * @param password password
         * @param rePassword re password
         */
        void getRegisterData(String username, String password, String rePassword);
    }
}
