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
         */
        void showLoginSuccess();
    }

    interface Presenter extends AbstractPresenter<View> {

        /**
         * Get Login data
         *
         * @param username user name
         * @param password password
         */
        void getLoginData(String username, String password);
    }
}
