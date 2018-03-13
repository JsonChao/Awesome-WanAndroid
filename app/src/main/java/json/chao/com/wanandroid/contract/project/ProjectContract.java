package json.chao.com.wanandroid.contract.project;

import json.chao.com.wanandroid.base.presenter.AbstractPresenter;
import json.chao.com.wanandroid.core.bean.project.ProjectClassifyResponse;
import json.chao.com.wanandroid.base.view.BaseView;

/**
 * @author quchao
 * @date 2018/2/11
 */

public interface ProjectContract {

    interface View extends BaseView {

        /**
         * Show project classify data
         *
         * @param projectClassifyResponse ProjectClassifyResponse
         */
        void showProjectClassifyData(ProjectClassifyResponse projectClassifyResponse);

        /**
         * Show project calssify data fail
         */
        void showProjectClassifyDataFail();

    }

    interface Presenter extends AbstractPresenter<View> {

        /**
         * Get project classify data
         */
        void getProjectClassifyData();
    }

}
