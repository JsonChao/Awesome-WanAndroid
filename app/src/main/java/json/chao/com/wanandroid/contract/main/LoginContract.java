package json.chao.com.wanandroid.contract.main;

import json.chao.com.wanandroid.base.presenter.AbstractPresenter;
import json.chao.com.wanandroid.core.bean.main.login.LoginData;
import json.chao.com.wanandroid.base.view.AbstractView;

/**
 * @author quchao
 * @date 2018/2/26
 */

public interface LoginContract {

    interface View extends AbstractView {

        /**
         * Show login data
         *
         * @param loginData LoginData
         */
        void showLoginData(LoginData loginData);

        /**
         * Show register data
         *
         * @param loginData LoginData
         */
        void showRegisterData(LoginData loginData);

    }

    interface Presenter extends AbstractPresenter<View> {

        /**
         * Set login status
         *
         * @param account account
         */
        void setLoginAccount(String account);

        /**
         * Set login password
         *
         * @param password password
         */
        void setLoginPassword(String password);

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
