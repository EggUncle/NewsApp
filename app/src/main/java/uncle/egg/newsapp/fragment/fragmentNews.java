package uncle.egg.newsapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.xlf.nrl.NsRefreshLayout;

import uncle.egg.newsapp.R;
import uncle.egg.newsapp.module.TestRecyclerAdapter;

/**
 * Created by egguncle on 16.7.20.
 */
public class fragmentNews extends Fragment  implements
        NsRefreshLayout.NsRefreshLayoutController, NsRefreshLayout.NsRefreshLayoutListener{

    private boolean loadMoreEnable = true;
    private NsRefreshLayout refreshLayout;
    private RecyclerView rvTest;

    private View view;


    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_news,null);
        refreshLayout = (NsRefreshLayout)view. findViewById(R.id.nrl_test);
        refreshLayout.setRefreshLayoutController(this);
        refreshLayout.setRefreshLayoutListener(this);

        rvTest = (RecyclerView)view. findViewById(R.id.rv_test);
        TestRecyclerAdapter adapter = new TestRecyclerAdapter(getActivity());
        rvTest.setAdapter(adapter);

        return view;
    }

    private void init(){

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
                Toast.makeText(getActivity(),"下拉刷新",Toast.LENGTH_LONG).show();
            }
        }, 1000);
    }

    @Override
    public void onLoadMore() {
        refreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshLayout.finishPullLoad();
                Toast.makeText(getActivity(),"上拉加载更多",Toast.LENGTH_LONG).show();
            }
        }, 1000);
    }
}
