package json.chao.com.wanandroid.widget;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.DecelerateInterpolator;

/**
 * @author quchao
 * @date 2018/3/1
 */

public class CircularRevealAnim {

    private static final long DURATION = 200;

    private AnimListener mListener = null;
    private Animator anim;

    public interface AnimListener {

        void onHideAnimationEnd();

        void onShowAnimationEnd();
    }

    @SuppressLint("NewApi")
    private void actionOtherVisible(Boolean isShow, View triggerView, View animView) {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {
            if (isShow) {
                animView.setVisibility(View.VISIBLE);
                if (mListener != null) {
                    anim.cancel();
                    anim = null;
                    mListener.onShowAnimationEnd();
                }
            } else {
                animView.setVisibility(View.GONE);
                if (mListener != null) {
                    anim.cancel();
                    anim = null;
                    mListener.onHideAnimationEnd();
                }
            }
            return;
        }

        /**
         * 计算 triggerView 的中心位置
         */
        int[] tvLocation = {0, 0};
        triggerView.getLocationInWindow(tvLocation);
        int tvX = (int) (tvLocation[0] + animView.getWidth() * 0.8);
        int tvY = tvLocation[1] + triggerView.getHeight() / 2;

        /**
         * 计算 animView 的中心位置
         */
        int[] avLocation = {0, 0};
        animView.getLocationInWindow(avLocation);
        int avX = avLocation[0] + animView.getWidth() / 2;
        int avY = avLocation[1] + animView.getHeight() / 2;

        int rippleW;
        if (tvX < avX) {
             rippleW = animView.getWidth() - tvX;
        } else {
             rippleW = tvX - avLocation[0];
        }

        int rippleH;
        if (tvY < avY) {
            rippleH = animView.getHeight() - tvY;
        } else {
            rippleH = tvY - avLocation[1];
        }

        float maxRadius = (float) Math.sqrt((double) (rippleW * rippleW + rippleH * rippleH));
        float startRadius;
        float endRadius;

        if (isShow) {
            startRadius = 0f;
            endRadius = maxRadius;
        } else {
            startRadius = maxRadius;
            endRadius = 0f;
        }

        anim = ViewAnimationUtils.createCircularReveal(animView, tvX, tvY, startRadius, endRadius);
        animView.setVisibility(View.VISIBLE);
        anim.setDuration(DURATION);
        anim.setInterpolator(new DecelerateInterpolator());

        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (isShow) {
                    animView.setVisibility(View.VISIBLE);
                    if (mListener != null) {
                        mListener.onShowAnimationEnd();
                    }
                } else {
                    animView.setVisibility(View.GONE);
                    if (mListener != null) {
                        mListener.onHideAnimationEnd();
                    }
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        anim.start();
    }

    public void show(View triggerView, View showView) {
        actionOtherVisible(true, triggerView, showView);
    }

    public void hide(View triggerView, View hideView) {
        actionOtherVisible(false, triggerView, hideView);
    }

    public void setAnimListener(AnimListener listener) {
        mListener = listener;
    }
}
