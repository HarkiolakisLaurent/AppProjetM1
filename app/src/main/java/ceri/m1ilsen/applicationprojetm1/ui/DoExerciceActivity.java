package ceri.m1ilsen.applicationprojetm1.ui;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import ceri.m1ilsen.applicationprojetm1.R;

public class DoExerciceActivity extends AppCompatActivity {

    private Button btnQuitter = null;
    private ImageButton imageMicroButton=null;
    private TextView txtView=null;
    private TextView txtViewSpeech=null;
    List<String> lines = null;
    ReadWordFile readWordFile=null;
    int position,i=0;
    String speechword="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_exercise);
        txtView=(TextView)findViewById(R.id.textExercice);
        txtViewSpeech=(TextView)findViewById(R.id.textViewsp);

        InputStream fis = getResources().openRawResource(R.raw.mots);
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader bufferedReader = new BufferedReader(isr);

        lines=new ArrayList<String>();
        readWordFile=new ReadWordFile(this);
        lines=readWordFile.readFile(bufferedReader);

        readlist(lines);

        addListenerOnButton();
        BoutonQuitter();

    }

    public void readlist(final List<String> list)  {


        new Thread() {
            public void run() {
                while (i< list.size()) {

                    try {
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                txtView.setText(++position+"/"+list.size()+"\n"+ list.get(i));
                                i++;
                            }
                        });
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }


    public void addListenerOnButton() {

        imageMicroButton = (ImageButton) findViewById(R.id.imageBtnMicro);

        imageMicroButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                promptSpeechInput();

            }

        });

    }

    public void promptSpeechInput() {
        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        try{
            startActivityForResult(i, 100);
        }
        catch(ActivityNotFoundException a){
            Toast.makeText(this, "Sorry! Your device doesn't speech your language ", Toast.LENGTH_LONG).show();
        }
    }

    public void onActivityResult(int request_code, int result_code, Intent i) {
        super.onActivityResult(request_code, result_code, i);

        switch (request_code) {
            case 100:
                if (result_code == RESULT_OK && i != null) {
                    ArrayList<String> result = i.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                   // result.get(0).toString();
                     speechword=result.get(0).toString();
                    System.out.println("ttttttttttttttttttttttttttttttttttt "+speechword);
                     WriteFile(speechword,getApplicationContext());

                    try {
                        TimeUnit.SECONDS.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
        }
    }

    public void WriteFile(String word,Context context){

            try {
                FileOutputStream fileout=openFileOutput("savespeech.txt", context.MODE_PRIVATE);
                OutputStreamWriter outputWriter=new OutputStreamWriter(fileout);
                outputWriter.write(word);
                outputWriter.close();

                //display file saved message
                Toast.makeText(getBaseContext(), "File saved successfully!",
                        Toast.LENGTH_SHORT).show();

                System.out.println("++++++++++++++++++++++++++++++++++++"+word);

                //System.out.println("+++++++++++++ "+ getResources().openRawResource(R.raw.savespeech));

            }
            catch (IOException e) {
                Log.e("Exception", "File write failed: " + e.toString());
            }


    }
    public void BoutonQuitter(){

        btnQuitter=(Button) findViewById(R.id.btnQuitter);

        btnQuitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Le premier paramètre est le nom de l'activité actuelle
                // Le second est le nom de l'activité de destination
                Intent nextActivity = new Intent(DoExerciceActivity.this, PatientActivity.class);
                startActivity(nextActivity);
            }
        });
    }
}
