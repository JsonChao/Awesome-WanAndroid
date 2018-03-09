package json.chao.com.wanandroid.contract;

import json.chao.com.wanandroid.core.bean.FeedArticleData;
import json.chao.com.wanandroid.core.bean.FeedArticleListResponse;
import json.chao.com.wanandroid.core.bean.ProjectListResponse;
import json.chao.com.wanandroid.base.BasePresenter;
import json.chao.com.wanandroid.base.BaseView;

/**
 * @author quchao
 * @date 2018/2/24
 */

public interface ProjectListContract {

    interface View extends BaseView {

        /**
         * Show project list data
         *
         * @param projectListResponse ProjectListResponse
         */
        void showProjectListData(ProjectListResponse projectListResponse);

        /**
         * Show article list
         *
         * @param position Position
         * @param feedArticleData FeedArticleData
         * @param feedArticleListResponse FeedArticleListResponse
         */
        void showCollectOutsideArticle(int position, FeedArticleData feedArticleData, FeedArticleListResponse feedArticleListResponse);

        /**
         * Show cancel collect article data
         *
         * @param position Position
         * @param feedArticleData FeedArticleData
         * @param feedArticleListResponse FeedArticleListResponse
         */
        void showCancelCollectArticleData(int position, FeedArticleData feedArticleData, FeedArticleListResponse feedArticleListResponse);

        /**
         * Show project list fail
         */
        void showProjectListFail();

    }

    interface Presenter extends BasePresenter<ProjectListContract.View> {

        /**
         * Get project list data
         *
         * @param page page num
         * @param cid second page id
         */
        void getProjectListData(int page, int cid);

        /**
         * Add collect outside article
         *
         * @param position Position
         * @param feedArticleData FeedArticleData
         */
        void addCollectOutsideArticle(int position, FeedArticleData feedArticleData);

        /**
         * Cancel collect article
         *
         * @param position Position
         * @param feedArticleData FeedArticleData
         */
        void cancelCollectArticle(int position, FeedArticleData feedArticleData);
    }

}
