package json.chao.com.wanandroid.presenter.main;


import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import json.chao.com.wanandroid.core.DataManager;
import json.chao.com.wanandroid.base.presenter.BasePresenter;
import json.chao.com.wanandroid.contract.main.SplashContract;


/**
 * @author quchao
 * @date 2017/11/29
 */

public class SplashPresenter extends BasePresenter<SplashContract.View> implements SplashContract.Presenter {


    @Inject
    SplashPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void attachView(SplashContract.View view) {
        super.attachView(view);
        long splashTime = 2000;
        addSubscribe(Observable.timer(splashTime, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> view.jumpToMain()));
    }

}
