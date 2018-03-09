package json.chao.com.wanandroid.core.bean;

import java.util.List;

/**
 * @author quchao
 * @date 2018/2/24
 */

public class ProjectClassifyResponse extends BaseResponse {

    private List<ProjectClassifyData> data;

    public List<ProjectClassifyData> getData() {
        return data;
    }

    public void setData(List<ProjectClassifyData> data) {
        this.data = data;
    }
}
