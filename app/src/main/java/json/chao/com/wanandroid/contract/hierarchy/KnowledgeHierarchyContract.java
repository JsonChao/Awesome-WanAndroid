package json.chao.com.wanandroid.contract.hierarchy;

import java.util.List;

import json.chao.com.wanandroid.base.presenter.AbstractPresenter;
import json.chao.com.wanandroid.base.view.AbstractView;
import json.chao.com.wanandroid.core.bean.hierarchy.KnowledgeHierarchyData;

/**
 * @author quchao
 * @date 2017/12/7
 */

public interface KnowledgeHierarchyContract {

    interface View extends AbstractView {

        /**
         * Show Knowledge Hierarchy Data
         *
         * @param knowledgeHierarchyDataList (List<KnowledgeHierarchyData>
         */
        void showKnowledgeHierarchyData(List<KnowledgeHierarchyData> knowledgeHierarchyDataList);

    }

    interface Presenter extends AbstractPresenter<View> {

        /**
         * 知识列表
         *
         * @param isShowError If show error
         */
        void getKnowledgeHierarchyData(boolean isShowError);
    }
}
