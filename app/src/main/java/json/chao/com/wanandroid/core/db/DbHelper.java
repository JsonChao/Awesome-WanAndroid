package json.chao.com.wanandroid.core.db;

import java.util.List;

import json.chao.com.wanandroid.core.dao.HistoryData;

/**
 * @author quchao
 * @date 2017/11/27
 */

public interface DbHelper {

    /**
     * 增加历史数据
     *
     * @param data  added string
     * @return  List<HistoryData>
     */
    List<HistoryData> addHistoryData(String data);

    /**
     * Clear search history data
     */
    void clearHistoryData();

    /**
     * Load all history data
     *
     * @return List<HistoryData>
     */
    List<HistoryData> loadAllHistoryData();

}
