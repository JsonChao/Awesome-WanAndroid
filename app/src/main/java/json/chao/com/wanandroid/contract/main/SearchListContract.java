package json.chao.com.wanandroid.contract.main;


import json.chao.com.wanandroid.base.presenter.AbstractPresenter;
import json.chao.com.wanandroid.base.view.BaseView;
import json.chao.com.wanandroid.core.bean.main.collect.FeedArticleData;
import json.chao.com.wanandroid.core.bean.main.collect.FeedArticleListResponse;


/**
 * @author quchao
 * @date 2018/3/13
 */

public interface SearchListContract {

    interface View extends BaseView {

        /**
         * Show search list
         *
         * @param feedArticleListResponse FeedArticleListResponse
         */
        void showSearchList(FeedArticleListResponse feedArticleListResponse);

        /**
         * Show collect article data
         *
         * @param position Position
         * @param feedArticleData FeedArticleData
         * @param feedArticleListResponse FeedArticleListResponse
         */
        void showCollectArticleData(int position, FeedArticleData feedArticleData, FeedArticleListResponse feedArticleListResponse);

        /**
         * Show cancel collect article data
         *
         * @param position Position
         * @param feedArticleData FeedArticleData
         * @param feedArticleListResponse FeedArticleListResponse
         */
        void showCancelCollectArticleData(int position, FeedArticleData feedArticleData, FeedArticleListResponse feedArticleListResponse);

        /**
         * Show search list fail
         */
        void showSearchListFail();

    }

    interface Presenter extends AbstractPresenter<SearchListContract.View> {

        /**
         * 搜索
         * @param page page
         * @param k POST search key
         */
        void getSearchList(int page, String k);

        /**
         * Add collect article
         *
         * @param position Position
         * @param feedArticleData FeedArticleData
         */
        void addCollectArticle(int position, FeedArticleData feedArticleData);

        /**
         * Cancel collect article
         *
         * @param position Position
         * @param feedArticleData FeedArticleData
         */
        void cancelCollectArticle(int position, FeedArticleData feedArticleData);

    }

}
