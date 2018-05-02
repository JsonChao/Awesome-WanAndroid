package json.chao.com.wanandroid.utils;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import json.chao.com.wanandroid.app.Constants;
import json.chao.com.wanandroid.ui.hierarchy.activity.KnowledgeHierarchyDetailActivity;
import json.chao.com.wanandroid.ui.main.activity.ArticleDetailActivity;
import json.chao.com.wanandroid.ui.main.activity.SearchListActivity;

/**
 * @author quchao
 * @date 2018/2/24
 */

public class JudgeUtils {

    public static void startArticleDetailActivity(Context mActivity, ActivityOptions activityOptions, int id, String articleTitle,
                                                  String articleLink, boolean isCollect,
                                                  boolean isCollectPage,boolean isCommonSite) {
        Intent intent = new Intent(mActivity, ArticleDetailActivity.class);
        intent.putExtra(Constants.ARTICLE_ID, id);
        intent.putExtra(Constants.ARTICLE_TITLE, articleTitle);
        intent.putExtra(Constants.ARTICLE_LINK, articleLink);
        intent.putExtra(Constants.IS_COLLECT, isCollect);
        intent.putExtra(Constants.IS_COLLECT_PAGE, isCollectPage);
        intent.putExtra(Constants.IS_COMMON_SITE, isCommonSite);
        if (activityOptions != null && !Build.MANUFACTURER.contains("samsung") && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mActivity.startActivity(intent, activityOptions.toBundle());
        } else {
            mActivity.startActivity(intent);
        }
    }

    public static void startKnowledgeHierarchyDetailActivity(Context mActivity, boolean isSingleChapter,
                                                             String superChapterName, String chapterName, int chapterId) {
        Intent intent = new Intent(mActivity, KnowledgeHierarchyDetailActivity.class);
        intent.putExtra(Constants.IS_SINGLE_CHAPTER, isSingleChapter);
        intent.putExtra(Constants.SUPER_CHAPTER_NAME, superChapterName);
        intent.putExtra(Constants.CHAPTER_NAME, chapterName);
        intent.putExtra(Constants.CHAPTER_ID, chapterId);
        mActivity.startActivity(intent);
    }

    public static void startSearchListActivity(Context mActivity, String searchText) {
        Intent intent = new Intent(mActivity, SearchListActivity.class);
        intent.putExtra(Constants.SEARCH_TEXT, searchText);
        mActivity.startActivity(intent);
    }




}
