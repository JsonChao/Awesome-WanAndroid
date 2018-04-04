package json.chao.com.wanandroid.base.presenter;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import json.chao.com.wanandroid.base.view.BaseView;
import json.chao.com.wanandroid.core.DataManager;

/**
 * Base Presenter
 * 管理事件流订阅的生命周期
 *
 * @author quchao
 * @date 2017/11/28
 */

public class BasePresenter<T extends BaseView> implements AbstractPresenter<T> {

    protected T mView;
    private CompositeDisposable compositeDisposable;
    private DataManager mDataManager;

    public BasePresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    protected void addSubscribe(Disposable disposable) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }

    @Override
    public void attachView(T view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        this.mView = null;
        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }
    }

    @Override
    public boolean getNightModeState() {
        return mDataManager.getNightModeState();
    }

}
