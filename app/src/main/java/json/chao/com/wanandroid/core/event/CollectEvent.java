package json.chao.com.wanandroid.core.event;

/**
 * @author quchao
 * @date 2018/3/16
 */

public class CollectEvent {

    private boolean isCancelCollectSuccess;

    public CollectEvent(boolean isCancelCollectSuccess) {
        this.isCancelCollectSuccess = isCancelCollectSuccess;
    }

    public boolean isCancelCollectSuccess() {
        return isCancelCollectSuccess;
    }

    public void setCancelCollectSuccess(boolean cancelCollectSuccess) {
        isCancelCollectSuccess = cancelCollectSuccess;
    }
}
