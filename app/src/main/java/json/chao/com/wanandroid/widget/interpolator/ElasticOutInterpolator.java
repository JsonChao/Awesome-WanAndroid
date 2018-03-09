package json.chao.com.wanandroid.widget.interpolator;

import android.view.animation.Interpolator;

/**
 * @author quchao
 * @date 2018/3/7
 */

public class ElasticOutInterpolator implements Interpolator {

    @Override
    public float getInterpolation(float t) {
        if (t == 0) {
            return 0;
        }
        if (t >= 1) {
            return 1;
        }
        float p = .3f;
        float s = p/4;
        return ((float) Math.pow(2, -10 * t) * (float)Math.sin((t - s) * (2 * (float)Math.PI) / p) + 1);
    }
}