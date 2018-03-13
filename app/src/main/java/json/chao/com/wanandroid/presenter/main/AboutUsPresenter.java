package json.chao.com.wanandroid.presenter.main;

import javax.inject.Inject;

import json.chao.com.wanandroid.base.presenter.BasePresenter;
import json.chao.com.wanandroid.contract.main.AboutUsContract;
import json.chao.com.wanandroid.core.DataManager;

/**
 * @author quchao
 * @date 2018/2/28
 */

public class AboutUsPresenter extends BasePresenter<AboutUsContract.View> implements AboutUsContract.Presenter {

    private DataManager mDataManager;

    @Inject
    AboutUsPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

}
