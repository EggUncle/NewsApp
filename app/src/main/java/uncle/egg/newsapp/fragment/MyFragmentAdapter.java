package uncle.egg.newsapp.fragment;

import android.content.Context;
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
    private Fragment[] fragments = new Fragment[3];

    private Context context;

    public MyFragmentAdapter(FragmentManager fm, Context context) {
        super(fm);
        init(context);
    }

    private void init(Context context) {
        fragments[0] = new FragmentToday();
        fragments[1] = new FragmentNews(FindNews.FIND_NEWS_ANDROID, context);
        //  fragments[2] = new FragmentNews(FindNews.FIND_NEWS_HTML);
        fragments[2] = new FragmentNews(FindNews.FIND_NEWS_IOS, context);

    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return fragments.length;
    }

    public void setFragmentsData(FragmentNews fragmentNews){
        fragmentNews.setFragmentData();
    }

}
