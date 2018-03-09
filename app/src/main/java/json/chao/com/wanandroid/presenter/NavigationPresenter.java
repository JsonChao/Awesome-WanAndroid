package json.chao.com.wanandroid.presenter;

import javax.inject.Inject;

import json.chao.com.wanandroid.core.DataManager;
import json.chao.com.wanandroid.base.BasePresenter;
import json.chao.com.wanandroid.contract.NavigationContract;
import json.chao.com.wanandroid.core.bean.BaseResponse;
import json.chao.com.wanandroid.core.bean.NavigationListResponse;
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
        this.mDataManager = dataManager;
    }

    @Override
    public void attachView(NavigationContract.View view) {
        super.attachView(view);
    }

    @Override
    public void getNavigationListData() {
        addSubscribe(mDataManager.getNavigationListData()
                        .compose(RxUtils.rxSchedulerHelper())
                        .subscribeWith(new BaseObserver<NavigationListResponse>(mView) {
                            @Override
                            public void onNext(NavigationListResponse navigationListResponse) {
                                if (navigationListResponse.getErrorCode() == BaseResponse.SUCCESS) {
                                    mView.showNavigationListData(navigationListResponse);
                                } else {
                                    mView.showNavigationListFail();
                                }
                            }
                        }));
    }

}
