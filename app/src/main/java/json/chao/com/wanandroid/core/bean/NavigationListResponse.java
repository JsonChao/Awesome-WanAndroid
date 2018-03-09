package json.chao.com.wanandroid.core.bean;

import java.util.List;

/**
 * @author quchao
 * @date 2018/2/24
 */

public class NavigationListResponse extends BaseResponse {

    private List<NavigationListData> data;

    public List<NavigationListData> getData() {
        return data;
    }

    public void setData(List<NavigationListData> data) {
        this.data = data;
    }
}
