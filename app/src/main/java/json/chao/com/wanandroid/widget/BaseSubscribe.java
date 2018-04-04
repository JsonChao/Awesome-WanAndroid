package json.chao.com.wanandroid.widget;

import android.text.TextUtils;

import io.reactivex.subscribers.ResourceSubscriber;
import json.chao.com.wanandroid.R;
import json.chao.com.wanandroid.app.GeeksApp;
import json.chao.com.wanandroid.base.view.BaseView;
import json.chao.com.wanandroid.core.http.exception.ServerException;
import json.chao.com.wanandroid.utils.LogHelper;
import retrofit2.HttpException;

/**
 * @author quchao
 * @date 2018/4/2
 */

public abstract class BaseSubscribe <T> extends ResourceSubscriber<T> {

    private BaseView mView;
    private String mErrorMsg;
    private boolean isShowError = true;

    protected BaseSubscribe(BaseView view){
        this.mView = view;
    }

    protected BaseSubscribe(BaseView view, String errorMsg){
        this.mView = view;
        this.mErrorMsg = errorMsg;
    }

    protected BaseSubscribe(BaseView view, boolean isShowError){
        this.mView = view;
        this.isShowError = isShowError;
    }

    protected BaseSubscribe(BaseView view, String errorMsg, boolean isShowError){
        this.mView = view;
        this.mErrorMsg = errorMsg;
        this.isShowError = isShowError;
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onError(Throwable e) {
        if (mView == null) {
            return;
        }
        if (mErrorMsg != null && !TextUtils.isEmpty(mErrorMsg)) {
            mView.showErrorMsg(mErrorMsg);
        } else if (e instanceof ServerException) {
            mView.showErrorMsg(e.toString());
        } else if (e instanceof HttpException) {
            mView.showErrorMsg(GeeksApp.getInstance().getString(R.string.http_error));
        } else {
            mView.showErrorMsg(GeeksApp.getInstance().getString(R.string.unKnown_error));
            LogHelper.d(e.toString());
        }
        if (isShowError) {
            mView.showError();
        }
    }
}
