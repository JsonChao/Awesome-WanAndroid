package json.chao.com.wanandroid.core.db;

import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import json.chao.com.wanandroid.app.WanAndroidApp;
import json.chao.com.wanandroid.core.dao.DaoSession;
import json.chao.com.wanandroid.core.dao.HistoryData;
import json.chao.com.wanandroid.core.dao.HistoryDataDao;

/**
 * 对外隐藏操作数据库的实现细节
 *
 * @author quchao
 * @date 2017/11/27
 */

public class DbHelperImpl implements DbHelper {

    private DaoSession daoSession;

    @Inject
    DbHelperImpl() {
        daoSession = WanAndroidApp.getInstance().getDaoSession();
    }

    @Override
    public List<HistoryData> addHistoryData(String data) {
        HistoryDataDao historyDataDao = daoSession.getHistoryDataDao();
        List<HistoryData> historyDataList = historyDataDao.loadAll();
        HistoryData historyData = new HistoryData();
        historyData.setDate(System.currentTimeMillis());
        historyData.setData(data);
        //重复搜索时进行历史记录前移
        Iterator<HistoryData> iterator = historyDataList.iterator();
        //不要在foreach循环中进行元素的remove、add操作，使用Iterator模式
        while (iterator.hasNext()) {
            HistoryData historyData1 = iterator.next();
            if (historyData1.getData().equals(data)) {
                historyDataList.remove(historyData1);
                historyDataList.add(historyData);
                historyDataDao.deleteAll();
                historyDataDao.insertInTx(historyDataList);
                return historyDataList;
            }
        }
        if (historyDataList.size() < 10) {
            historyDataDao.insert(historyData);
        } else {
            historyDataList.remove(0);
            historyDataList.add(historyData);
            historyDataDao.deleteAll();
            historyDataDao.insertInTx(historyDataList);
        }
        return historyDataList;
    }

    @Override
    public void clearHistoryData() {
        daoSession.getHistoryDataDao().deleteAll();
    }

    @Override
    public List<HistoryData> loadAllHistoryData() {
       return daoSession.getHistoryDataDao().loadAll();
    }


}
