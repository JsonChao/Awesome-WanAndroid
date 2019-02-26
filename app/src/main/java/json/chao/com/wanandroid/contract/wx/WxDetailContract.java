package json.chao.com.wanandroid.contract.wx;

import json.chao.com.wanandroid.base.presenter.AbstractPresenter;
import json.chao.com.wanandroid.base.view.AbstractView;
import json.chao.com.wanandroid.core.bean.main.collect.FeedArticleData;
import json.chao.com.wanandroid.core.bean.main.collect.FeedArticleListData;


/**
 * @author quchao
 * @date 2018/10/31
 */
public interface WxDetailContract {

    interface View extends AbstractView {

        /**
         * Show wx search view
         *
         * @param wxSearchData wx search data
         */
        void showWxSearchView(FeedArticleListData wxSearchData);

        /**
         * Show wx detail view
         *
         * @param wxSumData wx detail data
         */
        void showWxDetailView(FeedArticleListData wxSumData);

        /**
         * Show collect article data
         *
         * @param position Position
         * @param feedArticleData FeedArticleData
         * @param feedArticleListData FeedArticleListData
         */
        void showCollectArticleData(int position, FeedArticleData feedArticleData, FeedArticleListData feedArticleListData);

        /**
         * Show cancel collect article data
         *
         * @param position Position
         * @param feedArticleData FeedArticleData
         * @param feedArticleListData FeedArticleListData
         */
        void showCancelCollectArticleData(int position, FeedArticleData feedArticleData, FeedArticleListData feedArticleListData);

        /**
         * Show jump the top
         */
        void showJumpTheTop();

        /**
         * Show reload detail event
         */
        void showReloadDetailEvent();

    }

    interface Presenter extends AbstractPresenter<WxDetailContract.View> {

        /**
         * Get wx search data
         *
         * @param id wx id
         * @param page page number
         * @param k search content
         */
        void getWxSearchSumData(int id, int page, String k);

        /**
         * Get wx detail data
         *
         * @param id wx id
         * @param page page number
         * @param isShowError if show error
         */
        void getWxDetailData(int id, int page, boolean isShowError);

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
