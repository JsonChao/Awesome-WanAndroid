package json.chao.com.wanandroid.ui.adapter;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import json.chao.com.wanandroid.base.BaseFragment;
import json.chao.com.wanandroid.core.bean.NavigationListData;
import q.rorbin.verticaltablayout.adapter.TabAdapter;
import q.rorbin.verticaltablayout.widget.ITabView;
import q.rorbin.verticaltablayout.widget.TabView;

/**
 * @author quchao
 * @date 2018/3/2
 */

public class NavigationAdapter extends FragmentPagerAdapter implements TabAdapter {

    private List<NavigationListData> mData;
    private ArrayList<BaseFragment> mFragments;

    public NavigationAdapter(FragmentManager fm, List<NavigationListData> data, ArrayList<BaseFragment> fragments) {
        super(fm);
        mData = data;
        mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mData == null? 0 : mData.size();
    }

    @Override
    public ITabView.TabBadge getBadge(int i) {
        return null;
    }

    @Override
    public ITabView.TabIcon getIcon(int i) {
        return null;
    }

    @Override
    public ITabView.TabTitle getTitle(int i) {
        return new TabView.TabTitle.Builder()
                .setContent(mData.get(i).getName())
                .setTextColor(Color.WHITE, 0xBBFFFFFF)
                .build();
    }

    @Override
    public int getBackground(int i) {
        return 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mData.get(position).getName();
    }
}
