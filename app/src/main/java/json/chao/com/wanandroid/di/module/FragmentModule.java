package json.chao.com.wanandroid.di.module;

import android.app.Activity;
import android.app.DialogFragment;
import android.support.v4.app.Fragment;


import dagger.Module;
import dagger.Provides;
import json.chao.com.wanandroid.di.scope.FragmentScope;

/**
 * @author quchao
 * @date 2017/11/27
 */

@Module
public class FragmentModule {

    private Fragment fragment;
    private DialogFragment dialogFragment;

    public FragmentModule(Fragment fragment) {
        this.dialogFragment = null;
        this.fragment = fragment;
    }

    public FragmentModule(DialogFragment fragment) {
        this.fragment = null;
        this.dialogFragment = fragment;
    }

    @Provides
    @FragmentScope
    Activity provideActivity() {
        if (fragment == null) {
            return dialogFragment.getActivity();
        } else {
            return fragment.getActivity();
        }
    }

}
