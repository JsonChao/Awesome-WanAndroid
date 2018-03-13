package json.chao.com.wanandroid.ui.navigation.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import json.chao.com.wanandroid.core.bean.main.collect.FeedArticleData;
import json.chao.com.wanandroid.core.bean.navigation.NavigationListData;
import json.chao.com.wanandroid.R;
import json.chao.com.wanandroid.app.Constants;
import json.chao.com.wanandroid.base.fragment.BaseFragment;
import json.chao.com.wanandroid.contract.navigation.NavigationListContract;
import json.chao.com.wanandroid.presenter.navigation.NavigationListPresenter;
import json.chao.com.wanandroid.utils.CommonUtils;
import json.chao.com.wanandroid.utils.JudgeUtils;

/**
 * @author quchao
 * @date 2018/2/24
 */

public class NavigationListFragment extends BaseFragment<NavigationListPresenter> implements NavigationListContract.View {

    @BindView(R.id.navigation_list_flow_layout)
    TagFlowLayout mTagFlowLayout;
    @BindView(R.id.navigation_hint_tv)
    TextView mNavigationHintTv;

    private List<FeedArticleData> mArticles;

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_navigation_list;
    }

    @Override
    protected void initEventAndData() {
        NavigationListData data = ((NavigationListData) getArguments().getSerializable(Constants.ARG_PARAM1));
        if (data == null || data.getArticles() == null) {
            return;
        }
        mArticles = data.getArticles();
        String chapterName = mArticles.get(0).getChapterName();
        mNavigationHintTv.setText(getString(R.string.navigation_tint, chapterName));
        mNavigationHintTv.setTextColor(CommonUtils.intrandomColor());
        mNavigationHintTv.setVisibility(View.VISIBLE);

        mTagFlowLayout.setAdapter(new TagAdapter<FeedArticleData>(mArticles) {
            @Override
            public View getView(FlowLayout parent, int position, FeedArticleData feedArticleData) {
                assert _mActivity != null;
                TextView tv = (TextView) LayoutInflater.from(_mActivity).inflate(R.layout.flow_layout_tv,
                        mTagFlowLayout, false);
                assert feedArticleData != null;
                String name = feedArticleData.getTitle();
                tv.setText(name);
                tv.setTextColor(CommonUtils.intrandomColor());
                mTagFlowLayout.setOnTagClickListener((view, position1, parent1) -> {
                    JudgeUtils.startArticleDetailActivity(_mActivity,
                            mArticles.get(position1).getId(),
                            mArticles.get(position1).getTitle(),
                            mArticles.get(position1).getLink(),
                            mArticles.get(position1).isCollect(),
                            false,
                            false);
                    return true;
                });
                return tv;
            }
        });
    }

    public static NavigationListFragment getInstance(Serializable param1, String param2) {
        NavigationListFragment fragment = new NavigationListFragment();
        Bundle args = new Bundle();
        args.putSerializable(Constants.ARG_PARAM1, param1);
        args.putString(Constants.ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }




}
