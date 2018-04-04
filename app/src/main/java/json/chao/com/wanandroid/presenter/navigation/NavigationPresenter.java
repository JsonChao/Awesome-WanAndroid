package json.chao.com.wanandroid.presenter.navigation;

import java.util.List;

import javax.inject.Inject;

import json.chao.com.wanandroid.core.DataManager;
import json.chao.com.wanandroid.base.presenter.BasePresenter;
import json.chao.com.wanandroid.contract.navigation.NavigationContract;
import json.chao.com.wanandroid.core.bean.BaseResponse;
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
    public void getNavigationListData() {
        addSubscribe(mDataManager.getNavigationListData()
                        .compose(RxUtils.rxSchedulerHelper())
                        .subscribeWith(new BaseObserver<BaseResponse<List<NavigationListData>>>(mView) {
                            @Override
                            public void onNext(BaseResponse<List<NavigationListData>> navigationListResponse) {
                                if (navigationListResponse.getErrorCode() == BaseResponse.SUCCESS) {
                                    mView.showNavigationListData(navigationListResponse);
                                } else {
                                    mView.showNavigationListFail();
                                }
                            }
                        }));
    }

}
