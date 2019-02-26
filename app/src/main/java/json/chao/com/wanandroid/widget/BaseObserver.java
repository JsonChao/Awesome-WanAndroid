package json.chao.com.wanandroid.widget;

import android.text.TextUtils;

import io.reactivex.observers.ResourceObserver;
import json.chao.com.wanandroid.app.WanAndroidApp;
import json.chao.com.wanandroid.base.view.AbstractView;
import json.chao.com.wanandroid.core.http.exception.ServerException;
import json.chao.com.wanandroid.R;
import json.chao.com.wanandroid.utils.LogHelper;
import retrofit2.HttpException;

/**
 * @author quchao
 * @date 2017/11/27
 *
 * @param <T>
 */

public abstract class BaseObserver<T> extends ResourceObserver<T> {

    private AbstractView mView;
    private String mErrorMsg;
    private boolean isShowError = true;

    protected BaseObserver(AbstractView view){
        this.mView = view;
    }

    protected BaseObserver(AbstractView view, String errorMsg){
        this.mView = view;
        this.mErrorMsg = errorMsg;
    }

    protected BaseObserver(AbstractView view, boolean isShowError){
        this.mView = view;
        this.isShowError = isShowError;
    }

    protected BaseObserver(AbstractView view, String errorMsg, boolean isShowError){
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
                mView.showErrorMsg(WanAndroidApp.getInstance().getString(R.string.http_error));
        } else {
            mView.showErrorMsg(WanAndroidApp.getInstance().getString(R.string.unKnown_error));
            LogHelper.d(e.toString());
        }
        if (isShowError) {
            mView.showError();
        }
    }
}
