package json.chao.com.wanandroid.contract;

import json.chao.com.wanandroid.core.bean.FeedArticleData;
import json.chao.com.wanandroid.core.bean.FeedArticleListResponse;
import json.chao.com.wanandroid.base.BasePresenter;
import json.chao.com.wanandroid.base.BaseView;

/**
 * @author quchao
 * @date 2018/2/23
 */

public interface KnowledgeHierarchyListContract {

    interface View extends BaseView {

        /**
         * Show Knowledge Hierarchy Detail Data
         *
         * @param feedArticleListResponse FeedArticleListResponse
         */
        void showKnowledgeHierarchyDetailData(FeedArticleListResponse feedArticleListResponse);

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
         * Show knowledge hierarchy detail data fail
         */
        void showKnowledgeHierarchyDetailDataFail();

    }

    interface Presenter extends BasePresenter<KnowledgeHierarchyListContract.View> {

        /**
         * 知识列表
         *
         * @param page page num
         * @param cid second page id
         */
        void getKnowledgeHierarchyDetailData(int page, int cid);

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
