package json.chao.com.wanandroid.core.bean.hierarchy;

import java.util.List;

import json.chao.com.wanandroid.core.bean.BaseResponse;

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
