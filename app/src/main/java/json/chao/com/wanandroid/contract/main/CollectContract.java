package json.chao.com.wanandroid.contract.main;

import json.chao.com.wanandroid.base.presenter.AbstractPresenter;
import json.chao.com.wanandroid.base.view.AbstractView;
import json.chao.com.wanandroid.core.bean.main.collect.FeedArticleData;
import json.chao.com.wanandroid.core.bean.main.collect.FeedArticleListData;

/**
 * @author quchao
 * @date 2018/2/27
 */

public interface CollectContract {

    interface View extends AbstractView {

        /**
         * Show collect list
         *
         * @param feedArticleListData FeedArticleListData
         */
        void showCollectList(FeedArticleListData feedArticleListData);

        /**
         * Show cancel collect article data
         *
         * @param position Position
         * @param feedArticleData FeedArticleData
         * @param feedArticleListData FeedArticleListData
         */
        void showCancelCollectPageArticleData(int position, FeedArticleData feedArticleData, FeedArticleListData feedArticleListData);

        /**
         * Show Refresh event
         */
        void showRefreshEvent();

    }

    interface Presenter extends AbstractPresenter<View> {

        /**
         * Get collect list
         *
         * @param page page number
         * @param isShowError If show error
         */
        void getCollectList(int page, boolean isShowError);

        /**
         * Cancel collect article
         *
         * @param position Position
         * @param feedArticleData FeedArticleData
         */
        void cancelCollectPageArticle(int position, FeedArticleData feedArticleData);


    }
}
