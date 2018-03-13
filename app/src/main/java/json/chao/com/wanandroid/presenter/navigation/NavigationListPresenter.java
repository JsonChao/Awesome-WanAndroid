package json.chao.com.wanandroid.presenter.navigation;

import javax.inject.Inject;

import json.chao.com.wanandroid.core.DataManager;
import json.chao.com.wanandroid.base.presenter.BasePresenter;
import json.chao.com.wanandroid.contract.navigation.NavigationListContract;

/**
 * @author quchao
 * @date 2018/2/24
 */

public class NavigationListPresenter extends BasePresenter<NavigationListContract.View> implements NavigationListContract.Presenter {

    private DataManager mDataManager;

    @Inject
    NavigationListPresenter(DataManager dataManager) {
        this.mDataManager = dataManager;
    }


}
