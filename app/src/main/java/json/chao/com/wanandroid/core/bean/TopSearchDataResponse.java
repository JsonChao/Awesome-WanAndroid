package json.chao.com.wanandroid.core.bean;

import java.util.List;

/**
 * @author quchao
 * @date 2018/2/22
 */

public class TopSearchDataResponse extends BaseResponse {

    private List<TopSearchData> data;

    public List<TopSearchData> getData() {
        return data;
    }

    public void setData(List<TopSearchData> data) {
        this.data = data;
    }
}
