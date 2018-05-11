package json.chao.com.wanandroid.presenter.navigation;

import java.util.List;

import javax.inject.Inject;

import json.chao.com.wanandroid.R;
import json.chao.com.wanandroid.app.WanAndroidApp;
import json.chao.com.wanandroid.core.DataManager;
import json.chao.com.wanandroid.base.presenter.BasePresenter;
import json.chao.com.wanandroid.contract.navigation.NavigationContract;
import json.chao.com.wanandroid.core.bean.navigation.NavigationListData;
import json.chao.com.wanandroid.utils.RxUtils;
import json.chao.com.wanandroid.widget.BaseObserver;

/**
 * @author quchao
 * @date 2018/2/11
 */

public class NavigationPresenter extends BasePresenter<NavigationContract.View> implements NavigationContract.Presenter {

    private DataManager mDataManager;

    @Inject
    NavigationPresenter(DataManager dataManager) {
        super(dataManager);
        this.mDataManager = dataManager;
    }

    @Override
    public void attachView(NavigationContract.View view) {
        super.attachView(view);
    }

    @Override
    public void getNavigationListData(boolean isShowError) {
        addSubscribe(mDataManager.getNavigationListData()
                .compose(RxUtils.rxSchedulerHelper())
                .compose(RxUtils.handleResult())
                .subscribeWith(new BaseObserver<List<NavigationListData>>(mView,
                        WanAndroidApp.getInstance().getString(R.string.failed_to_obtain_navigation_list),
                        isShowError) {
                    @Override
                    public void onNext(List<NavigationListData> navigationDataList) {
                        mView.showNavigationListData(navigationDataList);
                    }
                }));
    }

}
