package json.chao.com.wanandroid.contract.project;

import java.util.List;

import json.chao.com.wanandroid.base.presenter.AbstractPresenter;
import json.chao.com.wanandroid.core.bean.project.ProjectClassifyData;
import json.chao.com.wanandroid.base.view.AbstractView;

/**
 * @author quchao
 * @date 2018/2/11
 */

public interface ProjectContract {

    interface View extends AbstractView {

        /**
         * Show project classify data
         *
         * @param projectClassifyDataList List<ProjectClassifyData>
         */
        void showProjectClassifyData(List<ProjectClassifyData> projectClassifyDataList);

    }

    interface Presenter extends AbstractPresenter<View> {

        /**
         * Get project classify data
         */
        void getProjectClassifyData();

        /**
         * Get project current page
         *
         * @return project current page
         */
        int getProjectCurrentPage();

        /**
         * Set project current page
         */
        void setProjectCurrentPage(int page);


    }

}
