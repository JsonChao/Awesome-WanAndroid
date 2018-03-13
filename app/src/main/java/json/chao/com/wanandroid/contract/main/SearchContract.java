package json.chao.com.wanandroid.contract.main;

import java.util.List;

import json.chao.com.wanandroid.base.presenter.AbstractPresenter;
import json.chao.com.wanandroid.core.bean.main.collect.FeedArticleData;
import json.chao.com.wanandroid.core.bean.main.collect.FeedArticleListResponse;
import json.chao.com.wanandroid.core.bean.main.search.TopSearchDataResponse;
import json.chao.com.wanandroid.core.bean.main.search.UsefulSitesResponse;
import json.chao.com.wanandroid.base.view.BaseView;
import json.chao.com.wanandroid.core.dao.HistoryData;

/**
 * @author quchao
 * @date 2018/2/12
 */

public interface SearchContract {

    interface View extends BaseView {

        /**
         * Show history data
         *
         * @param historyDataList List<HistoryData>
         */
        void showHistoryData(List<HistoryData> historyDataList);

        /**
         * Show top search data
         *
         * @param topSearchDataResponse TopSearchDataResponse
         */
        void showTopSearchData(TopSearchDataResponse topSearchDataResponse);

        /**
         * Show useful sites
         *
         * @param usefulSitesResponse UsefulSitesResponse
         */
        void showUsefulSites(UsefulSitesResponse usefulSitesResponse);

        /**
         * Show top search data fail
         */
        void showTopSearchDataFail();

        /**
         * Show useful sites data fail
         */
        void showUsefulSitesDataFail();

    }

    interface Presenter extends AbstractPresenter<View> {

        /**
         * Add history data
         *
         * @param data history data
         */
        void addHistoryData(String data);

        /**
         * 热搜
         */
        void getTopSearchData();

        /**
         * 常用网站
         */
        void getUsefulSites();

    }

}
