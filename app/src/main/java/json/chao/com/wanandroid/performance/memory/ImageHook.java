package json.chao.com.wanandroid.performance.memory;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.taobao.android.dexposed.XC_MethodHook;

import json.chao.com.wanandroid.utils.LogHelper;


/**
 * FileName: ImageHook
 * Date: 2019-12-28 10:41
 * Description:
 * @author quchao
 */
public class ImageHook extends XC_MethodHook {

    @Override
    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
        super.afterHookedMethod(param);
        // 实现我们的逻辑
        ImageView imageView = (ImageView) param.thisObject;
        checkBitmap(imageView,((ImageView) param.thisObject).getDrawable());
    }


    private static void checkBitmap(Object thiz, Drawable drawable) {
        if (drawable instanceof BitmapDrawable && thiz instanceof View) {
            final Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            if (bitmap != null) {
                final View view = (View) thiz;
                int width = view.getWidth();
                int height = view.getHeight();
                if (width > 0 && height > 0) {
                    // 图标宽高都大于view带下的2倍以上，则警告
                    if (bitmap.getWidth() >= (width << 1)
                            && bitmap.getHeight() >= (height << 1)) {
                        warn(bitmap.getWidth(), bitmap.getHeight(), width, height, new RuntimeException("Bitmap size too large"));
                    }
                } else {
                    final Throwable stackTrace = new RuntimeException();
                    view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                        @Override
                        public boolean onPreDraw() {
                            int w = view.getWidth();
                            int h = view.getHeight();
                            if (w > 0 && h > 0) {
                                if (bitmap.getWidth() >= (w << 1)
                                        && bitmap.getHeight() >= (h << 1)) {
                                    warn(bitmap.getWidth(), bitmap.getHeight(), w, h, stackTrace);
                                }
                                view.getViewTreeObserver().removeOnPreDrawListener(this);
                            }
                            return true;
                        }
                    });
                }
            }
        }
    }

    private static void warn(int bitmapWidth, int bitmapHeight, int viewWidth, int viewHeight, Throwable t) {
        String warnInfo = "Bitmap size too large: " +
                "\n real size: (" + bitmapWidth + ',' + bitmapHeight + ')' +
                "\n desired size: (" + viewWidth + ',' + viewHeight + ')' +
                "\n call stack trace: \n" + Log.getStackTraceString(t) + '\n';

        LogHelper.i(warnInfo);
    }

}
