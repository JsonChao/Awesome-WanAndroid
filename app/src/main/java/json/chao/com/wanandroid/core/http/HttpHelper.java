package json.chao.com.wanandroid.core.http;


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


/**
 * @author quchao
 * @date 2017/11/27
 */

public interface HttpHelper {

    /**
     * 获取feed文章列表
     *
     * @param pageNum 页数
     * @return feed文章列表被观察者
     */
    Observable<FeedArticleListResponse> getFeedArticleList(int pageNum);

    /**
     * 获取搜索的文章列表
     *
     * @param pageNum 页数
     * @param k 关键字
     * @return 搜索的文章列表
     */
    Observable<FeedArticleListResponse> getSearchList(int pageNum, String k);

    /**
     * 热搜
     * http://www.wanandroid.com//hotkey/json
     *
     * @return Observable<TopSearchDataResponse>
     */
    Observable<TopSearchDataResponse> getTopSearchData();

    /**
     * 常用网站
     * http://www.wanandroid.com/friend/json
     *
     * @return Observable<UsefulSitesResponse>
     */
    Observable<UsefulSitesResponse> getUsefulSites();

    /**
     * 知识体系
     * http://www.wanandroid.com/tree/json
     *
     * @return Observable<KnowledgeHierarchyResponse>
     */
    Observable<KnowledgeHierarchyResponse> getKnowledgeHierarchyData();

    /**
     * 知识体系下的文章
     * http://www.wanandroid.com/article/list/0?cid=60
     *
     * @param page page num
     * @param cid second page id
     * @return Observable<FeedArticleListResponse>
     */
    Observable<FeedArticleListResponse> getKnowledgeHierarchyDetailData(int page, int cid);

    /**
     * 导航
     * http://www.wanandroid.com/navi/json
     *
     * @return Observable<NavigationListResponse>
     */
    Observable<NavigationListResponse> getNavigationListData();

    /**
     * 项目分类
     * http://www.wanandroid.com/project/tree/json
     *
     * @return Observable<ProjectClassifyResponse>
     */
    Observable<ProjectClassifyResponse> getProjectClassifyData();

    /**
     * 项目类别数据
     * http://www.wanandroid.com/project/list/1/json?cid=294
     *
     * @param page page num
     * @param cid second page id
     * @return Observable<ProjectListResponse>
     */
    Observable<ProjectListResponse> getProjectListData(int page, int cid);

    /**
     * 登陆
     * http://www.wanandroid.com/user/login
     *
     * @param username user name
     * @param password password
     * @return Observable<LoginResponse>
     */
    Observable<LoginResponse> getLoginData(String username, String password);

    /**
     * 注册
     * http://www.wanandroid.com/user/register
     *
     * @param username user name
     * @param password password
     * @param rePassword re password
     * @return Observable<LoginResponse>
     */
    Observable<LoginResponse> getRegisterData(String username, String password, String rePassword);

    /**
     * 收藏站内文章
     * http://www.wanandroid.com/lg/collect/1165/json
     *
     * @param id article id
     * @return Observable<FeedArticleListResponse>
     */
    Observable<FeedArticleListResponse> addCollectArticle(int id);

    /**
     * 收藏站外文章
     * http://www.wanandroid.com/lg/collect/add/json
     *
     * @param title title
     * @param author author
     * @param link link
     * @return Observable<FeedArticleListResponse>
     */
    Observable<FeedArticleListResponse> addCollectOutsideArticle(String  title, String author, String link);

    /**
     * 获取收藏列表
     * http://www.wanandroid.com/lg/collect/list/0/json
     *
     * @param page page number
     * @return Observable<FeedArticleListResponse>
     */
    Observable<FeedArticleListResponse> getCollectList(int page);

    /**
     * 取消收藏页面站内文章
     * http://www.wanandroid.com/lg/uncollect_originId/2333/json
     *
     * @param id article id
     * @return Observable<FeedArticleListResponse>
     */
    Observable<FeedArticleListResponse> cancelCollectPageArticle(int id);

    /**
     * 取消站内文章
     * http://www.wanandroid.com/lg/uncollect_originId/2333/json
     *
     * @param id article id
     * @return Observable<FeedArticleListResponse>
     */
    Observable<FeedArticleListResponse> cancelCollectArticle(int id);

    /**
     * 广告栏
     * http://www.wanandroid.com/banner/json
     *
     * @return Observable<BannerResponse>
     */
    Observable<BannerResponse> getBannerData();


}
