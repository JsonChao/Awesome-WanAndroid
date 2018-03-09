package json.chao.com.wanandroid.contract;

import json.chao.com.wanandroid.core.bean.NavigationListResponse;
import json.chao.com.wanandroid.base.BasePresenter;
import json.chao.com.wanandroid.base.BaseView;

/**
 * @author quchao
 * @date 2018/2/11
 */

public interface NavigationContract {

    interface View extends BaseView {

        /**
         * Show navigation list data
         *
         * @param navigationListResponse NavigationListResponse
         */
        void showNavigationListData(NavigationListResponse navigationListResponse);

        /**
         * Show navigation list fail
         */
        void showNavigationListFail();
    }

    interface Presenter extends BasePresenter<NavigationContract.View> {

        /**
         * Get navigation list data
         */
        void getNavigationListData();
    }

}
