package ceri.m1ilsen.applicationprojetm1.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import ceri.m1ilsen.applicationprojetm1.R;

public class CreateExerciseActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private Button btnLireMots;
    static int i=0;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.patient_navigation_exercises:
                    return true;
                case R.id.patient_navigation_recordings:
                    Intent recordings = new Intent(CreateExerciseActivity.this, PatientRecordingsActivity.class);
                    startActivity(recordings);
                    return true;
                case R.id.patient_navigation_results:
                    Intent results = new Intent(CreateExerciseActivity.this, PatientResultsActivity.class);
                    startActivity(results);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_exercise);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        //File file=new File("/src/java/mots.txt.txt");
        ReadFileWord();
    }

    public void ReadFileWord(){

        btnLireMots=(Button) findViewById(R.id.button10);
        btnLireMots.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                try {
                InputStream fis = getResources().openRawResource(R.raw.mots);
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader bufferedReader = new BufferedReader(isr);
                String sb = "";
                int position=0;
                    String t="";
                   // LineNumberReader lnr = new LineNumberReader(bufferedReader);
                    int linenumber = 0;
                    String mot="";
                   // Random random=new Random();
                    List<String> lines = new ArrayList<String>();

                    Intent intent = new Intent(getBaseContext(), DoExerciceActivity.class);
                    while ((sb= bufferedReader.readLine()) != null){

                       lines.add(sb);

                    }
            bufferedReader.close();
                    for (String m: lines  ) {
                        //System.out.println("chaine file: "+m);
                        intent.putExtra("Word", lines.get(i));
                        intent.putExtra("WordTotal", lines.size());
                        intent.putExtra("PositionWord", i+1);

                        startActivity(intent);

                    }
                   //i++;


                    //mot=lines.get(i);


                    // int index=random.nextInt(lines.size());

                        /*if (sb.equals(lines.get(index))) {
                            mot=sb;
                            position = linenumber;
                        }*/


                   // System.out.println("Total number of lines : " + linenumber+" la position est "+position+" le mot est "+mot);

                    // lnr.close();
                  //  bufferedReader.close();
                    //Transfere des données vers l'activité DoExerciceActivity


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

}
