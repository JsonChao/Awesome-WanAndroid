package json.chao.com.wanandroid.di.component;

import dagger.Subcomponent;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;
import json.chao.com.wanandroid.base.fragment.BaseDialogFragment;

/**
 * @author quchao
 * @date 2018/5/3
 */

@Subcomponent(modules = {AndroidInjectionModule.class})
public interface BaseDialogFragmentComponent extends AndroidInjector<BaseDialogFragment> {

    /**
     * 每一个继承于BaseDialogFragment的Fragment都继承于同一个子组件
     */
    @Subcomponent.Builder
    abstract class BaseBuilder extends AndroidInjector.Builder<BaseDialogFragment>{

    }
}
