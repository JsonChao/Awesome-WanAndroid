package json.chao.com.wanandroid;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

/**
 * @author quchao
 * @date 2018/6/6
 */

@Implements(Person.class)
public class ShadowPerson {

    @Implementation
    public String getName() {
        return "JsonChao";
    }

    @Implementation
    public int getAge() {
        return 25;
    }

}
