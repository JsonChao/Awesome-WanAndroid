package json.chao.com.wanandroid.contract.hierarchy;

import json.chao.com.wanandroid.base.presenter.AbstractPresenter;
import json.chao.com.wanandroid.core.bean.hierarchy.KnowledgeHierarchyResponse;
import json.chao.com.wanandroid.base.view.BaseView;

/**
 * @author quchao
 * @date 2017/12/7
 */

public interface KnowledgeHierarchyContract {

    interface View extends BaseView {

        /**
         * Show Knowledge Hierarchy Data
         *
         * @param knowledgeHierarchyResponse KnowledgeHierarchyResponse
         */
        void showKnowledgeHierarchyData(KnowledgeHierarchyResponse knowledgeHierarchyResponse);

        /**
         * Show knowledge hierarchy detail data fail
         */
        void showKnowledgeHierarchyDetailDataFail();

    }

    interface Presenter extends AbstractPresenter<View> {

        /**
         * 知识列表
         */
        void getKnowledgeHierarchyData();
    }
}
