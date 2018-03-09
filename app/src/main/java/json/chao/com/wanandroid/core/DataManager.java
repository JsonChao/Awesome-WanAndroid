package json.chao.com.wanandroid.core;


import io.reactivex.Observable;
import json.chao.com.wanandroid.core.bean.BannerResponse;
import json.chao.com.wanandroid.core.bean.FeedArticleListResponse;
import json.chao.com.wanandroid.core.bean.KnowledgeHierarchyResponse;
import json.chao.com.wanandroid.core.bean.LoginResponse;
import json.chao.com.wanandroid.core.bean.NavigationListResponse;
import json.chao.com.wanandroid.core.bean.ProjectClassifyResponse;
import json.chao.com.wanandroid.core.bean.ProjectListResponse;
import json.chao.com.wanandroid.core.bean.TopSearchDataResponse;
import json.chao.com.wanandroid.core.bean.UsefulSitesResponse;
import json.chao.com.wanandroid.core.db.DBHelper;
import json.chao.com.wanandroid.core.http.HttpHelper;
import json.chao.com.wanandroid.core.prefs.PreferenceHelper;

/**
 * @author quchao
 * @date 2017/11/27
 */

public class DataManager implements HttpHelper, DBHelper, PreferenceHelper {

    private HttpHelper mHttpHelper;
    private DBHelper mDBHelper;
    private PreferenceHelper mPreferenceHelper;

    public DataManager(HttpHelper httpHelper, DBHelper dbHelper, PreferenceHelper preferencesHelper) {
        mHttpHelper = httpHelper;
        mDBHelper = dbHelper;
        mPreferenceHelper = preferencesHelper;
    }

    @Override
    public Observable<FeedArticleListResponse> getFeedArticleList(int pageNum) {
        return mHttpHelper.getFeedArticleList(pageNum);
    }

    @Override
    public Observable<FeedArticleListResponse> getSearchList(int pageNum, String k) {
        return mHttpHelper.getSearchList(pageNum, k);
    }

    @Override
    public Observable<TopSearchDataResponse> getTopSearchData() {
        return mHttpHelper.getTopSearchData();
    }

    @Override
    public Observable<UsefulSitesResponse> getUsefulSites() {
        return mHttpHelper.getUsefulSites();
    }

    @Override
    public Observable<KnowledgeHierarchyResponse> getKnowledgeHierarchyData() {
        return mHttpHelper.getKnowledgeHierarchyData();
    }

    @Override
    public Observable<FeedArticleListResponse> getKnowledgeHierarchyDetailData(int page, int cid) {
        return mHttpHelper.getKnowledgeHierarchyDetailData(page, cid);
    }

    @Override
    public Observable<NavigationListResponse> getNavigationListData() {
        return mHttpHelper.getNavigationListData();
    }

    @Override
    public Observable<ProjectClassifyResponse> getProjectClassifyData() {
        return mHttpHelper.getProjectClassifyData();
    }

    @Override
    public Observable<ProjectListResponse> getProjectListData(int page, int cid) {
        return mHttpHelper.getProjectListData(page, cid);
    }

    @Override
    public Observable<LoginResponse> getLoginData(String username, String password) {
        return mHttpHelper.getLoginData(username, password);
    }

    @Override
    public Observable<LoginResponse> getRegisterData(String username, String password, String repassword) {
        return mHttpHelper.getRegisterData(username, password, repassword);
    }

    @Override
    public Observable<FeedArticleListResponse> addCollectArticle(int id) {
        return mHttpHelper.addCollectArticle(id);
    }

    @Override
    public Observable<FeedArticleListResponse> addCollectOutsideArticle(String title, String author, String link) {
        return mHttpHelper.addCollectOutsideArticle(title, author, link);
    }

    @Override
    public Observable<FeedArticleListResponse> getCollectList(int page) {
        return mHttpHelper.getCollectList(page);
    }

    @Override
    public Observable<FeedArticleListResponse> cancelCollectArticle(int id) {
        return mHttpHelper.cancelCollectArticle(id);
    }

    @Override
    public Observable<BannerResponse> getBannerData() {
        return mHttpHelper.getBannerData();
    }

    @Override
    public Observable<FeedArticleListResponse> cancelCollectPageArticle(int id) {
        return mHttpHelper.cancelCollectPageArticle(id);
    }

    @Override
    public void setLoginAccount(String account) {
        mPreferenceHelper.setLoginAccount(account);
    }

    @Override
    public void setLoginPassword(String password) {
        mPreferenceHelper.setLoginPassword(password);
    }

    @Override
    public String getLoginAccount() {
        return mPreferenceHelper.getLoginAccount();
    }

    @Override
    public String getLoginPassword() {
        return mPreferenceHelper.getLoginPassword();
    }

    @Override
    public void setLoginStatus(boolean isLogin) {
        mPreferenceHelper.setLoginStatus(isLogin);
    }

    @Override
    public boolean getLoginStatus() {
        return mPreferenceHelper.getLoginStatus();
    }

    @Override
    public void setCurrentPage(int position) {
        mPreferenceHelper.setCurrentPage(position);
    }

    @Override
    public int getCurrentPage() {
        return mPreferenceHelper.getCurrentPage();
    }

    @Override
    public void setProjectCurrentPage(int position) {
        mPreferenceHelper.setProjectCurrentPage(position);
    }

    @Override
    public int getProjectCurrentPage() {
        return mPreferenceHelper.getProjectCurrentPage();
    }


}
