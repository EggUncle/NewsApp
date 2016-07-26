package uncle.egg.newsapp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.xlf.nrl.NsRefreshLayout;

import java.util.List;

import uncle.egg.newsapp.DB.NewsDB;
import uncle.egg.newsapp.R;
import uncle.egg.newsapp.module.ImageRecyclerAdapter;
import uncle.egg.newsapp.module.ListRecyclerAdapter;
import uncle.egg.newsapp.module.News;
import uncle.egg.newsapp.util.FindNews;
import uncle.egg.newsapp.util.SpacesItemDecoration;

/**
 * Created by egguncle on 16.7.20.
 */
public class FragmentImage extends Fragment implements
        NsRefreshLayout.NsRefreshLayoutController, NsRefreshLayout.NsRefreshLayoutListener {

    private boolean loadMoreEnable = true;

    private NsRefreshLayout refreshLayout;
    private RecyclerView rvTest;

    //新闻消息的类型
    private int type;

    //当前的页面,默认第一页
    private int pageNum = 1;

    //List中的数据数量
    private int listDataNum = 10;

    private View view;


    private  List<News> dataNews;

    private ImageRecyclerAdapter adapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // setFragmentData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_news, null);
        refreshLayout = (NsRefreshLayout) view.findViewById(R.id.nrl_test);
        refreshLayout.setRefreshLayoutController(this);
        refreshLayout.setRefreshLayoutListener(this);

        rvTest = (RecyclerView) view.findViewById(R.id.rv_test);
        adapter = new ImageRecyclerAdapter(getActivity(), dataNews);

        //瀑布流
        rvTest.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        SpacesItemDecoration decoration=new SpacesItemDecoration(16);
        rvTest.addItemDecoration(decoration);
        rvTest.setAdapter(adapter);

        Log.v("MY_TAG", "--------------------------------oncreateview--------------" + type);

        return view;
    }

    public FragmentImage() {
        //当没有传入新闻的类型时，默认问安卓类的新闻
        //   this.type = FindNews.FIND_NEWS_ANDROID;
    }

    public FragmentImage(int type) {
        this.type = type;
        FindNews.getNews(type, 1);
        dataNews = NewsDB.getDBNews(FindNews.FIND_NEWS_GIRL, 10);
    }


    //获取更多数据
    public void setFragmentData() {
        // strType = "福利";
        FindNews.getNews(type, ++pageNum);
        dataNews.clear();
        //一次多获取十条
        listDataNum = listDataNum + 10;
        dataNews.addAll(NewsDB.getDBNews(FindNews.FIND_NEWS_GIRL, listDataNum));
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
            //    Toast.makeText(getActivity(), "下拉刷新", Toast.LENGTH_LONG).show();
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
              //  Toast.makeText(getActivity(), "上拉加载更多", Toast.LENGTH_LONG).show();
            }
        }, 1000);
    }
}
