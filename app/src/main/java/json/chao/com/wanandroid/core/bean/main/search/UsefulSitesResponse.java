package json.chao.com.wanandroid.core.bean.main.search;

import java.util.List;

import json.chao.com.wanandroid.core.bean.BaseResponse;

/**
 * @author quchao
 * @date 2018/2/22
 */

public class UsefulSitesResponse extends BaseResponse {

    private List<UsefulSiteData> data;

    public List<UsefulSiteData> getData() {
        return data;
    }

    public void setData(List<UsefulSiteData> data) {
        this.data = data;
    }
}
