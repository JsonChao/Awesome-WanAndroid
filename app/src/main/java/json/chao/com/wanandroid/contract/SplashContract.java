package json.chao.com.wanandroid.contract;

import json.chao.com.wanandroid.base.AbstractPresenter;
import json.chao.com.wanandroid.base.BaseView;

/**
 * @author quchao
 * @date 2017/11/29
 */

public interface SplashContract {

    interface View extends BaseView {
        /**
         * after some time jump to main page
         */
        void jumpToMain();
    }

    interface Presenter extends AbstractPresenter<View> {

    }

}
