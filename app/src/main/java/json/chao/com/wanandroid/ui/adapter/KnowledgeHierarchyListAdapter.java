package json.chao.com.wanandroid.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import json.chao.com.wanandroid.R;
import json.chao.com.wanandroid.core.bean.FeedArticleData;
import json.chao.com.wanandroid.ui.viewholder.KnowledgeHierarchyListViewHolder;

/**
 * @author quchao
 * @date 2018/2/24
 */

public class KnowledgeHierarchyListAdapter extends BaseQuickAdapter<FeedArticleData, KnowledgeHierarchyListViewHolder> {

    private boolean isCollectPage;
    private boolean isSearchPage;

    public KnowledgeHierarchyListAdapter(int layoutResId, @Nullable List<FeedArticleData> data) {
        super(layoutResId, data);
    }

    public void isCollectPage() {
        isCollectPage = true;
        notifyDataSetChanged();
    }

    public void isSearchPage() {
        isSearchPage = true;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(KnowledgeHierarchyListViewHolder helper, FeedArticleData article) {
        if (!TextUtils.isEmpty(article.getTitle())) {
            helper.setText(R.id.item_search_pager_title, Html.fromHtml(article.getTitle()));
        }
        if (article.isCollect() || isCollectPage) {
            helper.setImageResource(R.id.item_search_pager_like_iv, R.mipmap.icon_like_article_selected);
        } else {
            helper.setImageResource(R.id.item_search_pager_like_iv, R.mipmap.icon_like_article_not_selected);
        }
        if (!TextUtils.isEmpty(article.getAuthor())) {
            helper.setText(R.id.item_search_pager_author, mContext.getString(R.string.item_article_author, article.getAuthor()));
        }
        if (!TextUtils.isEmpty(article.getChapterName())) {
            helper.setText(R.id.item_search_pager_chapterName, mContext.getString(R.string.item_article_classify, article.getChapterName()));
        }
        if (!TextUtils.isEmpty(article.getNiceDate())) {
            helper.setText(R.id.item_search_pager_niceDate, mContext.getString(R.string.item_article_time, article.getNiceDate()));
        }
        if (isSearchPage) {
            CardView cardView = helper.getView(R.id.item_search_pager_group);
            cardView.setForeground(null);
            cardView.setBackground(ContextCompat.getDrawable(mContext, R.drawable.selector_search_item_bac));
        }

        helper.addOnClickListener(R.id.item_search_pager_like_iv);
    }
}
