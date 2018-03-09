package json.chao.com.wanandroid.contract;

import json.chao.com.wanandroid.base.BasePresenter;
import json.chao.com.wanandroid.base.BaseView;
import json.chao.com.wanandroid.core.bean.FeedArticleData;
import json.chao.com.wanandroid.core.bean.FeedArticleListResponse;

/**
 * @author quchao
 * @date 2018/2/27
 */

public interface CollectContract {

    interface View extends BaseView {

        /**
         * Show collect list
         *
         * @param feedArticleListResponse FeedArticleListResponse
         */
        void showCollectList(FeedArticleListResponse feedArticleListResponse);

        /**
         * Show cancel collect article data
         *
         * @param position Position
         * @param feedArticleData FeedArticleData
         * @param feedArticleListResponse FeedArticleListResponse
         */
        void showCancelCollectPageArticleData(int position, FeedArticleData feedArticleData, FeedArticleListResponse feedArticleListResponse);

        /**
         * Show collect list fail
         */
        void showCollectListFail();
    }

    interface Presenter extends BasePresenter<CollectContract.View> {

        /**
         * Get collect list
         *
         * @param page page number
         */
        void getCollectList(int page);

        /**
         * Cancel collect article
         *
         * @param position Position
         * @param feedArticleData FeedArticleData
         */
        void cancelCollectPageArticle(int position, FeedArticleData feedArticleData);


    }
}
