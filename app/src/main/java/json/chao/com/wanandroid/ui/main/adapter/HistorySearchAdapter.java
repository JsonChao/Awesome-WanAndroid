package json.chao.com.wanandroid.ui.main.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import json.chao.com.wanandroid.R;
import json.chao.com.wanandroid.core.dao.HistoryData;
import json.chao.com.wanandroid.ui.main.viewholder.SearchHistoryViewHolder;
import json.chao.com.wanandroid.utils.CommonUtils;

/**
 * @author quchao
 * @date 2018/3/23
 */

public class HistorySearchAdapter extends BaseQuickAdapter<HistoryData, SearchHistoryViewHolder> {

    public HistorySearchAdapter(int layoutResId, @Nullable List<HistoryData> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(SearchHistoryViewHolder helper, HistoryData historyData) {
        helper.setText(R.id.item_search_history_tv, historyData.getData());
        helper.setTextColor(R.id.item_search_history_tv, CommonUtils.randomColor());

        helper.addOnClickListener(R.id.item_search_history_tv);
    }
}
