package json.chao.com.wanandroid.core.bean.main.search;

import json.chao.com.wanandroid.core.bean.BaseResponse;

/**
 * @author quchao
 * @date 2018/2/22
 */

public class SearchListResponse extends BaseResponse {

    private ArticleData data;

    public ArticleData getData() {
        return data;
    }

    public void setData(ArticleData data) {
        this.data = data;
    }
}
