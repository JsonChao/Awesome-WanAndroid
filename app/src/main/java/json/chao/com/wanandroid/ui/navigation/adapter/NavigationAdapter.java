package json.chao.com.wanandroid.ui.navigation.adapter;

import android.app.ActivityOptions;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

import json.chao.com.wanandroid.R;
import json.chao.com.wanandroid.core.bean.main.collect.FeedArticleData;
import json.chao.com.wanandroid.core.bean.navigation.NavigationListData;
import json.chao.com.wanandroid.ui.navigation.viewholder.NavigationViewHolder;
import json.chao.com.wanandroid.utils.CommonUtils;
import json.chao.com.wanandroid.utils.JudgeUtils;


/**
 * @author quchao
 * @date 2018/3/2
 */

public class NavigationAdapter extends BaseQuickAdapter<NavigationListData, NavigationViewHolder> {

    public NavigationAdapter(int layoutResId, @Nullable List<NavigationListData> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(NavigationViewHolder helper, NavigationListData item) {
        if (!TextUtils.isEmpty(item.getName())) {
            helper.setText(R.id.item_navigation_tv, item.getName());
        }
        TagFlowLayout mTagFlowLayout = helper.getView(R.id.item_navigation_flow_layout);
        List<FeedArticleData> mArticles = item.getArticles();
        mTagFlowLayout.setAdapter(new TagAdapter<FeedArticleData>(mArticles) {
            @Override
            public View getView(FlowLayout parent, int position, FeedArticleData feedArticleData) {
                TextView tv = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.flow_layout_tv,
                        mTagFlowLayout, false);
                if (feedArticleData == null) {
                    return null;
                }
                String name = feedArticleData.getTitle();
                tv.setPadding(CommonUtils.dp2px(10), CommonUtils.dp2px(10),
                        CommonUtils.dp2px(10), CommonUtils.dp2px(10));
                tv.setText(name);
                tv.setTextColor(CommonUtils.randomColor());
                mTagFlowLayout.setOnTagClickListener((view, position1, parent1) -> {
                    startNavigationPager(view, position1, parent, mArticles);
                    return true;
                });
                return tv;
            }
        });
    }

    private void startNavigationPager(View view, int position1, FlowLayout parent2, List<FeedArticleData> mArticles) {
        ActivityOptions options = ActivityOptions.makeScaleUpAnimation(view,
                view.getWidth() / 2,
                view.getHeight() / 2,
                0 ,
                0);
        JudgeUtils.startArticleDetailActivity(parent2.getContext(),
                options,
                mArticles.get(position1).getId(),
                mArticles.get(position1).getTitle(),
                mArticles.get(position1).getLink(),
                mArticles.get(position1).isCollect(),
                false,
                false);
    }

}
