package json.chao.com.wanandroid.core.bean.main.banner;

import java.util.List;

import json.chao.com.wanandroid.core.bean.BaseResponse;

/**
 * @author quchao
 * @date 2018/3/2
 */

public class BannerResponse extends BaseResponse {

    private List<BannerData> data;

    public List<BannerData> getData() {
        return data;
    }

    public void setData(List<BannerData> data) {
        this.data = data;
    }
}
