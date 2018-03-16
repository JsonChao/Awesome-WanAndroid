package json.chao.com.wanandroid.contract.hierarchy;

import json.chao.com.wanandroid.base.presenter.AbstractPresenter;
import json.chao.com.wanandroid.core.bean.main.collect.FeedArticleData;
import json.chao.com.wanandroid.core.bean.main.collect.FeedArticleListResponse;
import json.chao.com.wanandroid.base.view.BaseView;

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

        /**
         * Show jump the top
         */
        void showJumpTheTop();

    }

    interface Presenter extends AbstractPresenter<View> {

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
