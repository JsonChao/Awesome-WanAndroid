package json.chao.com.wanandroid.core.bean;

/**
 * @author quchao
 * @date 2018/2/12
 */

public class FeedArticleListResponse extends BaseResponse {

    private FeedArticleListData data;

    public FeedArticleListData getData() {
        return data;
    }

    public void setData(FeedArticleListData data) {
        this.data = data;
    }
}
