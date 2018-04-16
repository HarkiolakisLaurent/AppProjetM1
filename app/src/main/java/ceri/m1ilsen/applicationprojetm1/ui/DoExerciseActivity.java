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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

import ceri.m1ilsen.applicationprojetm1.R;

public class DoExerciseActivity extends AppCompatActivity {

    private Button btnQuitter = null;
    private ImageButton imageMicroButton=null;
    private TextView txtView=null;
    List<String> lines = null;
    int position,i=0;
    String speechword="";
    boolean pause=true;

    public static String getRandomElement (Vector v) {
        Random generator = new Random();
        int rnd = generator.nextInt(v.size() - 1);
        return (String)v.get(rnd);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_exercise);
        txtView=(TextView)findViewById(R.id.textExercice);
        String myLine;
        InputStreamReader flog	= null;
        LineNumberReader llog;
        Vector<String> valeur=new Vector<String>();
        lines=new ArrayList<String>();
        File exercisesDirectory = null;
        try {
            switch(getIntent().getStringExtra("task")) {
                case("mots"):
                    flog = new InputStreamReader(new FileInputStream("/storage/emulated/0/App/Resources/WORDS_RESOURCE.txt" +"") );
                    exercisesDirectory = new File("storage/emulated/0/App/Exercises/Words");
                    break;

                case("phrases"):
                    flog = new InputStreamReader(new FileInputStream("/storage/emulated/0/App/Resources/SENTENCES_RESOURCE.txt" +"") );
                    exercisesDirectory = new File("storage/emulated/0/App/Exercises/Sentences");
                    break;

                case("textes"):
                    flog = new InputStreamReader(new FileInputStream("/storage/emulated/0/App/Resources/TEXT_RESOURCE.txt" +"") );
                    exercisesDirectory = new File("storage/emulated/0/App/Exercises/Texts");
                    break;

                default:
                    break;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        llog = new LineNumberReader(flog);
        try {
            while ((myLine = llog.readLine()) != null) {
                valeur.add(myLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(int i=0;i<52;i++) {
            lines.add(getRandomElement(valeur));
        }

        File file = new File(exercisesDirectory+"/idqqchEx.txt");
        try {
            if (!file.exists()) {
                new File(file.getParent()).mkdirs();
                file.createNewFile();
            }
        } catch (IOException e) {
            Log.e("", "Could not create file.", e);
            return;
        }
        try {
            FileWriter fw = new FileWriter(file,false);
            for(String str:lines)
                fw.write(str+System.getProperty( "line.separator" ));
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        readlist(lines);
        imageMicroButton = (ImageButton) findViewById(R.id.imageBtnMicro);
        imageMicroButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                promptSpeechInput();
            }
        });
        btnQuitter=(Button) findViewById(R.id.btnQuitter);
        btnQuitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Le premier paramètre est le nom de l'activité actuelle
                // Le second est le nom de l'activité de destination
                setResult(1);
                finish();
            }
        });

    }

    public void readlist(final List<String> list)  {


        new Thread() {
            public void run() {
                while (i< list.size()) {

                    try {
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                txtView.setText(++position + "/" + list.size() + "\n" + list.get(i));
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
                     speechword=result.get(0).toString();

                    File file = new File("storage/emulated/0/App/Speech/WORDS_Speech.wav");
                    try {
                        if (!file.exists()) {
                            new File(file.getParent()).mkdirs();
                            file.createNewFile();
                            FileWriter fw = new FileWriter(file);
                            fw.write (speechword);
                            fw.close();
                        }
                    } catch (IOException e) {
                        Log.e("", "Could not create file.", e);
                        return;
                    }

                    try {
                        TimeUnit.SECONDS.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
        }
    }
}
