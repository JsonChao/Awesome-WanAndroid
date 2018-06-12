package json.chao.com.wanandroid.rule;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * @author quchao
 * @date 2018/5/30
 */
public class MyRuler implements TestRule {

    @Override
    public Statement apply(Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                String methodName = description.getMethodName();
                System.out.println(methodName + "测试开始~");

                //执行单元测试操作
                base.evaluate();

                System.out.println(methodName + "测试结束");
            }
        };
    }
}
