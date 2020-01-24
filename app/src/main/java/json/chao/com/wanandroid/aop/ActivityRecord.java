package json.chao.com.wanandroid.aop;

public class ActivityRecord {

    /**
     * 避免没有仅执行onResume就去统计界面打开速度的情况，如息屏、亮屏等等
     */
    public boolean isNewCreate;

    public long mOnCreateTime;
    public long mOnWindowsFocusChangedTime;

}
