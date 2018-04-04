package json.chao.com.wanandroid.base.activity;

import android.view.View;
import android.view.ViewGroup;

import com.airbnb.lottie.LottieAnimationView;

import json.chao.com.wanandroid.R;
import json.chao.com.wanandroid.base.presenter.BasePresenter;


/**
 * @author quchao
 * @date 2018/3/30
 */

public abstract class AbstractRootActivity<T extends BasePresenter> extends BaseActivity<T> {

    private static final int NORMAL_STATE = 0;
    private static final int LOADING_STATE = 1;

    private LottieAnimationView mLoadingAnimation;
    private View mLoadingView;
    private ViewGroup mNormalView;

    private int currentState = NORMAL_STATE;

    @Override
    protected void initEventAndData() {
        mNormalView = (ViewGroup) findViewById(R.id.normal_view);
        if (mNormalView == null) {
            throw new IllegalStateException(
                    "The subclass of RootActivity must contain a View named 'mNormalView'.");
        }
        if (!(mNormalView.getParent() instanceof ViewGroup)) {
            throw new IllegalStateException(
                    "mNormalView's ParentView should be a ViewGroup.");
        }
        ViewGroup parent = (ViewGroup) mNormalView.getParent();
        View.inflate(this, R.layout.loading_view, parent);
        mLoadingView = parent.findViewById(R.id.loading_group);
        mLoadingAnimation = (LottieAnimationView) mLoadingView.findViewById(R.id.loading_animation);
        mLoadingView.setVisibility(View.GONE);
        mNormalView.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        if (mLoadingAnimation != null) {
            mLoadingAnimation.cancelAnimation();
        }
        super.onDestroy();
    }

    @Override
    public void showLoading() {
        if (currentState == LOADING_STATE) {
            return;
        }
        hideCurrentView();
        currentState = LOADING_STATE;
        mLoadingView.setVisibility(View.VISIBLE);
        mLoadingAnimation.setAnimation("loading_bus.json");
        mLoadingAnimation.playAnimation();
    }

    @Override
    public void showNormal() {
        if (currentState == NORMAL_STATE) {
            return;
        }
        hideCurrentView();
        currentState = NORMAL_STATE;
        mNormalView.setVisibility(View.VISIBLE);
    }

    private void hideCurrentView() {
        switch (currentState) {
            case NORMAL_STATE:
                mNormalView.setVisibility(View.GONE);
                break;
            case LOADING_STATE:
                mLoadingAnimation.cancelAnimation();
                mLoadingView.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }
}