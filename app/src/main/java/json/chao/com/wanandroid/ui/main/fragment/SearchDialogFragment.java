package json.chao.com.wanandroid.ui.main.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jakewharton.rxbinding2.view.RxView;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import json.chao.com.wanandroid.R;
import json.chao.com.wanandroid.app.Constants;
import json.chao.com.wanandroid.base.fragment.BaseDialogFragment;
import json.chao.com.wanandroid.contract.main.SearchContract;
import json.chao.com.wanandroid.core.bean.main.search.TopSearchData;
import json.chao.com.wanandroid.core.dao.HistoryData;
import json.chao.com.wanandroid.presenter.main.SearchPresenter;
import json.chao.com.wanandroid.ui.main.adapter.HistorySearchAdapter;
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
    @BindView(R.id.search_tint_tv)
    TextView mTintTv;
    @BindView(R.id.search_edit)
    EditText mSearchEdit;
    @BindView(R.id.search_tv)
    TextView mSearchTv;
    @BindView(R.id.search_history_clear_all_tv)
    TextView mClearAllHistoryTv;
    @BindView(R.id.search_scroll_view)
    NestedScrollView mSearchScrollView;
    @BindView(R.id.search_history_null_tint_tv)
    TextView mHistoryNullTintTv;
    @BindView(R.id.search_history_rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.top_search_flow_layout)
    TagFlowLayout mTopSearchFlowLayout;
    @BindView(R.id.search_floating_action_btn)
    FloatingActionButton mFloatingActionButton;

    private List<TopSearchData> mTopSearchDataList;
    private CircularRevealAnim mCircularRevealAnim;
    private HistorySearchAdapter historySearchAdapter;

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
    protected int getLayout() {
        return R.layout.fragment_search;
    }

    @Override
    protected void initEventAndData() {
        initCircleAnimation();
        initRecyclerView();
        mTopSearchDataList = new ArrayList<>();
        mSearchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(mSearchEdit.getText().toString())) {
                    mTintTv.setText(R.string.search_tint);
                } else {
                    mTintTv.setText("");
                }
            }
        });
        mPresenter.addRxBindingSubscribe(RxView.clicks(mSearchTv)
                .throttleFirst(Constants.CLICK_TIME_AREA, TimeUnit.MILLISECONDS)
                .filter(o -> !TextUtils.isEmpty(mSearchEdit.getText().toString().trim()))
                .subscribe(o -> {
                    mPresenter.addHistoryData(mSearchEdit.getText().toString().trim());
                    setHistoryTvStatus(false);
                }));

        showHistoryData(mPresenter.loadAllHistoryData());
        mPresenter.getTopSearchData();
    }

    @Override
    public void showHistoryData(List<HistoryData> historyDataList) {
        if (historyDataList == null || historyDataList.size() <= 0) {
            setHistoryTvStatus(true);
            return;
        }
        setHistoryTvStatus(false);
        Collections.reverse(historyDataList);
        historySearchAdapter.replaceData(historyDataList);
    }

    @Override
    public void showTopSearchData(List<TopSearchData> topSearchDataList) {
        mTopSearchDataList = topSearchDataList;
        mTopSearchFlowLayout.setAdapter(new TagAdapter<TopSearchData>(mTopSearchDataList) {
            @Override
            public View getView(FlowLayout parent, int position, TopSearchData topSearchData) {
                assert getActivity() != null;
                TextView tv = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.flow_layout_tv,
                        parent, false);
                assert topSearchData != null;
                String name = topSearchData.getName();
                tv.setText(name);
                setItemBackground(tv);
                mTopSearchFlowLayout.setOnTagClickListener((view, position1, parent1) -> {
                    showTopSearchView(position1);
                    return true;
                });
                return tv;
            }
        });
    }

    @Override
    public void judgeToTheSearchListActivity() {
        backEvent();
        JudgeUtils.startSearchListActivity(getActivity(), mSearchEdit.getText().toString().trim());
    }

    @Override
    public void onHideAnimationEnd() {
        mSearchEdit.setText("");
        dismissAllowingStateLoss();
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

    @OnClick({R.id.search_back_ib, R.id.search_floating_action_btn, R.id.search_history_clear_all_tv})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_back_ib:
                backEvent();
                break;
            case R.id.search_floating_action_btn:
                mSearchScrollView.smoothScrollTo(0, 0);
                break;
            case R.id.search_history_clear_all_tv:
                clearHistoryData();
                break;
            default:
                break;
        }
    }

    private void clearHistoryData() {
        mPresenter.clearHistoryData();
        historySearchAdapter.replaceData(new ArrayList<>());
        setHistoryTvStatus(true);
    }

    private void showTopSearchView(int position1) {
        mPresenter.addHistoryData(mTopSearchDataList.get(position1).getName().trim());
        setHistoryTvStatus(false);
        mSearchEdit.setText(mTopSearchDataList.get(position1).getName().trim());
        mSearchEdit.setSelection(mSearchEdit.getText().length());
    }

    private void initRecyclerView() {
        historySearchAdapter = new HistorySearchAdapter(R.layout.item_search_history, null);
        historySearchAdapter.setOnItemChildClickListener((adapter, view, position) -> searchHistoryData(adapter, position));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(historySearchAdapter);
    }

    private void searchHistoryData(BaseQuickAdapter adapter, int position) {
        HistoryData historyData = (HistoryData) adapter.getData().get(position);
        mPresenter.addHistoryData(historyData.getData());
        mSearchEdit.setText(historyData.getData());
        mSearchEdit.setSelection(mSearchEdit.getText().length());
        setHistoryTvStatus(false);
    }

    private void setItemBackground(TextView tv) {
        tv.setBackgroundColor(CommonUtils.randomTagColor());
        tv.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
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

    private void setHistoryTvStatus(boolean isClearAll) {
        mClearAllHistoryTv.setEnabled(!isClearAll);
        if (isClearAll) {
            setHistoryTvStatus(View.VISIBLE, R.color.search_grey_gone, R.drawable.ic_clear_all_gone);
        } else {
            setHistoryTvStatus(View.GONE, R.color.search_grey, R.drawable.ic_clear_all);
        }
    }

    private void setHistoryTvStatus(int visibility, @ColorRes int textColor, @DrawableRes int clearDrawable) {
        Drawable drawable;
        mHistoryNullTintTv.setVisibility(visibility);
        mClearAllHistoryTv.setTextColor(ContextCompat.getColor(getActivity(), textColor));
        drawable = ContextCompat.getDrawable(getActivity(), clearDrawable);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        mClearAllHistoryTv.setCompoundDrawables(drawable, null, null, null);
        mClearAllHistoryTv.setCompoundDrawablePadding(CommonUtils.dp2px(6));
    }

}
