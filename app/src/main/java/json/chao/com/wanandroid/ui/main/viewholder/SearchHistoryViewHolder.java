package json.chao.com.wanandroid.ui.main.viewholder;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;
import json.chao.com.wanandroid.R;

/**
 * @author quchao
 * @date 2018/3/23
 */

public class SearchHistoryViewHolder extends BaseViewHolder {

    @BindView(R.id.item_search_history_tv)
    TextView mSearchHistoryTv;

    public SearchHistoryViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
