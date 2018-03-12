package json.chao.com.wanandroid.ui.activity;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewStub;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import json.chao.com.wanandroid.component.RxBus;
import json.chao.com.wanandroid.core.bean.KnowledgeHierarchyData;
import json.chao.com.wanandroid.R;
import json.chao.com.wanandroid.app.Constants;
import json.chao.com.wanandroid.base.BaseActivity;
import json.chao.com.wanandroid.base.BaseFragment;
import json.chao.com.wanandroid.contract.KnowledgeHierarchyDetailContract;
import json.chao.com.wanandroid.core.event.DismissDetailErrorView;
import json.chao.com.wanandroid.core.event.KnowledgeJumpTopEvent;
import json.chao.com.wanandroid.core.event.ReloadDetailEvent;
import json.chao.com.wanandroid.core.event.ShowDetailErrorView;
import json.chao.com.wanandroid.presenter.KnowledgeHierarchyDetailPresenter;
import json.chao.com.wanandroid.ui.fragment.KnowledgeHierarchyListFragment;

/**
 * @author quchao
 * @date 2018/2/23
 */

public class KnowledgeHierarchyDetailActivity extends BaseActivity<KnowledgeHierarchyDetailPresenter> implements KnowledgeHierarchyDetailContract.View {

    @BindView(R.id.common_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.common_toolbar_title_tv)
    TextView mTitleTv;
    @BindView(R.id.knowledge_hierarchy_detail_tab_layout)
    SlidingTabLayout mTabLayout;
    @BindView(R.id.knowledge_hierarchy_detail_viewpager)
    ViewPager mNoScrollViewPager;
    @BindView(R.id.knowledge_floating_action_btn)
    FloatingActionButton mFloatingActionButton;
    @BindView(R.id.detail_view_stub)
    ViewStub mErrorView;

    private KnowledgeHierarchyData mKnowledgeHierarchyData;
    private List<KnowledgeHierarchyData> knowledgeHierarchyDataList;
    private ArrayList<BaseFragment> mFragments;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_knowledge_hierarchy_detail;
    }

    @Override
    protected void initEventAndData() {
        mKnowledgeHierarchyData = (KnowledgeHierarchyData) getIntent().getSerializableExtra(Constants.ARG_PARAM1);
        initToolbar();
        knowledgeHierarchyDataList = mKnowledgeHierarchyData.getChildren();
        if (knowledgeHierarchyDataList == null) {
            return;
        }
        mFragments = new ArrayList<>();
        for (KnowledgeHierarchyData data : knowledgeHierarchyDataList) {
            mFragments.add(KnowledgeHierarchyListFragment.getInstance(data, null));
        }
        mNoScrollViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments == null? 0 : mFragments.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return knowledgeHierarchyDataList.get(position).getName();
            }
        });
        mTabLayout.setViewPager(mNoScrollViewPager);

        RxBus.getDefault().toFlowable(ShowDetailErrorView.class)
                .filter(showDetailErrorView -> mErrorView != null)
                .subscribe(showDetailErrorView -> showError());

        RxBus.getDefault().toFlowable(DismissDetailErrorView.class)
                .filter(dismissDetailErrorView -> mErrorView != null)
                .subscribe(dismissDetailErrorView -> mErrorView.setVisibility(View.GONE));
    }

    @Override
    public void showError() {
        mErrorView.setVisibility(View.VISIBLE);
        TextView reloadTv = (TextView) findViewById(R.id.error_reload_tv);
        reloadTv.setOnClickListener(v -> RxBus.getDefault().post(new ReloadDetailEvent()));
    }

    @OnClick({R.id.knowledge_floating_action_btn})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.knowledge_floating_action_btn:
                RxBus.getDefault().post(new KnowledgeJumpTopEvent());
                break;
            default:
                break;
        }
    }

    private void initToolbar() {
        assert mKnowledgeHierarchyData != null && mKnowledgeHierarchyData.getName() != null;
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowTitleEnabled(false);
        mTitleTv.setText(mKnowledgeHierarchyData.getName().trim());
        mToolbar.setNavigationOnClickListener(v -> onBackPressedSupport());
    }


}
