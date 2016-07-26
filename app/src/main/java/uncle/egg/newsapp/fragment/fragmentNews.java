package uncle.egg.newsapp.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.xlf.nrl.NsRefreshLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import uncle.egg.newsapp.DB.NewsDB;
import uncle.egg.newsapp.R;
import uncle.egg.newsapp.module.ListRecyclerAdapter;
import uncle.egg.newsapp.module.News;
import uncle.egg.newsapp.util.FindNews;

/**
 * Created by egguncle on 16.7.20.
 */
public class FragmentNews extends Fragment implements
        NsRefreshLayout.NsRefreshLayoutController, NsRefreshLayout.NsRefreshLayoutListener {

    private boolean loadMoreEnable = true;
    private NsRefreshLayout refreshLayout;
    private RecyclerView rvTest;
    private ProgressBar progressBar;

    //新闻消息的类型
    private int type;



    //List中的数据数量
    private int listDataNum = 10;

    //List一次数据添加的数量
    private int listDataAddNum = 10;

    private View view;


    private List<News> dateNews;

    private ListRecyclerAdapter adapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Thread(new Runnable() {
            @Override
            public void run() {
                switch (type) {
                    case FindNews.FIND_NEWS_ANDROID: {
                  //      FindNews.getNews(type, 1);
                        dateNews = NewsDB.getDBNews(FindNews.FIND_NEWS_ANDROID, listDataNum);
                    }
                    break;
                    case FindNews.FIND_NEWS_IOS: {
                    //    FindNews.getNews(type, 1);
                       dateNews = NewsDB.getDBNews(FindNews.FIND_NEWS_IOS, listDataNum);
                    }
                    break;
                    case FindNews.FIND_NEWS_HTML: {
                   //     FindNews.getNews(type, 1);
                       dateNews = NewsDB.getDBNews(FindNews.FIND_NEWS_HTML, listDataNum);
                    }
                    break;
                    case FindNews.FIND_NEWS_GIRL: {
                        // strType = "福利";
                    }
                    break;
                }
            }
        }).start();


        // setFragmentData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_news, null);
        refreshLayout = (NsRefreshLayout) view.findViewById(R.id.nrl_test);
     //   progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        refreshLayout.setRefreshLayoutController(this);
        refreshLayout.setRefreshLayoutListener(this);

        rvTest = (RecyclerView) view.findViewById(R.id.rv_test);
        adapter = new ListRecyclerAdapter(getActivity(), dateNews);
        rvTest.setAdapter(adapter);



//        MyAyncTask myAyncTask = new MyAyncTask();
//        myAyncTask.execute();

        Log.v("MY_TAG", "--------------------------------oncreateview--------------" + type);

        return view;
    }

    public FragmentNews() {
        //当没有传入新闻的类型时，默认问安卓类的新闻
        //   this.type = FindNews.FIND_NEWS_ANDROID;
    }

    public FragmentNews(int type) {
        this.type = type;
        
    }


    //获取更多数据
    public void setFragmentData() {
        switch (type) {
            case FindNews.FIND_NEWS_ANDROID: {
             //   FindNews.getNews(type, ++pageNum);
                dateNews.clear();
                //一次多获取十条
                listDataNum = listDataNum + listDataAddNum;
                dateNews.addAll(NewsDB.getDBNews(FindNews.FIND_NEWS_ANDROID, listDataNum));
            }
            break;
            case FindNews.FIND_NEWS_IOS: {
             //   FindNews.getNews(type, ++pageNum);
                dateNews.clear();
                //一次多获取十条
                listDataNum = listDataNum + listDataAddNum;
                dateNews.addAll(NewsDB.getDBNews(FindNews.FIND_NEWS_IOS, listDataNum));
            }
            break;
            case FindNews.FIND_NEWS_HTML: {
             //   FindNews.getNews(type, ++pageNum);
                dateNews.clear();
                //一次多获取十条
                listDataNum = listDataNum + listDataAddNum;
                dateNews.addAll(NewsDB.getDBNews(FindNews.FIND_NEWS_HTML, listDataNum));
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

    //使用异步加载数据
    //第一个参数：启动任务时输入的参数类型.
    //第二个参数：后台任务执行中返回进度值的类型.
    //第三个参数：后台任务执行完成后返回结果的类型.
    class MyAyncTask extends AsyncTask<Void, Integer, List<News>> {



        public MyAyncTask() {
            super();
        }


        @Override
        protected void onPostExecute(List<News> news) {
            //执行后返回值
            super.onPostExecute(news);
            progressBar.setVisibility(View.GONE);
            //  my_list.setAdapter(myListAdapter);
        }

        @Override
        protected void onPreExecute() {
            //执行前的初始化操作
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            //更新时调用的操作
            //   list_name_data= setListData();
            super.onProgressUpdate(values);
        }

        @Override
        protected List<News> doInBackground(Void... params) {
            //后台加载时的操作
            //   setListData();




            return null;
        }
    }
}
