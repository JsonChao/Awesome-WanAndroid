package json.chao.com.wanandroid.contract.project;

import json.chao.com.wanandroid.base.presenter.AbstractPresenter;
import json.chao.com.wanandroid.core.bean.BaseResponse;
import json.chao.com.wanandroid.core.bean.main.collect.FeedArticleData;
import json.chao.com.wanandroid.core.bean.main.collect.FeedArticleListData;
import json.chao.com.wanandroid.core.bean.project.ProjectListData;
import json.chao.com.wanandroid.base.view.BaseView;

/**
 * @author quchao
 * @date 2018/2/24
 */

public interface ProjectListContract {

    interface View extends BaseView {

        /**
         * Show project list data
         *
         * @param projectListData ProjectListData
         */
        void showProjectListData(ProjectListData projectListData);

        /**
         * Show article list
         *
         * @param position Position
         * @param feedArticleData FeedArticleData
         * @param feedArticleListData FeedArticleListData
         */
        void showCollectOutsideArticle(int position, FeedArticleData feedArticleData, FeedArticleListData feedArticleListData);

        /**
         * Show cancel collect article data
         *
         * @param position Position
         * @param feedArticleData FeedArticleData
         * @param feedArticleListData FeedArticleListData
         */
        void showCancelCollectArticleData(int position, FeedArticleData feedArticleData, FeedArticleListData feedArticleListData);

        /**
         * Show jump to the top
         */
        void showJumpToTheTop();

    }

    interface Presenter extends AbstractPresenter<View> {

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
