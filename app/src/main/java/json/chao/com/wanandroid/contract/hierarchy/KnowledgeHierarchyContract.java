package json.chao.com.wanandroid.contract.hierarchy;

import java.util.List;

import json.chao.com.wanandroid.base.presenter.AbstractPresenter;
import json.chao.com.wanandroid.core.bean.BaseResponse;
import json.chao.com.wanandroid.core.bean.hierarchy.KnowledgeHierarchyData;
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
         * @param knowledgeHierarchyDataList (List<KnowledgeHierarchyData>
         */
        void showKnowledgeHierarchyData(List<KnowledgeHierarchyData> knowledgeHierarchyDataList);

    }

    interface Presenter extends AbstractPresenter<View> {

        /**
         * 知识列表
         */
        void getKnowledgeHierarchyData();
    }
}
