package ceri.m1ilsen.applicationprojetm1.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import ceri.m1ilsen.applicationprojetm1.R;
import ceri.m1ilsen.applicationprojetm1.sqlite.CommentsDataSource;
import ceri.m1ilsen.applicationprojetm1.sqlite.Contexte;

/**
 * Created by le fléo on 16/03/2018.
 */

public class CliniResultActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_results);

        String[] sessions ;
        Button btnExercice = null;
        Button btnEnregist = null;
        Button btnResult = null;

        btnEnregist = (Button) findViewById(R.id.btnEnregist);
        btnEnregist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Le premier paramètre est le nom de l'activité actuelle
                // Le second est le nom de l'activité de destination
                Intent nextActivity = new Intent(CliniResultActivity.this, RecordingsActivity.class);
                startActivity(nextActivity);
            }
        });


            }
}
