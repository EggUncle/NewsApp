package uncle.egg.newsapp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uncle.egg.newsapp.R;

/**
 * Created by egguncle on 16.7.20.
 */
public class FragmentImage extends Fragment{
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_news,null);
        return view;
    }

    private void init(){

    }
}
