package json.chao.com.wanandroid.contract;

import json.chao.com.wanandroid.base.AbstractPresenter;
import json.chao.com.wanandroid.base.BaseView;
import json.chao.com.wanandroid.core.bean.FeedArticleListResponse;

/**
 * @author quchao
 * @date 2018/2/13
 */

public interface ArticleDetailContract {

    interface View extends BaseView {

        /**
         * Show collect article data
         *
         * @param feedArticleListResponse FeedArticleListResponse
         */
        void showCollectArticleData(FeedArticleListResponse feedArticleListResponse);

        /**
         * Show cancel collect article data
         *
         * @param feedArticleListResponse FeedArticleListResponse
         */
        void showCancelCollectArticleData(FeedArticleListResponse feedArticleListResponse);


    }

    interface Presenter extends AbstractPresenter<View> {

        /**
         * Add collect article
         *
         * @param id article id
         */
        void addCollectArticle(int id);

        /**
         * Cancel collect article
         *
         * @param id article id
         */
        void cancelCollectArticle(int id);

        /**
         * Cancel collect article
         *
         * @param id article id
         */
        void cancelCollectPageArticle(int id);

    }
}
