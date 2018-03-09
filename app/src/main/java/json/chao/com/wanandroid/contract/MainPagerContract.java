package json.chao.com.wanandroid.contract;

import json.chao.com.wanandroid.core.bean.BannerResponse;
import json.chao.com.wanandroid.core.bean.FeedArticleData;
import json.chao.com.wanandroid.core.bean.FeedArticleListResponse;
import json.chao.com.wanandroid.base.AbstractPresenter;
import json.chao.com.wanandroid.base.BaseView;

/**
 * @author quchao
 * @date 2017/12/7
 */

public interface MainPagerContract {

    interface View extends BaseView {

        /**
         * Show content
         *
         * @param feedArticleListResponse FeedArticleListResponse
         */
        void showArticleList(FeedArticleListResponse feedArticleListResponse);

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
         * Show banner data
         *
         * @param bannerResponse BannerResponse
         */
        void showBannerData(BannerResponse bannerResponse);

        /**
         * Show article list fail
         */
        void showArticleListFail();

        /**
         * Show banner data fail
         */
        void showBannerDataFail();

    }

    interface Presenter extends AbstractPresenter<View> {

        /**
         * Get feed article list
         *
         *  @param page Page
         */
        void getFeedArticleList(int page);

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

        /**
         * Get banner data
         */
        void getBannerData();

    }

}
