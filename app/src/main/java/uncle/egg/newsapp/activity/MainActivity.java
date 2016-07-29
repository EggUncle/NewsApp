package uncle.egg.newsapp.activity;


import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import java.util.ArrayList;
import java.util.List;

import uncle.egg.newsapp.DB.NewsDB;
import uncle.egg.newsapp.MyApplication;
import uncle.egg.newsapp.R;
import uncle.egg.newsapp.fragment.FragmentNews;
import uncle.egg.newsapp.fragment.MyFragmentAdapter;
import uncle.egg.newsapp.module.ChangeColorIconWithText;


public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        ViewPager.OnPageChangeListener {

    private MyFragmentAdapter fragmentAdapter;
    private ViewPager viewPager;
    private List<ChangeColorIconWithText> tabIndicators = new ArrayList<ChangeColorIconWithText>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initGetNews();
    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.id_viewpager);

        ChangeColorIconWithText one = (ChangeColorIconWithText) findViewById(R.id.id_indicator_one);
        tabIndicators.add(one);
        ChangeColorIconWithText two = (ChangeColorIconWithText) findViewById(R.id.id_indicator_two);
        tabIndicators.add(two);
        ChangeColorIconWithText three = (ChangeColorIconWithText) findViewById(R.id.id_indicator_three);
        tabIndicators.add(three);
//        ChangeColorIconWithText four = (ChangeColorIconWithText) findViewById(R.id.id_indicator_four);
//        tabIndicators.add(four);

        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        //  four.setOnClickListener(this);

        one.setIconAlpha(1.0f);

        FragmentManager fm = getSupportFragmentManager();
        fragmentAdapter = new MyFragmentAdapter(fm, this);
        viewPager.setAdapter(fragmentAdapter);
        viewPager.addOnPageChangeListener(MainActivity.this);

    }

    private void initGetNews() {

//        MyApplication.getDataAndroidNews();
//        MyApplication.getDataIOSNews();
//        MyApplication.getDataHtmlNews();
    }


    @Override
    public void onClick(View v) {
        clickTab(v);

    }

    /**
     * 点击Tab按钮
     *
     * @param v
     */
    private void clickTab(View v) {
        resetOtherTabs();

        switch (v.getId()) {
            case R.id.id_indicator_one:
                tabIndicators.get(0).setIconAlpha(1.0f);
                viewPager.setCurrentItem(0, false);
                //    setTitle(titleStrs[0]);
                break;
            case R.id.id_indicator_two:
                tabIndicators.get(1).setIconAlpha(1.0f);
                viewPager.setCurrentItem(1, false);
                //   setTitle(titleStrs[1]);
                break;
            case R.id.id_indicator_three:
                tabIndicators.get(2).setIconAlpha(1.0f);
                viewPager.setCurrentItem(2, false);
                //  setTitle(titleStrs[2]);
                break;
//            case R.id.id_indicator_four:
//                tabIndicators.get(3).setIconAlpha(1.0f);
//                viewPager.setCurrentItem(3, false);
//                //   setTitle(titleStrs[3]);
//                break;
        }
    }

    /**
     * 重置其他的TabIndicator的颜色
     */
    private void resetOtherTabs() {
        for (int i = 0; i < tabIndicators.size(); i++) {
            tabIndicators.get(i).setIconAlpha(0);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset,
                               int positionOffsetPixels) {
        // Log.e("TAG", "position = " + position + " ,positionOffset =  "
        // + positionOffset);
        if (positionOffset > 0) {
            ChangeColorIconWithText left = tabIndicators.get(position);
            ChangeColorIconWithText right = tabIndicators.get(position + 1);
            left.setIconAlpha(1 - positionOffset);
            right.setIconAlpha(positionOffset);
        }

    }

    private String[] titles = new String[]{"今日热点", "Android", "iOS"};

    @Override
    public void onPageSelected(int position) {
        // TODO Auto-generated method stub
        //  Log.v("MT",position+"");
        setTitle(titles[position]);
        if (position == 1 || position == 2) {
            fragmentAdapter.setFragmentsData((FragmentNews) fragmentAdapter.getItem(position));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(MainActivity.this,HistoryActivity.class);
        startActivity(intent);
        return true;
    }


    @Override
    public void onPageScrollStateChanged(int state) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem item = menu.add(0, 0, 0, "历史");
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return true;
    }

}
