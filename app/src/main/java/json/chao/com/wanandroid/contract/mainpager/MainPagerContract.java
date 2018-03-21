package json.chao.com.wanandroid.contract.mainpager;

import java.util.List;

import json.chao.com.wanandroid.core.bean.BaseResponse;
import json.chao.com.wanandroid.core.bean.main.banner.BannerData;
import json.chao.com.wanandroid.core.bean.main.collect.FeedArticleData;
import json.chao.com.wanandroid.base.presenter.AbstractPresenter;
import json.chao.com.wanandroid.base.view.BaseView;
import json.chao.com.wanandroid.core.bean.main.collect.FeedArticleListData;

/**
 * @author quchao
 * @date 2017/12/7
 */

public interface MainPagerContract {

    interface View extends BaseView {

        /**
         * Show auto login success
         */
        void showAutoLoginSuccess();

        /**
         * Show auto login fail
         */
        void showAutoLoginFail();

        /**
         * Show content
         *
         * @param feedArticleListResponse BaseResponse<FeedArticleListData>
         * @param isRefresh If refresh
         */
        void showArticleList(BaseResponse<FeedArticleListData> feedArticleListResponse, boolean isRefresh);

        /**
         * Show collect article data
         *
         * @param position Position
         * @param feedArticleData FeedArticleData
         * @param feedArticleListResponse BaseResponse<FeedArticleListData>
         */
        void showCollectArticleData(int position, FeedArticleData feedArticleData, BaseResponse<FeedArticleListData> feedArticleListResponse);

        /**
         * Show cancel collect article data
         *
         * @param position Position
         * @param feedArticleData FeedArticleData
         * @param feedArticleListResponse BaseResponse<FeedArticleListData>
         */
        void showCancelCollectArticleData(int position, FeedArticleData feedArticleData, BaseResponse<FeedArticleListData> feedArticleListResponse);

        /**
         * Show banner data
         *
         * @param bannerResponse BaseResponse<List<BannerData>>
         */
        void showBannerData(BaseResponse<List<BannerData>> bannerResponse);

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
         * Load main pager data
         */
        void loadMainPagerData();

        /**
         * Get feed article list
         */
        void getFeedArticleList();

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

        /**
         * Auto refresh
         */
        void autoRefresh();

        /**
         * Load more
         */
        void loadMore();

    }

}
