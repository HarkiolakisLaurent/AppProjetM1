package ceri.m1ilsen.applicationprojetm1.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ceri.m1ilsen.applicationprojetm1.R;

public class CreateExerciceActivity2 extends AppCompatActivity {

    private TextView mTextMessage;
    private Button btnLireMots;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_exercice2);

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
                    Random random=new Random();
                    List<String> lines = new ArrayList<String>();

                    while ((sb= bufferedReader.readLine()) != null){

                       lines.add(sb);
                        linenumber++;
                        int index=random.nextInt(lines.size());

                        if (sb.equals(lines.get(index))) {
                            mot=sb;
                            position = linenumber;
                        }
                    }
                   // System.out.println("Total number of lines : " + linenumber+" la position est "+position+" le mot est "+mot);

                    // lnr.close();
                    bufferedReader.close();
                    //Transfere des données vers l'activité DoExerciceActivity
                    Intent intent = new Intent(getBaseContext(), DoExerciceActivity.class);
                    intent.putExtra("WordTotal", linenumber);
                    intent.putExtra("PositionWord", position);
                    intent.putExtra("Word", mot);
                    startActivity(intent);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

}
