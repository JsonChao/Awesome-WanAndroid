package json.chao.com.wanandroid.presenter;



import javax.inject.Inject;

import json.chao.com.wanandroid.core.DataManager;
import json.chao.com.wanandroid.base.RxPresenter;
import json.chao.com.wanandroid.contract.MainContract;

/**
 * @author quchao
 * @date 2017/11/28
 */

public class MainPresenter extends RxPresenter<MainContract.View> implements MainContract.Presenter {

    private DataManager mDataManager;

    @Inject
    MainPresenter(DataManager dataManager) {
        this.mDataManager = dataManager;
    }


}
