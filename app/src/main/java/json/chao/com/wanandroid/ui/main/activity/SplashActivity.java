package json.chao.com.wanandroid.ui.main.activity;

import android.content.Intent;

import com.airbnb.lottie.LottieAnimationView;

import butterknife.BindView;
import json.chao.com.wanandroid.R;
import json.chao.com.wanandroid.app.GeeksApp;
import json.chao.com.wanandroid.base.activity.BaseActivity;
import json.chao.com.wanandroid.contract.main.SplashContract;
import json.chao.com.wanandroid.presenter.main.SplashPresenter;
import json.chao.com.wanandroid.utils.StatusBarUtil;

/**
 * @author quchao
 * @date 2017/11/29
 */

public class SplashActivity extends BaseActivity<SplashPresenter> implements SplashContract.View {

    @BindView(R.id.one_animation)
    LottieAnimationView mOneAnimation;
    @BindView(R.id.two_animation)
    LottieAnimationView mTwoAnimation;
    @BindView(R.id.three_animation)
    LottieAnimationView mThreeAnimation;
    @BindView(R.id.four_animation)
    LottieAnimationView mFourAnimation;
    @BindView(R.id.five_animation)
    LottieAnimationView mFiveAnimation;
    @BindView(R.id.six_animation)
    LottieAnimationView mSixAnimation;
    @BindView(R.id.seven_animation)
    LottieAnimationView mSevenAnimation;
    @BindView(R.id.eight_animation)
    LottieAnimationView mEightAnimation;
    @BindView(R.id.nine_animation)
    LottieAnimationView mNineAnimation;
    @BindView(R.id.ten_animation)
    LottieAnimationView mTenAnimation;

    @Override
    protected void onDestroy() {
        cancelAnimation();
        super.onDestroy();
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initEventAndData() {
        if (GeeksApp.isFirstRun) {
            jumpToMain();
            return;
        }
        StatusBarUtil.immersive(this);
        GeeksApp.isFirstRun = true;
        mOneAnimation.setAnimation("W.json");
        mOneAnimation.playAnimation();
        mTwoAnimation.setAnimation("A.json");
        mTwoAnimation.playAnimation();
        mThreeAnimation.setAnimation("N.json");
        mThreeAnimation.playAnimation();
        mFourAnimation.setAnimation("A.json");
        mFourAnimation.playAnimation();
        mFiveAnimation.setAnimation("N.json");
        mFiveAnimation.playAnimation();
        mSixAnimation.setAnimation("D.json");
        mSixAnimation.playAnimation();
        mSevenAnimation.setAnimation("R.json");
        mSevenAnimation.playAnimation();
        mEightAnimation.setAnimation("I.json");
        mEightAnimation.playAnimation();
        mNineAnimation.setAnimation("O.json");
        mNineAnimation.playAnimation();
        mTenAnimation.setAnimation("D.json");
        mTenAnimation.playAnimation();
    }

    @Override
    public void jumpToMain() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    private void cancelAnimation() {
        if (mOneAnimation != null) {
            mOneAnimation.cancelAnimation();
        }
        if (mTwoAnimation != null) {
            mTwoAnimation.cancelAnimation();
        }
        if (mThreeAnimation != null) {
            mThreeAnimation.cancelAnimation();
        }
        if (mFourAnimation != null) {
            mFourAnimation.cancelAnimation();
        }
        if (mFiveAnimation != null) {
            mFiveAnimation.cancelAnimation();
        }
        if (mSixAnimation != null) {
            mSixAnimation.cancelAnimation();
        }
        if (mSevenAnimation != null) {
            mSevenAnimation.cancelAnimation();
        }
        if (mEightAnimation != null) {
            mEightAnimation.cancelAnimation();
        }
        if (mNineAnimation != null) {
            mNineAnimation.cancelAnimation();
        }
        if (mTenAnimation != null) {
            mTenAnimation.cancelAnimation();
        }
    }

}
