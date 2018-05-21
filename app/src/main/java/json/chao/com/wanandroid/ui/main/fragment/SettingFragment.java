package json.chao.com.wanandroid.ui.main.fragment;

import android.os.Bundle;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import json.chao.com.wanandroid.R;
import json.chao.com.wanandroid.app.Constants;
import json.chao.com.wanandroid.base.fragment.BaseFragment;
import json.chao.com.wanandroid.component.ACache;
import json.chao.com.wanandroid.component.RxBus;
import json.chao.com.wanandroid.contract.main.SettingContract;
import json.chao.com.wanandroid.core.event.NightModeEvent;
import json.chao.com.wanandroid.presenter.main.SettingPresenter;
import json.chao.com.wanandroid.utils.ShareUtil;

/**
 * @author quchao
 * @date 2018/4/2
 */

public class SettingFragment extends BaseFragment<SettingPresenter> implements
        SettingContract.View,
        CompoundButton.OnCheckedChangeListener {

    @BindView(R.id.cb_setting_cache)
    AppCompatCheckBox mCbSettingCache;
    @BindView(R.id.cb_setting_image)
    AppCompatCheckBox mCbSettingImage;
    @BindView(R.id.cb_setting_night)
    AppCompatCheckBox mCbSettingNight;
    @BindView(R.id.ll_setting_feedback)
    TextView mLlSettingFeedback;
    @BindView(R.id.ll_setting_clear)
    LinearLayout mLlSettingClear;
    @BindView(R.id.tv_setting_clear)
    TextView mTvSettingClear;

    private File cacheFile;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_setting;
    }

    @Override
    protected void initEventAndData() {
        cacheFile = new File(Constants.PATH_CACHE);
        mTvSettingClear.setText(ACache.getCacheSize(cacheFile));
        mCbSettingCache.setChecked(mPresenter.getAutoCacheState());
        mCbSettingImage.setChecked(mPresenter.getNoImageState());
        mCbSettingNight.setChecked(mPresenter.getNightModeState());
        mCbSettingCache.setOnCheckedChangeListener(this);
        mCbSettingImage.setOnCheckedChangeListener(this);
        mCbSettingNight.setOnCheckedChangeListener(this);
    }

    @OnClick({R.id.ll_setting_feedback, R.id.ll_setting_clear})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_setting_feedback:
                ShareUtil.sendEmail(_mActivity, getString(R.string.send_email));
                break;
            case R.id.ll_setting_clear:
                clearCache();
                break;
            default:
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.cb_setting_night:
                mPresenter.setNightModeState(b);
                RxBus.getDefault().post(new NightModeEvent(b));
                break;
            case R.id.cb_setting_image:
                mPresenter.setNoImageState(b);
                break;
            case R.id.cb_setting_cache:
                mPresenter.setAutoCacheState(b);
                break;
            default:
                break;
        }
    }

    public static SettingFragment getInstance(String param1, String param2) {
        SettingFragment fragment = new SettingFragment();
        Bundle args = new Bundle();
        args.putString(Constants.ARG_PARAM1, param1);
        args.putString(Constants.ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private void clearCache() {
        ACache.deleteDir(cacheFile);
        mTvSettingClear.setText(ACache.getCacheSize(cacheFile));
    }


}
