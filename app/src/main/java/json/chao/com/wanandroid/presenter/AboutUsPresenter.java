package json.chao.com.wanandroid.presenter;

import javax.inject.Inject;

import json.chao.com.wanandroid.base.RxPresenter;
import json.chao.com.wanandroid.contract.AboutUsContract;
import json.chao.com.wanandroid.core.DataManager;

/**
 * @author quchao
 * @date 2018/2/28
 */

public class AboutUsPresenter extends RxPresenter<AboutUsContract.View> implements AboutUsContract.Presenter {

    private DataManager mDataManager;

    @Inject
    AboutUsPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

}
