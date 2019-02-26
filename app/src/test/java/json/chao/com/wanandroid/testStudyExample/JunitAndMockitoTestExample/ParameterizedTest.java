package json.chao.com.wanandroid.testStudyExample.JunitAndMockitoTestExample;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author quchao
 * @date 2018/6/5
 */
@RunWith(Parameterized.class)
public class ParameterizedTest {

    private int num;
    private boolean truth;

    public ParameterizedTest(int num, boolean truth) {
        this.num = num;
        this.truth = truth;
    }

    //被此注解注解的方法将把返回的列表数据中的元素对应注入到测试类
    //的构造函数ParameterizedTest(int num, boolean truth)中
    @Parameterized.Parameters
    public static Collection providerTruth() {
        return Arrays.asList(new Object[][]{
                {0, true},
                {1, false},
                {2, true},
                {3, false},
                {4, true},
                {5, false}
        });
    }

//    //也可不使用构造函数注入的方式，使用注解注入public变量的方式
//    @Parameterized.Parameter
//    public int num;
//    //value = 1指定括号里的第二个Boolean值
//    @Parameterized.Parameter(value = 1)
//    public boolean truth;

    @Test
    public void printTest() {
        Assert.assertEquals(truth, print(num));
        System.out.println(num);
    }

    private boolean print(int num) {
        return num % 2 == 0;
    }

}
