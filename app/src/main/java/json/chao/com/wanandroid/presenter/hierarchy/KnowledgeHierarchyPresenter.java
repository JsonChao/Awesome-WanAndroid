package json.chao.com.wanandroid.presenter.hierarchy;

import javax.inject.Inject;

import json.chao.com.wanandroid.core.DataManager;
import json.chao.com.wanandroid.base.presenter.BasePresenter;
import json.chao.com.wanandroid.contract.hierarchy.KnowledgeHierarchyContract;
import json.chao.com.wanandroid.core.bean.BaseResponse;
import json.chao.com.wanandroid.core.bean.hierarchy.KnowledgeHierarchyResponse;
import json.chao.com.wanandroid.utils.RxUtils;
import json.chao.com.wanandroid.widget.BaseObserver;

/**
 * @author quchao
 * @date 2017/12/7
 */

public class KnowledgeHierarchyPresenter extends BasePresenter<KnowledgeHierarchyContract.View> implements KnowledgeHierarchyContract.Presenter {

    private DataManager mDataManager;

    @Inject
    KnowledgeHierarchyPresenter(DataManager dataManager) {
        this.mDataManager = dataManager;
    }

    @Override
    public void getKnowledgeHierarchyData() {
        addSubscribe(mDataManager.getKnowledgeHierarchyData()
                        .compose(RxUtils.rxSchedulerHelper())
                        .subscribeWith(new BaseObserver<KnowledgeHierarchyResponse>(mView) {
                            @Override
                            public void onNext(KnowledgeHierarchyResponse knowledgeHierarchyResponse) {
                                if (knowledgeHierarchyResponse.getErrorCode() == BaseResponse.SUCCESS) {
                                    mView.showKnowledgeHierarchyData(knowledgeHierarchyResponse);
                                } else {
                                    mView.showKnowledgeHierarchyDetailDataFail();
                                }
                            }
                        }));
    }
}
