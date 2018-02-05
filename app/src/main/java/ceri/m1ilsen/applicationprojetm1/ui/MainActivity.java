package ceri.m1ilsen.applicationprojetm1.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import ceri.m1ilsen.applicationprojetm1.R;
import ceri.m1ilsen.applicationprojetm1.sqlite.CommentsDataSource;

public class MainActivity extends AppCompatActivity {
    private CommentsDataSource datasource;
    private TextView signUp = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signUp = (TextView) findViewById(R.id.signup);



        signUp.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                // Le premier paramètre est le nom de l'activité actuelle

                // Le second est le nom de l'activité de destination

                Intent secondeActivite = new Intent(MainActivity.this, SignupActivity.class);



                // On rajoute un extra

                //secondeActivite.putExtra(AGE, 31);


                // Puis on lance l'intent !

                startActivity(secondeActivite);

            }

        });
    }

    @Override
    public void onResume() {
        //datasource.open();
        super.onResume();
    }

    @Override
    public void onPause() {
        //datasource.close();
        super.onPause();
    }
}
