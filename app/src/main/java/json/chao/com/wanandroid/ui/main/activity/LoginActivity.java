package json.chao.com.wanandroid.ui.main.activity;

import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.jakewharton.rxbinding2.view.RxView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import json.chao.com.wanandroid.app.Constants;
import json.chao.com.wanandroid.component.RxBus;
import json.chao.com.wanandroid.core.bean.main.login.LoginData;
import json.chao.com.wanandroid.R;
import json.chao.com.wanandroid.base.activity.BaseActivity;
import json.chao.com.wanandroid.contract.main.LoginContract;
import json.chao.com.wanandroid.core.event.LoginEvent;
import json.chao.com.wanandroid.presenter.main.LoginPresenter;
import json.chao.com.wanandroid.utils.CommonUtils;
import json.chao.com.wanandroid.utils.StatusBarUtil;
import json.chao.com.wanandroid.widget.RegisterPopupWindow;

/**
 * @author quchao
 * @date 2018/2/26
 */

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View, View.OnClickListener {

    @BindView(R.id.login_group)
    RelativeLayout mLoginGroup;
    @BindView(R.id.login_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.login_account_edit)
    EditText mAccountEdit;
    @BindView(R.id.login_password_edit)
    EditText mPasswordEdit;
    @BindView(R.id.login_btn)
    Button mLoginBtn;
    @BindView(R.id.login_register_btn)
    Button mRegisterBtn;

    private RegisterPopupWindow mPopupWindow;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initEventAndData() {
        mRegisterBtn.setOnClickListener(this);
        mPopupWindow = new RegisterPopupWindow(this, this);
        mPopupWindow.setBackgroundDrawable(getDrawable(R.color.transparent));
        mPopupWindow.setAnimationStyle(R.style.popup_window_animation);
        mPopupWindow.setOnDismissListener(() -> mRegisterBtn.setOnClickListener(this));
        StatusBarUtil.immersive(this);
        StatusBarUtil.setPaddingSmart(this, mToolbar);
        mToolbar.setNavigationOnClickListener(v -> onBackPressedSupport());

        mPresenter.addRxBindingSubscribe(RxView.clicks(mLoginBtn)
                .throttleFirst(Constants.CLICK_TIME_AREA, TimeUnit.MILLISECONDS)
                .filter(o -> mPresenter != null)
                .subscribe(o -> {
                    String account = mAccountEdit.getText().toString().trim();
                    String password = mPasswordEdit.getText().toString().trim();
                    if (TextUtils.isEmpty(account) || TextUtils.isEmpty(password)) {
                        CommonUtils.showSnackMessage(this, getString(R.string.account_password_null_tint));
                        return;
                    }
                    mPresenter.getLoginData(account, password);
                }));
    }

    @Override
    public void showLoginData(LoginData loginData) {
        mPresenter.setLoginAccount(loginData.getUsername());
        mPresenter.setLoginPassword(loginData.getPassword());
        mPresenter.setLoginStatus(true);
        RxBus.getDefault().post(new LoginEvent(true));
        CommonUtils.showSnackMessage(this, getString(R.string.login_success));
        onBackPressedSupport();
    }

    @Override
    public void showRegisterData(LoginData loginData) {
        mPresenter.getLoginData(loginData.getUsername(), loginData.getPassword());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_register_btn:
                mPopupWindow.showAtLocation(mLoginGroup, Gravity.CENTER, 0, 0);
                mRegisterBtn.setOnClickListener(null);
                break;
            case R.id.register_btn:
                register();
            default:
                break;
        }
    }

    private void register() {
        if (mPopupWindow == null || mPresenter == null) {
            return;
        }
        String account = mPopupWindow.mUserNameEdit.getText().toString().trim();
        String password = mPopupWindow.mPasswordEdit.getText().toString().trim();
        String rePassword = mPopupWindow.mRePasswordEdit.getText().toString().trim();

        if (TextUtils.isEmpty(account) || TextUtils.isEmpty(password) || TextUtils.isEmpty(rePassword)) {
            CommonUtils.showSnackMessage(this, getString(R.string.account_password_null_tint));
            return;
        }

        if (!password.equals(rePassword)) {
            CommonUtils.showSnackMessage(this, getString(R.string.password_not_same));
            return;
        }

        mPresenter.getRegisterData(account, password, rePassword);
    }
}
