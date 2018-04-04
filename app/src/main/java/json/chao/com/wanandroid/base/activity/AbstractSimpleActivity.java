package json.chao.com.wanandroid.base.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportActivity;
import json.chao.com.wanandroid.component.ActivityCollector;

/**
 * Common simple Activity
 *
 * @author quchao
 * @date 2017/11/28
 */

public abstract class AbstractSimpleActivity extends SupportActivity {

    private Unbinder unBinder;
    protected AbstractSimpleActivity mActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        unBinder = ButterKnife.bind(this);
        mActivity = this;
        onViewCreated();
        ActivityCollector.getInstance().addActivity(this);
        initEventAndData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.getInstance().removeActivity(this);
        unBinder.unbind();
    }

    protected void setToolBar(Toolbar toolBar, CharSequence title) {
        toolBar.setTitle(title);
        setSupportActionBar(toolBar);
        assert getSupportActionBar() != null;
    }

    protected void onViewCreated() {

    }

    /**
     * 获取当前Activity的UI布局
     *
     * @return 布局id
     */
    protected abstract int getLayoutId();

    /**
     * 初始化数据
     */
    protected abstract void initEventAndData();

}
