package json.chao.com.wanandroid.presenter.main;

import javax.inject.Inject;

import json.chao.com.wanandroid.base.presenter.BasePresenter;
import json.chao.com.wanandroid.contract.main.SettingContract;
import json.chao.com.wanandroid.core.DataManager;

/**
 * @author quchao
 * @date 2018/4/2
 */

public class SettingPresenter extends BasePresenter<SettingContract.View> implements SettingContract.Presenter {

    private DataManager mDataManager;

    @Inject
    SettingPresenter(DataManager dataManager) {
        super(dataManager);
        mDataManager = dataManager;
    }

    @Override
    public boolean getAutoCacheState() {
        return mDataManager.getAutoCacheState();
    }

    @Override
    public boolean getNoImageState() {
        return mDataManager.getNoImageState();
    }

    @Override
    public boolean getNightModeState() {
        return mDataManager.getNightModeState();
    }

    @Override
    public void setNightModeState(boolean b) {
        mDataManager.setNightModeState(b);
    }

    @Override
    public void setNoImageState(boolean b) {
        mDataManager.setNoImageState(b);
    }

    @Override
    public void setAutoCacheState(boolean b) {
        mDataManager.setAutoCacheState(b);
    }


}
