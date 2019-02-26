package json.chao.com.wanandroid.presenter.main;

import java.util.List;

import javax.inject.Inject;

import json.chao.com.wanandroid.R;
import json.chao.com.wanandroid.app.WanAndroidApp;
import json.chao.com.wanandroid.base.presenter.BasePresenter;
import json.chao.com.wanandroid.contract.main.UsageDialogContract;
import json.chao.com.wanandroid.core.DataManager;
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
                .compose(RxUtils.handleResult())
                .subscribeWith(new BaseObserver<List<UsefulSiteData>>(mView,
                        WanAndroidApp.getInstance().getString(R.string.failed_to_obtain_useful_sites_data)) {
                    @Override
                    public void onNext(List<UsefulSiteData> usefulSiteDataList) {
                        mView.showUsefulSites(usefulSiteDataList);
                    }
                }));
    }

}
