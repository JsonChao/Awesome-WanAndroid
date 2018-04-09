package json.chao.com.wanandroid.ui.main.fragment;

import android.app.ActivityOptions;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import json.chao.com.wanandroid.R;
import json.chao.com.wanandroid.base.fragment.BaseDialogFragment;
import json.chao.com.wanandroid.contract.main.UsageDialogContract;
import json.chao.com.wanandroid.core.DataManager;
import json.chao.com.wanandroid.core.bean.BaseResponse;
import json.chao.com.wanandroid.core.bean.main.search.UsefulSiteData;
import json.chao.com.wanandroid.presenter.main.UsageDialogPresenter;
import json.chao.com.wanandroid.utils.CommonUtils;
import json.chao.com.wanandroid.utils.JudgeUtils;
import json.chao.com.wanandroid.widget.CircularRevealAnim;

/**
 * @author quchao
 * @date 2018/4/2
 */

public class UsageDialogFragment extends BaseDialogFragment<UsageDialogPresenter> implements
        UsageDialogContract.View,
        CircularRevealAnim.AnimListener,
        ViewTreeObserver.OnPreDrawListener {

    @BindView(R.id.common_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.common_toolbar_title_tv)
    TextView mTitleTv;
    @BindView(R.id.useful_sites_flow_layout)
    TagFlowLayout mUsefulSitesFlowLayout;

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
        return R.layout.fragment_usage;
    }

    @Override
    protected void initEventAndData() {
        initCircleAnimation();
        initToolbar();
        mUsefulSiteDataList = new ArrayList<>();
        mPresenter.getUsefulSites();
    }

    @Override
    public void showUsefulSites(BaseResponse<List<UsefulSiteData>> usefulSitesResponse) {
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
                setItemBackground(tv);
                mUsefulSitesFlowLayout.setOnTagClickListener((view, position1, parent1) -> {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(), view, getString(R.string.share_view));
                    JudgeUtils.startArticleDetailActivity(getActivity(),
                            options,
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
    public void showUsefulSitesDataFail() {
        CommonUtils.showSnackMessage(getActivity(), getString(R.string.failed_to_obtain_useful_sites_data));
    }

    @Override
    public void onHideAnimationEnd() {
        dismiss();
    }

    @Override
    public void onShowAnimationEnd() {
    }

    @Override
    public boolean onPreDraw() {
        mTitleTv.getViewTreeObserver().removeOnPreDrawListener(this);
        mCircularRevealAnim.show(mTitleTv, mRootView);
        return true;
    }

    private void setItemBackground(TextView tv) {
        tv.setBackgroundColor(CommonUtils.randomTagColor());
        tv.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
    }

    private void initCircleAnimation() {
        mCircularRevealAnim = new CircularRevealAnim();
        mCircularRevealAnim.setAnimListener(this);
        mTitleTv.getViewTreeObserver().addOnPreDrawListener(this);
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

    private void initToolbar() {
        mTitleTv.setText(R.string.useful_sites);
        if (mPresenter.getNightModeState()) {
            mTitleTv.setTextColor(ContextCompat.getColor(getActivity(), R.color.comment_text));
            mToolbar.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorCard));
            mToolbar.setNavigationIcon(ContextCompat.getDrawable(getActivity(), R.drawable.ic_arrow_back_white_24dp));
        } else {
            mTitleTv.setTextColor(ContextCompat.getColor(getActivity(), R.color.title_black));
            mToolbar.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.white));
            mToolbar.setNavigationIcon(ContextCompat.getDrawable(getActivity(), R.drawable.ic_arrow_back_grey_24dp));
        }
        mToolbar.setNavigationOnClickListener(v -> mCircularRevealAnim.hide(mTitleTv, mRootView));
    }

}