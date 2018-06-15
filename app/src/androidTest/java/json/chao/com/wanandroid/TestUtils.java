package json.chao.com.wanandroid;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;


/**
 * @author quchao
 * @date 2018/6/15
 */
public class TestUtils {
    
    public static String getToolBarNavigationContentDescription(@NonNull AppCompatActivity mActivity,
                                      @NonNull @IdRes int mToolbarId) {
        Toolbar toolbar = (Toolbar) mActivity.findViewById(mToolbarId);
        if (toolbar != null) {
            return (String) toolbar.getNavigationContentDescription();
        } else {
            throw new RuntimeException("Toolbar is null!");
        }
    }
    
}
