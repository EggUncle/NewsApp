package uncle.egg.newsapp.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.xlf.nrl.NsRefreshLayout;

import java.util.List;

import uncle.egg.newsapp.R;
import uncle.egg.newsapp.module.ListHistoryAdapter;
import uncle.egg.newsapp.module.ListRecyclerAdapter;
import uncle.egg.newsapp.module.ListRecyclerTodayAdapter;
import uncle.egg.newsapp.module.News;
import uncle.egg.newsapp.util.FindNews;
import uncle.egg.newsapp.util.FindNewsByInternet;

public class HistoryActivity extends AppCompatActivity implements
        NsRefreshLayout.NsRefreshLayoutController, NsRefreshLayout.NsRefreshLayoutListener {

    private boolean loadMoreEnable = true;
    private NsRefreshLayout refreshLayout;
    private RecyclerView rvTest;
//    private ProgressBar progressBar;

    //新闻消息的类型
    private int type;
    //当前页数
    private int pageNum = 1;

    //List中的数据数量
    private int listDataNum = 10;

    //List一次数据添加的数量
    private int listDataAddNum = 10;


    private List<News> dateNews;

    private ListHistoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        setTitle("历史热点");
        init();
    }

    private void init() {
        dateNews = FindNewsByInternet.getHistoryNews(5);
        adapter = new ListHistoryAdapter(this, dateNews);
        refreshLayout = (NsRefreshLayout)findViewById(R.id.nrl_test);
        rvTest = (RecyclerView)findViewById(R.id.rv_test);

        rvTest.setAdapter(adapter);
        refreshLayout.setRefreshLayoutController(this);
        refreshLayout.setRefreshLayoutListener(this);
        setFragmentData();
    }


    //获取更多数据
    public void setFragmentData() {
        dateNews = FindNewsByInternet.getHistoryNews(5);
        adapter.notifyDataSetChanged();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        loadMoreEnable = false;
        return true;
    }

    @Override
    public boolean isPullRefreshEnable() {
        return true;
    }

    @Override
    public boolean isPullLoadEnable() {
        return loadMoreEnable;
    }

    @Override
    public void onRefresh() {
        refreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshLayout.finishPullRefresh();
                setFragmentData();
                //  Toast.makeText(getActivity(), "下拉刷新", Toast.LENGTH_LONG).show();
            }
        }, 1000);
    }

    @Override
    public void onLoadMore() {
        refreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshLayout.finishPullLoad();
                setFragmentData();
                //     Toast.makeText(getActivity(), "上拉加载更多", Toast.LENGTH_LONG).show();
            }
        }, 1000);
    }

}
