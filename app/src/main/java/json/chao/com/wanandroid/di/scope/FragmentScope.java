package json.chao.com.wanandroid.di.scope;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author quchao
 * @date 2017/11/27
 */

@Scope
@Retention(RUNTIME)
public @interface FragmentScope {
}
