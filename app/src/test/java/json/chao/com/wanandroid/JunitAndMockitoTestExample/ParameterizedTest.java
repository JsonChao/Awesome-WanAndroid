package json.chao.com.wanandroid.JunitAndMockitoTestExample;

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

    @Test
    public void printTest() {
        Assert.assertEquals(truth, print(num));
        System.out.println(num);
    }

    private boolean print(int num) {
        return num % 2 == 0;
    }

}
