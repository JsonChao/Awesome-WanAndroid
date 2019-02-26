package json.chao.com.wanandroid.presenter.hierarchy;

import javax.inject.Inject;

import json.chao.com.wanandroid.component.RxBus;
import json.chao.com.wanandroid.core.DataManager;
import json.chao.com.wanandroid.base.presenter.BasePresenter;
import json.chao.com.wanandroid.contract.hierarchy.KnowledgeHierarchyDetailContract;
import json.chao.com.wanandroid.core.event.SwitchNavigationEvent;
import json.chao.com.wanandroid.core.event.SwitchProjectEvent;

/**
 * @author quchao
 * @date 2018/2/23
 */

public class KnowledgeHierarchyDetailPresenter extends BasePresenter<KnowledgeHierarchyDetailContract.View>
        implements KnowledgeHierarchyDetailContract.Presenter {

    @Inject
    KnowledgeHierarchyDetailPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void attachView(KnowledgeHierarchyDetailContract.View view) {
        super.attachView(view);
        registerEvent();
    }

    private void registerEvent() {
        addSubscribe(RxBus.getDefault().toFlowable(SwitchProjectEvent.class)
                .subscribe(switchProjectEvent -> mView.showSwitchProject()));

        addSubscribe(RxBus.getDefault().toFlowable(SwitchNavigationEvent.class)
                .subscribe(switchNavigationEvent -> mView.showSwitchNavigation()));
    }


}
