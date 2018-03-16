package json.chao.com.wanandroid.presenter.main;


import javax.inject.Inject;

import json.chao.com.wanandroid.component.RxBus;
import json.chao.com.wanandroid.core.DataManager;
import json.chao.com.wanandroid.base.presenter.BasePresenter;
import json.chao.com.wanandroid.contract.main.MainContract;
import json.chao.com.wanandroid.core.event.DismissErrorView;
import json.chao.com.wanandroid.core.event.LoginEvent;

/**
 * @author quchao
 * @date 2017/11/28
 */

public class MainPresenter extends BasePresenter<MainContract.View> implements MainContract.Presenter {

    private DataManager mDataManager;

    @Inject
    MainPresenter(DataManager dataManager) {
        this.mDataManager = dataManager;
    }

    @Override
    public void attachView(MainContract.View view) {
        super.attachView(view);
        registerEvent();
    }

    private void registerEvent() {
        addSubscribe(RxBus.getDefault().toFlowable(DismissErrorView.class)
                .subscribe(dismissErrorView -> mView.showDismissErrorView()));

        addSubscribe(  RxBus.getDefault().toFlowable(LoginEvent.class)
                .filter(LoginEvent::isLogin)
                .subscribe(loginEvent -> mView.showLoginView()));

        addSubscribe(RxBus.getDefault().toFlowable(LoginEvent.class)
                .filter(loginEvent -> !loginEvent.isLogin())
                .subscribe(logoutEvent -> mView.showLogoutView()));
    }


}
