package uncle.egg.newsapp.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import uncle.egg.newsapp.MyApplication;
import uncle.egg.newsapp.util.FindNews;

/**
 * Created by 西域战神阿凡提 on 2016/1/19.
 */
public class MyFragmentAdapter extends FragmentStatePagerAdapter {
    Fragment[] fragments = new Fragment[4];



    public MyFragmentAdapter(FragmentManager fm) {
        super(fm);
        init();
    }

    private void init() {
        fragments[0] = new FragmentNews(FindNews.FIND_NEWS_ANDROID);
        fragments[1] = new FragmentNews(FindNews.FIND_NEWS_IOS);
        fragments[2] = new FragmentNews(FindNews.FIND_NEWS_HTML);
        fragments[3] = new FragmentImage(FindNews.FIND_NEWS_GIRL);

    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return fragments.length;
    }


}
