package json.chao.com.wanandroid.ui.main.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.just.agentweb.AgentWeb;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.lang.reflect.Method;

import butterknife.BindView;
import json.chao.com.wanandroid.R;
import json.chao.com.wanandroid.app.Constants;
import json.chao.com.wanandroid.base.activity.BaseActivity;
import json.chao.com.wanandroid.component.RxBus;
import json.chao.com.wanandroid.contract.main.ArticleDetailContract;
import json.chao.com.wanandroid.core.bean.main.collect.FeedArticleListData;
import json.chao.com.wanandroid.core.event.CollectEvent;
import json.chao.com.wanandroid.presenter.main.ArticleDetailPresenter;
import json.chao.com.wanandroid.utils.CommonUtils;
import json.chao.com.wanandroid.utils.StatusBarUtil;

/**
 * @author quchao
 * @date 2018/2/13
 */
public class ArticleDetailActivity extends BaseActivity<ArticleDetailPresenter> implements ArticleDetailContract.View {

    @BindView(R.id.article_detail_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.article_detail_web_view)
    FrameLayout mWebContent;

    private Bundle bundle;
    private MenuItem mCollectItem;
    private int articleId;
    private String articleLink;
    private String title;

    private boolean isCollect;
    private boolean isCommonSite;
    private boolean isCollectPage;
    private AgentWeb mAgentWeb;

    @Override
    protected void onPause() {
        mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }
    @Override
    public void onDestroy() {
        mAgentWeb.getWebLifeCycle().onDestroy();
        super.onDestroy();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_article_detail;
    }

    @Override
    protected void initToolbar() {
        getBundleData();
        mToolbar.setTitle(Html.fromHtml(title));
        setSupportActionBar(mToolbar);
        StatusBarUtil.immersive(this);
        StatusBarUtil.setPaddingSmart(this, mToolbar);
        mToolbar.setNavigationOnClickListener(v -> {
            if (isCollect) {
                RxBus.getDefault().post(new CollectEvent(false));
            } else {
                RxBus.getDefault().post(new CollectEvent(true));
            }
            onBackPressedSupport();
        });
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void initEventAndData() {
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(mWebContent, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .setMainFrameErrorView(R.layout.webview_error_view, -1)
                .createAgentWeb()
                .ready()
                .go(articleLink);

        WebView mWebView = mAgentWeb.getWebCreator().getWebView();
        WebSettings mSettings = mWebView.getSettings();
        if (mPresenter.getNoImageState()) {
            mSettings.setBlockNetworkImage(true);
        } else {
            mSettings.setBlockNetworkImage(false);
        }
        if (mPresenter.getAutoCacheState()) {
            mSettings.setAppCacheEnabled(true);
            mSettings.setDomStorageEnabled(true);
            mSettings.setDatabaseEnabled(true);
            if (CommonUtils.isNetworkConnected()) {
                mSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
            } else {
                mSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            }
        } else {
            mSettings.setAppCacheEnabled(false);
            mSettings.setDomStorageEnabled(false);
            mSettings.setDatabaseEnabled(false);
            mSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        }

        mSettings.setJavaScriptEnabled(true);
        mSettings.setSupportZoom(true);
        mSettings.setBuiltInZoomControls(true);
        //不显示缩放按钮
        mSettings.setDisplayZoomControls(false);
        //设置自适应屏幕，两者合用
        //将图片调整到适合WebView的大小
        mSettings.setUseWideViewPort(true);
        //缩放至屏幕的大小
        mSettings.setLoadWithOverviewMode(true);
        //自适应屏幕
        mSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return mAgentWeb.handleKeyEvent(keyCode, event) || super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        bundle = getIntent().getExtras();
        assert bundle != null;
        isCommonSite = (boolean) bundle.get(Constants.IS_COMMON_SITE);
        if (!isCommonSite) {
            unCommonSiteEvent(menu);
        } else {
            getMenuInflater().inflate(R.menu.menu_article_common, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_share:
                mPresenter.shareEventPermissionVerify(new RxPermissions(this));
                break;
            case R.id.item_collect:
                collectEvent();
                break;
            case R.id.item_system_browser:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(articleLink)));
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 让菜单同时显示图标和文字
     *
     * @param featureId Feature id
     * @param menu Menu
     * @return menu if opened
     */
    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (menu != null) {
            if (Constants.MENU_BUILDER.equalsIgnoreCase(menu.getClass().getSimpleName())) {
                try {
                    @SuppressLint("PrivateApi")
                    Method method = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    method.setAccessible(true);
                    method.invoke(menu, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }

    @Override
    public void shareEvent() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_type_url, getString(R.string.app_name), title, articleLink));
        intent.setType("text/plain");
        startActivity(intent);
    }

    @Override
    public void shareError() {
        CommonUtils.showMessage(this, getString(R.string.write_permission_not_allowed));
    }

    @Override
    public void onBackPressedSupport() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            pop();
        } else {
            supportFinishAfterTransition();
        }
    }

    @Override
    public void showCollectArticleData(FeedArticleListData feedArticleListData) {
        isCollect = true;
        mCollectItem.setTitle(R.string.cancel_collect);
        mCollectItem.setIcon(R.mipmap.ic_toolbar_like_p);
        CommonUtils.showMessage(this, getString(R.string.collect_success));
    }

    @Override
    public void showCancelCollectArticleData(FeedArticleListData feedArticleListData) {
        isCollect = false;
        if (!isCollectPage) {
            mCollectItem.setTitle(R.string.collect);
        }
        mCollectItem.setIcon(R.mipmap.ic_toolbar_like_n);
        CommonUtils.showMessage(this, getString(R.string.cancel_collect_success));
    }

    private void unCommonSiteEvent(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_acticle, menu);
        mCollectItem = menu.findItem(R.id.item_collect);
        if (isCollect) {
            mCollectItem.setTitle(getString(R.string.cancel_collect));
            mCollectItem.setIcon(R.mipmap.ic_toolbar_like_p);
        } else {
            mCollectItem.setTitle(getString(R.string.collect));
            mCollectItem.setIcon(R.mipmap.ic_toolbar_like_n);
        }
    }

    private void collectEvent() {
        if (!mPresenter.getLoginStatus()) {
            CommonUtils.showMessage(this, getString(R.string.login_tint));
            startActivity(new Intent(this, LoginActivity.class));
        } else {
            if (mCollectItem.getTitle().equals(getString(R.string.collect))) {
                mPresenter.addCollectArticle(articleId);
            } else {
                if (isCollectPage) {
                    mPresenter.cancelCollectPageArticle(articleId);
                } else {
                    mPresenter.cancelCollectArticle(articleId);
                }
            }
        }
    }

    private void getBundleData() {
        bundle = getIntent().getExtras();
        assert bundle != null;
        title = (String) bundle.get(Constants.ARTICLE_TITLE);
        articleLink = (String) bundle.get(Constants.ARTICLE_LINK);
        articleId = ((int) bundle.get(Constants.ARTICLE_ID));
        isCommonSite = ((boolean) bundle.get(Constants.IS_COMMON_SITE));
        isCollect = ((boolean) bundle.get(Constants.IS_COLLECT));
        isCollectPage = ((boolean) bundle.get(Constants.IS_COLLECT_PAGE));
    }


}
