package json.chao.com.wanandroid.contract.project;

import java.util.List;

import json.chao.com.wanandroid.base.presenter.AbstractPresenter;
import json.chao.com.wanandroid.core.bean.BaseResponse;
import json.chao.com.wanandroid.core.bean.project.ProjectClassifyData;
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
         * @param projectClassifyResponse List<ProjectClassifyData>
         */
        void showProjectClassifyData(BaseResponse<List<ProjectClassifyData>> projectClassifyResponse);

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
