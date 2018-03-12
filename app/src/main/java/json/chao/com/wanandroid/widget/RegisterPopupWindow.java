package json.chao.com.wanandroid.widget;

import android.app.Activity;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;

import json.chao.com.wanandroid.R;

/**
 * @author quchao
 * @date 2018/2/26
 */

public class RegisterPopupWindow extends PopupWindow {

    public EditText mUserNameEdit;

    public EditText mPasswordEdit;

    public EditText mRePasswordEdit;


    public RegisterPopupWindow(Activity mContext, View.OnClickListener itemsOnClick) {
        View mRootView = LayoutInflater.from(mContext).inflate(R.layout.popup_window_register, null);

        mUserNameEdit = (EditText) mRootView.findViewById(R.id.register_account_edit);
        mPasswordEdit = (EditText) mRootView.findViewById(R.id.register_password_edit);
        mRePasswordEdit = (EditText) mRootView.findViewById(R.id.register_re_password_edit);

        Button registerBtn = (Button) mRootView.findViewById(R.id.register_btn);

        // 设置按钮监听
        registerBtn.setOnClickListener(itemsOnClick);

        // 设置外部可点击
        this.setOutsideTouchable(true);

        // 设置视图
        this.setContentView(mRootView);

        /*
         * 设置弹出窗体的宽和高,获取圣诞框的窗口对象及参数对象以修改对话框的布局设置,
         * 可以直接调用getWindow(),表示获得这个Activity的Window
         * 对象,这样这可以以同样的方式改变这个Activity的属性.
         */
        Window dialogWindow = mContext.getWindow();

        WindowManager m = mContext.getWindowManager();
        // 获取屏幕宽、高用
        Display d = m.getDefaultDisplay();
        // 获取对话框当前的参数值
        WindowManager.LayoutParams p = dialogWindow.getAttributes();

        this.setHeight((int) (d.getHeight() * 0.5));
        this.setWidth((int) (d.getWidth() * 0.7));

        // 设置弹出窗体可点击
        this.setFocusable(true);

    }
}