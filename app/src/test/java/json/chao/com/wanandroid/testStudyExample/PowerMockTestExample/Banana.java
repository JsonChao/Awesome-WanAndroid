package json.chao.com.wanandroid.testStudyExample.PowerMockTestExample;

/**
 * @author quchao
 * @date 2018/6/5
 */

public class Banana extends Fruit{

    private static String COLOR = "黄色的";

    public Banana() {}

    public static String getColor() {
        return COLOR;
    }

    public String getBananaInfo() {
        return flavor() + getColor();
    }

    private String flavor() {
        return "甜甜的";
    }

    public final boolean isLike() {
        return true;
    }
}
