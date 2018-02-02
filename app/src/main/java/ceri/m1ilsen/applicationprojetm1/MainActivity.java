package ceri.m1ilsen.applicationprojetm1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ceri.m1ilsen.applicationprojetm1.sqlite.CommentsDataSource;

public class MainActivity extends AppCompatActivity {
    private CommentsDataSource datasource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        datasource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        datasource.close();
        super.onPause();
    }
}
