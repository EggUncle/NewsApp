package uncle.egg.newsapp.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.support.v4.app.FragmentManager;

import uncle.egg.newsapp.R;
import uncle.egg.newsapp.fragment.FragmentToday;

public class HotPointActivity extends AppCompatActivity {
    private LinearLayout lineHot;
    private FragmentToday fragmentToday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hot_point);
        lineHot = (LinearLayout) findViewById(R.id.line_hot);
        String date = getIntent().getStringExtra("date");
        setTitle(date);
        fragmentToday = new FragmentToday(this,date);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction=fm.beginTransaction();
        transaction.replace(R.id.hot_point_fragment,fragmentToday);
        transaction.commit();
    }
}
