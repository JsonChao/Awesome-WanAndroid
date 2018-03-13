package json.chao.com.wanandroid.core.http;

import javax.inject.Inject;

import io.reactivex.Observable;
import json.chao.com.wanandroid.core.bean.main.banner.BannerResponse;
import json.chao.com.wanandroid.core.bean.main.collect.FeedArticleListResponse;
import json.chao.com.wanandroid.core.bean.hierarchy.KnowledgeHierarchyResponse;
import json.chao.com.wanandroid.core.bean.main.login.LoginResponse;
import json.chao.com.wanandroid.core.bean.navigation.NavigationListResponse;
import json.chao.com.wanandroid.core.bean.project.ProjectClassifyResponse;
import json.chao.com.wanandroid.core.bean.project.ProjectListResponse;
import json.chao.com.wanandroid.core.bean.main.search.TopSearchDataResponse;
import json.chao.com.wanandroid.core.bean.main.search.UsefulSitesResponse;
import json.chao.com.wanandroid.core.http.api.GeeksApis;


/**
 * @author quchao
 * @date 2017/11/27
 */

public class RetrofitHelper implements HttpHelper {

    private GeeksApis mGeeksApis;

    @Inject
    public RetrofitHelper(GeeksApis geeksApis) {
        mGeeksApis = geeksApis;
    }

    @Override
    public Observable<FeedArticleListResponse> getFeedArticleList(int pageNum) {
        return mGeeksApis.getFeedArticleList(pageNum);
    }

    @Override
    public Observable<FeedArticleListResponse> getSearchList(int pageNum, String k) {
        return mGeeksApis.getSearchList(pageNum, k);
    }

    @Override
    public Observable<TopSearchDataResponse> getTopSearchData() {
        return mGeeksApis.getTopSearchData();
    }

    @Override
    public Observable<UsefulSitesResponse> getUsefulSites() {
        return mGeeksApis.getUsefulSites();
    }

    @Override
    public Observable<KnowledgeHierarchyResponse> getKnowledgeHierarchyData() {
        return mGeeksApis.getKnowledgeHierarchyData();
    }

    @Override
    public Observable<FeedArticleListResponse> getKnowledgeHierarchyDetailData(int page, int cid) {
        return mGeeksApis.getKnowledgeHierarchyDetailData(page, cid);
    }

    @Override
    public Observable<NavigationListResponse> getNavigationListData() {
        return mGeeksApis.getNavigationListData();
    }

    @Override
    public Observable<ProjectClassifyResponse> getProjectClassifyData() {
        return mGeeksApis.getProjectClassifyData();
    }

    @Override
    public Observable<ProjectListResponse> getProjectListData(int page, int cid) {
        return mGeeksApis.getProjectListData(page, cid);
    }

    @Override
    public Observable<LoginResponse> getLoginData(String username, String password) {
        return mGeeksApis.getLoginData(username, password);
    }

    @Override
    public Observable<LoginResponse> getRegisterData(String username, String password, String repassword) {
        return mGeeksApis.getRegisterData(username, password, repassword);
    }

    @Override
    public Observable<FeedArticleListResponse> addCollectArticle(int id) {
        return mGeeksApis.addCollectArticle(id);
    }

    @Override
    public Observable<FeedArticleListResponse> addCollectOutsideArticle(String title, String author, String link) {
        return mGeeksApis.addCollectOutsideArticle(title, author, link);
    }

    @Override
    public Observable<FeedArticleListResponse> getCollectList(int page) {
        return mGeeksApis.getCollectList(page);
    }

    @Override
    public Observable<FeedArticleListResponse> cancelCollectPageArticle(int id) {
        return mGeeksApis.cancelCollectPageArticle(id, -1);
    }

    @Override
    public Observable<FeedArticleListResponse> cancelCollectArticle(int id) {
        return mGeeksApis.cancelCollectArticle(id, -1);
    }

    @Override
    public Observable<BannerResponse> getBannerData() {
        return mGeeksApis.getBannerData();
    }


}
