package json.chao.com.wanandroid.core.event;

/**
 * @author quchao
 * @date 2018/4/3
 */

public class NightModeEvent {

    private boolean isNightMode;

    public NightModeEvent(boolean isNightMode) {
        this.isNightMode = isNightMode;
    }

    public boolean isNightMode() {
        return isNightMode;
    }

    public void setNightMode(boolean nightMode) {
        isNightMode = nightMode;
    }
}
