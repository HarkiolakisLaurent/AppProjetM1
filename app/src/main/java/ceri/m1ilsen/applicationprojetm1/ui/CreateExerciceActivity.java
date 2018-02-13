package ceri.m1ilsen.applicationprojetm1.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import ceri.m1ilsen.applicationprojetm1.R;

/**
 * Created by le fléo on 13/02/2018.
 */

public class CreateExerciceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_exercice);
    }

    public void Home (View view) {
        Intent intent = new Intent(this,HomePageActivity.class);
        startActivity(intent);
    }
}
