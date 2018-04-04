package json.chao.com.wanandroid.presenter.main;

import java.util.List;

import javax.inject.Inject;

import json.chao.com.wanandroid.base.presenter.BasePresenter;
import json.chao.com.wanandroid.contract.main.UsageDialogContract;
import json.chao.com.wanandroid.core.DataManager;
import json.chao.com.wanandroid.core.bean.BaseResponse;
import json.chao.com.wanandroid.core.bean.main.search.UsefulSiteData;
import json.chao.com.wanandroid.utils.RxUtils;
import json.chao.com.wanandroid.widget.BaseObserver;

/**
 * @author quchao
 * @date 2018/4/2
 */

public class UsageDialogPresenter extends BasePresenter<UsageDialogContract.View> implements UsageDialogContract.Presenter {

    private DataManager mDataManager;

    @Inject
    UsageDialogPresenter(DataManager dataManager) {
        super(dataManager);
        mDataManager = dataManager;
    }

    @Override
    public void getUsefulSites() {
        addSubscribe(mDataManager.getUsefulSites()
                .compose(RxUtils.rxSchedulerHelper())
                .subscribeWith(new BaseObserver<BaseResponse<List<UsefulSiteData>>>(mView) {
                    @Override
                    public void onNext(BaseResponse<List<UsefulSiteData>> usefulSitesResponse) {
                        if (usefulSitesResponse.getErrorCode() == BaseResponse.SUCCESS) {
                            mView.showUsefulSites(usefulSitesResponse);
                        } else {
                            mView.showUsefulSitesDataFail();
                        }
                    }
                }));
    }

}
