package json.chao.com.wanandroid.ui.main.fragment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.NestedScrollView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import json.chao.com.wanandroid.R;
import json.chao.com.wanandroid.app.Constants;
import json.chao.com.wanandroid.app.GeeksApp;
import json.chao.com.wanandroid.base.fragment.BaseDialogFragment;
import json.chao.com.wanandroid.contract.main.SearchContract;
import json.chao.com.wanandroid.core.DataManager;
import json.chao.com.wanandroid.core.bean.main.search.TopSearchData;
import json.chao.com.wanandroid.core.bean.main.search.TopSearchDataResponse;
import json.chao.com.wanandroid.core.bean.main.search.UsefulSiteData;
import json.chao.com.wanandroid.core.bean.main.search.UsefulSitesResponse;
import json.chao.com.wanandroid.core.dao.HistoryData;
import json.chao.com.wanandroid.presenter.main.SearchPresenter;
import json.chao.com.wanandroid.utils.CommonUtils;
import json.chao.com.wanandroid.utils.JudgeUtils;
import json.chao.com.wanandroid.utils.KeyBoardUtils;
import json.chao.com.wanandroid.widget.CircularRevealAnim;

/**
 * @author quchao
 * @date 2018/3/1
 */

public class SearchDialogFragment extends BaseDialogFragment<SearchPresenter> implements
        SearchContract.View,
        CircularRevealAnim.AnimListener,
        ViewTreeObserver.OnPreDrawListener {

    @BindView(R.id.search_back_ib)
    ImageButton mBackIb;
    @BindView(R.id.search_edit)
    EditText mSearchEdit;
    @BindView(R.id.search_tv)
    TextView mSearchTv;
    @BindView(R.id.search_scroll_view)
    NestedScrollView mSearchScrollView;
    @BindView(R.id.history_search_flow_layout)
    TagFlowLayout mHistorySearchFlowLayout;
    @BindView(R.id.top_search_flow_layout)
    TagFlowLayout mTopSearchFlowLayout;
    @BindView(R.id.useful_sites_flow_layout)
    TagFlowLayout mUsefulSitesFlowLayout;
    @BindView(R.id.search_floating_action_btn)
    FloatingActionButton mFloatingActionButton;

    private List<TopSearchData> mTopSearchDataList;
    private List<UsefulSiteData> mUsefulSiteDataList;

    @Inject
    DataManager mDataManager;
    private CircularRevealAnim mCircularRevealAnim;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.DialogStyle);
    }

    @Override
    public void onStart() {
        super.onStart();
        initDialog();
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_search;
    }
    
    @Override
    protected void initEventAndData() {
        initCircleAnimation();
        mTopSearchDataList = new ArrayList<>();
        mUsefulSiteDataList = new ArrayList<>();
        RxView.clicks(mSearchTv)
                .throttleFirst(Constants.CLICK_TIME_AREA, TimeUnit.MILLISECONDS)
                .filter(o -> !TextUtils.isEmpty(mSearchEdit.getText().toString().trim()))
                .subscribe(o -> {
                    mPresenter.addHistoryData(mSearchEdit.getText().toString().trim());
                    backEvent();
                    JudgeUtils.startSearchListActivity(getActivity(), mSearchEdit.getText().toString().trim());
                });

        showHistoryData(GeeksApp.getInstance().getDaoSession().getHistoryDataDao().loadAll());
        mPresenter.getTopSearchData();
        mPresenter.getUsefulSites();
    }

    @Override
    public void showTopSearchData(TopSearchDataResponse topSearchDataResponse) {
        if (topSearchDataResponse == null) {
            showTopSearchDataFail();
            return;
        }
        mTopSearchDataList = topSearchDataResponse.getData();
        mTopSearchFlowLayout.setAdapter(new TagAdapter<TopSearchData>(mTopSearchDataList) {
            @Override
            public View getView(FlowLayout parent, int position, TopSearchData topSearchData) {
                assert getActivity() != null;
                TextView tv = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.flow_layout_tv,
                        parent, false);
                assert topSearchData != null;
                String name = topSearchData.getName();
                tv.setText(name);
                tv.setTextColor(CommonUtils.randomColor());
                mTopSearchFlowLayout.setOnTagClickListener((view, position1, parent1) -> {
                    mSearchEdit.setText(mTopSearchDataList.get(position1).getName().trim());
                    mSearchEdit.setSelection(mSearchEdit.getText().length());
                    backEvent();
                    JudgeUtils.startSearchListActivity(getActivity(), mSearchEdit.getText().toString().trim());
                    mPresenter.addHistoryData(mTopSearchDataList.get(position1).getName().trim());
                    return true;
                });
                return tv;
            }
        });
    }

    @Override
    public void showUsefulSites(UsefulSitesResponse usefulSitesResponse) {
        if (usefulSitesResponse == null) {
            showUsefulSitesDataFail();
            return;
        }
        mUsefulSiteDataList = usefulSitesResponse.getData();
        mUsefulSitesFlowLayout.setAdapter(new TagAdapter<UsefulSiteData>(mUsefulSiteDataList) {
            @Override
            public View getView(FlowLayout parent, int position, UsefulSiteData usefulSiteData) {
                assert getActivity() != null;
                TextView tv = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.flow_layout_tv,
                        parent, false);
                assert usefulSiteData != null;
                String name = usefulSiteData.getName();
                tv.setText(name);
                tv.setTextColor(CommonUtils.randomColor());
                mUsefulSitesFlowLayout.setOnTagClickListener((view, position1, parent1) -> {
                    JudgeUtils.startArticleDetailActivity(getActivity(),
                            mUsefulSiteDataList.get(position1).getId(),
                            mUsefulSiteDataList.get(position1).getName().trim(),
                            mUsefulSiteDataList.get(position1).getLink().trim(),
                            false,
                            false,
                            true);
                    return true;
                });
                return tv;
            }
        });
    }

    @Override
    public void showHistoryData(List<HistoryData> historyDataList) {
        if (historyDataList == null) {
            return;
        }
        Collections.reverse(historyDataList);
        mHistorySearchFlowLayout.setAdapter(new TagAdapter<HistoryData>(historyDataList) {
            @Override
            public View getView(FlowLayout parent, int position, HistoryData historyData) {
                assert getActivity() != null;
                TextView tv = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.flow_layout_tv,
                        parent, false);
                assert historyData != null;
                String data = historyData.getData();
                tv.setText(data);
                tv.setTextColor(CommonUtils.randomColor());
                mHistorySearchFlowLayout.setOnTagClickListener((view, position1, parent1) -> {
                    mSearchEdit.setText(historyDataList.get(position1).getData().trim());
                    mSearchEdit.setSelection(mSearchEdit.getText().length());
                    backEvent();
                    JudgeUtils.startSearchListActivity(getActivity(), mSearchEdit.getText().toString().trim());
                    mPresenter.addHistoryData(historyDataList.get(position1).getData().trim());
                    return true;
                });
                return tv;
            }
        });
    }

    @Override
    public void showTopSearchDataFail() {
        CommonUtils.showMessage(getActivity(), getString(R.string.failed_to_obtain_top_data));
    }

    @Override
    public void showUsefulSitesDataFail() {
        CommonUtils.showMessage(getActivity(), getString(R.string.failed_to_obtain_useful_sites_data));
    }

    @Override
    public void onHideAnimationEnd() {
        mSearchEdit.setText("");
        dismiss();
    }

    @Override
    public void onShowAnimationEnd() {
        KeyBoardUtils.openKeyboard(getActivity(), mSearchEdit);
    }

    @Override
    public boolean onPreDraw() {
        mSearchEdit.getViewTreeObserver().removeOnPreDrawListener(this);
        mCircularRevealAnim.show(mSearchEdit, mRootView);
        return true;
    }

    @OnClick({R.id.search_back_ib, R.id.search_floating_action_btn})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_back_ib:
                backEvent();
                break;
            case R.id.search_floating_action_btn:
                mSearchScrollView.smoothScrollTo(0, 0);
                break;
            default:
                break;
        }
    }

    private void initCircleAnimation() {
        mCircularRevealAnim = new CircularRevealAnim();
        mCircularRevealAnim.setAnimListener(this);
        mSearchEdit.getViewTreeObserver().addOnPreDrawListener(this);
    }

    private void initDialog() {
        Window window = getDialog().getWindow();
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        //DialogSearch的宽
        int width = (int) (metrics.widthPixels * 0.98);
        assert window != null;
        window.setLayout(width, WindowManager.LayoutParams.MATCH_PARENT);
        window.setGravity(Gravity.TOP);
        //取消过渡动画 , 使DialogSearch的出现更加平滑
        window.setWindowAnimations(R.style.DialogEmptyAnimation);
    }

    public void backEvent() {
        KeyBoardUtils.closeKeyboard(getActivity(), mSearchEdit);
        mCircularRevealAnim.hide(mSearchEdit, mRootView);
    }

}
