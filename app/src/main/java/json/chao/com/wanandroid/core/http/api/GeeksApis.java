package json.chao.com.wanandroid.core.http.api;


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
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @author quchao
 * @date 2018/2/12
 */

public interface GeeksApis {

    String HOST = "http://www.wanandroid.com/";

    /**
     * 获取feed文章列表
     *
     * @param num 页数
     * @return Observable<FeedArticleListResponse>
     */
    @GET("article/list/{num}/json")
    Observable<FeedArticleListResponse> getFeedArticleList(@Path("num") int num);

    /**
     * 搜索
     * http://www.wanandroid.com/article/query/0/json
     * @param page page
     * @param k POST search key
     * @return Observable<FeedArticleListResponse>
     */
    @POST("article/query/{page}/json")
    @FormUrlEncoded
    Observable<FeedArticleListResponse> getSearchList(@Path("page") int page, @Field("k") String k);

    /**
     * 热搜
     * http://www.wanandroid.com//hotkey/json
     *
     * @return Observable<TopSearchDataResponse>
     */
    @GET("hotkey/json")
    @Headers("Cache-Control: public, max-age=36000")
    Observable<TopSearchDataResponse> getTopSearchData();

    /**
     * 常用网站
     * http://www.wanandroid.com/friend/json
     *
     * @return Observable<UsefulSitesResponse>
     */
    @GET("friend/json")
    Observable<UsefulSitesResponse> getUsefulSites();

    /**
     * 广告栏
     * http://www.wanandroid.com/banner/json
     *
     * @return Observable<BannerResponse>
     */
    @GET("banner/json")
    Observable<BannerResponse> getBannerData();

    /**
     * 知识体系
     * http://www.wanandroid.com/tree/json
     *
     * @return Observable<KnowledgeHierarchyResponse>
     */
    @GET("tree/json")
    Observable<KnowledgeHierarchyResponse> getKnowledgeHierarchyData();

    /**
     * 知识体系下的文章
     * http://www.wanandroid.com/article/list/0?cid=60
     *
     * @param page page num
     * @param cid second page id
     * @return Observable<FeedArticleListResponse>
     */
    @GET("article/list/{page}/json")
    Observable<FeedArticleListResponse> getKnowledgeHierarchyDetailData(@Path("page") int page, @Query("cid") int cid);

    /**
     * 导航
     * http://www.wanandroid.com/navi/json
     *
     * @return Observable<NavigationListResponse>
     */
    @GET("navi/json")
    Observable<NavigationListResponse> getNavigationListData();

    /**
     * 项目分类
     * http://www.wanandroid.com/project/tree/json
     *
     * @return Observable<ProjectClassifyResponse>
     */
    @GET("project/tree/json")
    Observable<ProjectClassifyResponse> getProjectClassifyData();

    /**
     * 项目类别数据
     * http://www.wanandroid.com/project/list/1/json?cid=294
     *
     * @param page page num
     * @param cid second page id
     * @return Observable<ProjectListResponse>
     */
    @GET("project/list/{page}/json")
    Observable<ProjectListResponse> getProjectListData(@Path("page") int page, @Query("cid") int cid);

    /**
     * 登陆
     * http://www.wanandroid.com/user/login
     *
     * @param username user name
     * @param password password
     * @return Observable<LoginResponse>
     */
    @POST("user/login")
    @FormUrlEncoded
    Observable<LoginResponse> getLoginData(@Field("username") String username, @Field("password") String password);

    /**
     * 注册
     * http://www.wanandroid.com/user/register
     *
     * @param username user name
     * @param password password
     * @param repassword re password
     * @return Observable<LoginResponse>
     */
    @POST("user/register")
    @FormUrlEncoded
    Observable<LoginResponse> getRegisterData(@Field("username") String username, @Field("password") String password, @Field("repassword") String repassword);

    /**
     * 收藏站内文章
     * http://www.wanandroid.com/lg/collect/1165/json
     *
     * @param id article id
     * @return Observable<FeedArticleListResponse>
     */
    @POST("lg/collect/{id}/json")
    Observable<FeedArticleListResponse> addCollectArticle(@Path("id") int id);

    /**
     * 收藏站外文章
     * http://www.wanandroid.com/lg/collect/add/json
     *
     * @param title title
     * @param author author
     * @param link link
     * @return Observable<FeedArticleListResponse>
     */
    @POST("lg/collect/add/json")
    @FormUrlEncoded
    Observable<FeedArticleListResponse> addCollectOutsideArticle(@Field("title") String  title, @Field("author") String author, @Field("link") String link);


    /**
     * 获取收藏列表
     * http://www.wanandroid.com/lg/collect/list/0/json
     *
     * @param page page number
     * @return Observable<FeedArticleListResponse>
     */
    @GET("lg/collect/list/{page}/json")
    Observable<FeedArticleListResponse> getCollectList(@Path("page") int page);

    /**
     * 取消站内文章
     * http://www.wanandroid.com/lg/uncollect_originId/2333/json
     *
     * @param id article id
     * @param originId origin id
     * @return Observable<FeedArticleListResponse>
     */
    @POST("lg/uncollect/{id}/json")
    @FormUrlEncoded
    Observable<FeedArticleListResponse> cancelCollectPageArticle(@Path("id") int id, @Field("originId") int originId);

    /**
     * 取消收藏页面站内文章
     * http://www.wanandroid.com/lg/uncollect_originId/2333/json
     *
     * @param id article id
     * @param originId origin id
     * @return Observable<FeedArticleListResponse>
     */
    @POST("lg/uncollect_originId/{id}/json")
    @FormUrlEncoded
    Observable<FeedArticleListResponse> cancelCollectArticle(@Path("id") int id, @Field("originId") int originId);

}
