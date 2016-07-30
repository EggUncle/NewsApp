package uncle.egg.newsapp.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.xlf.nrl.NsRefreshLayout;

import java.util.List;

import uncle.egg.newsapp.R;
import uncle.egg.newsapp.module.ListRecyclerAdapter;
import uncle.egg.newsapp.module.News;
import uncle.egg.newsapp.util.FindNews;
import uncle.egg.newsapp.util.FindNewsByInternet;

/**
 * Created by egguncle on 16.7.20.
 */
public class FragmentNews extends Fragment implements
        NsRefreshLayout.NsRefreshLayoutController, NsRefreshLayout.NsRefreshLayoutListener {

    private Context context;
    private boolean loadMoreEnable = true;
    private NsRefreshLayout refreshLayout;
    private RecyclerView rvTest;
    private ProgressBar newsProgressBar;
//    private ProgressBar progressBar;

    //新闻消息的类型
    private int type;
//当前页数
    private int pageNum=1;

    //List中的数据数量
    private int listDataNum = 10;

    //List一次数据添加的数量
    private int listDataAddNum = 10;

    private View view;


    private List<News> dateNews;

    private ListRecyclerAdapter adapter;

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            if (msg.what == 0x123) {
                setFragmentData();
                newsProgressBar.setVisibility(View.GONE);
                refreshLayout.setVisibility(View.VISIBLE);

            }

            super.handleMessage(msg);
        }


    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // dateNews= findNewsByInternet.getData(type,1);

        // setFragmentData();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_news, null);
        newsProgressBar = (ProgressBar) view.findViewById(R.id.news_progress);
        refreshLayout = (NsRefreshLayout) view.findViewById(R.id.nrl_test);
        //   progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        refreshLayout.setRefreshLayoutController(this);
        refreshLayout.setRefreshLayoutListener(this);

        rvTest = (RecyclerView) view.findViewById(R.id.rv_test);
        rvTest.setAdapter(adapter);
        //      setFragmentData();

        newsProgressBar.setVisibility(View.GONE);
        refreshLayout.setVisibility(View.VISIBLE);
        Log.v("MY_TAG_View", "--------------------------------oncreateview--------------" + type);

//        MyAyncTask myAyncTask = new MyAyncTask();
//        myAyncTask.execute();
//        Message msg = new Message();
//        msg.what = 0x123;
//        handler.sendMessage(msg);
//        Log.v("handler", msg.what + "");

        return view;
    }

    public FragmentNews() {
        //当没有传入新闻的类型时，默认问安卓类的新闻
        //   this.type = FindNews.FIND_NEWS_ANDROID;
    }

    public FragmentNews(int type,Context context) {
        this.type = type;
        this.context = context;
        switch (type) {
            case FindNews.FIND_NEWS_ANDROID: {
                dateNews = FindNewsByInternet.getAndroidData(pageNum++);
//               dateNews= MyApplication.getDataAndroidNews();
//                pageNum++;
            }
            break;
            case FindNews.FIND_NEWS_IOS: {
//                dateNews= MyApplication.getDataIOSNews();
//                pageNum++;
                dateNews = FindNewsByInternet.getiOSData(pageNum++);
            }
            break;
        }
        adapter = new ListRecyclerAdapter(context, dateNews);
    }

    //获取更多数据
    public  void setFragmentData() {
        switch (type) {
            case FindNews.FIND_NEWS_ANDROID: {
                dateNews = FindNewsByInternet.getAndroidData(pageNum++);
            }
            break;
            case FindNews.FIND_NEWS_IOS: {
                dateNews = FindNewsByInternet.getiOSData(pageNum++);
            }
            break;
            case FindNews.FIND_NEWS_HTML: {

            }
            break;
            case FindNews.FIND_NEWS_GIRL: {
                // strType = "福利";
            }
            break;
        }
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
