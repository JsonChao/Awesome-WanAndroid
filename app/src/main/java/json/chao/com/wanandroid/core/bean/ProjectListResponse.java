package json.chao.com.wanandroid.core.bean;


/**
 * @author quchao
 * @date 2018/2/24
 */

public class ProjectListResponse extends BaseResponse {

    private ProjectListData data;

    public ProjectListData getData() {
        return data;
    }

    public void setData(ProjectListData data) {
        this.data = data;
    }
}
