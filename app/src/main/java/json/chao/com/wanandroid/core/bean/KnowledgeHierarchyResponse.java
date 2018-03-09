package json.chao.com.wanandroid.core.bean;

import java.util.List;

/**
 * @author quchao
 * @date 2018/2/23
 */

public class KnowledgeHierarchyResponse extends BaseResponse {

    private List<KnowledgeHierarchyData> data;

    public List<KnowledgeHierarchyData> getData() {
        return data;
    }

    public void setData(List<KnowledgeHierarchyData> data) {
        this.data = data;
    }
}
