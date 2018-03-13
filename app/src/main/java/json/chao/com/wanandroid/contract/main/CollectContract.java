package json.chao.com.wanandroid.contract.main;

import json.chao.com.wanandroid.base.presenter.AbstractPresenter;
import json.chao.com.wanandroid.base.view.BaseView;
import json.chao.com.wanandroid.core.bean.main.collect.FeedArticleData;
import json.chao.com.wanandroid.core.bean.main.collect.FeedArticleListResponse;

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

    interface Presenter extends AbstractPresenter<View> {

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
