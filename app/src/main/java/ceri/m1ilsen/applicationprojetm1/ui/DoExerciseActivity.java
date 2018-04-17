package ceri.m1ilsen.applicationprojetm1.ui;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

import ceri.m1ilsen.applicationprojetm1.R;
import ceri.m1ilsen.applicationprojetm1.exercise.Exercise;
import ceri.m1ilsen.applicationprojetm1.sqlite.MyApplicationDataSource;
import ceri.m1ilsen.applicationprojetm1.task.Task;

public class DoExerciseActivity extends AppCompatActivity {

    private Button btnQuitter = null;
    private ImageButton imageMicroButton=null;
    private TextView txtView=null;
    List<String> lines = null;
    int position,i=0;
    String speechword="";
    boolean pause=true;
    private File exercisesDirectory = null;


    private static final int RECORDER_BPP = 16;
    private static final int RECORDER_SAMPLERATE = 44100;
    private static final int RECORDER_CHANNELS = AudioFormat.CHANNEL_IN_STEREO;
    private static final int RECORDER_AUDIO_ENCODING = AudioFormat.ENCODING_PCM_16BIT;
    short[] audioData;

    private int bufferSize = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_exercise);
        txtView=(TextView)findViewById(R.id.textExercice);

        initExercise();

        bufferSize = AudioRecord.getMinBufferSize
                (RECORDER_SAMPLERATE,RECORDER_CHANNELS,RECORDER_AUDIO_ENCODING)*3;

        audioData = new short [bufferSize]; //short array that pcm data is put into.

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
                Exercise exercise = null;
                final MyApplicationDataSource BD = new MyApplicationDataSource(getApplicationContext());
                BD.open();
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy_HH:mm");
                Date resultdate = new Date(System.currentTimeMillis());
                switch(getIntent().getStringExtra("task")) {
                    case("mots"):
                        exercise = new Exercise(getIntent().getStringExtra("patientPseudo")+"_LireMots_"+sdf.format(resultdate),0);
                        BD.insertExercise(exercise,getIntent().getExtras().getInt("patientId"));
                        break;

                    case("phrases"):
                        exercise = new Exercise(getIntent().getStringExtra("patientPseudo")+"_LirePhrases_"+sdf.format(resultdate),0);
                        BD.insertExercise(exercise,getIntent().getExtras().getInt("patientId"));
                        break;

                    case("textes"):
                        exercise = new Exercise(getIntent().getStringExtra("patientPseudo")+"_LireTextes_"+sdf.format(resultdate),0);
                        BD.insertExercise(exercise,getIntent().getExtras().getInt("patientId"));
                        break;

                    default:
                        break;
                }

                File file = new File(exercisesDirectory+"/"+exercise.getName()+".txt");
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
                BD.close();
                setResult(10000);
                finish();
            }
        });
    }

    public static String getRandomElement (Vector v) {
        Random generator = new Random();
        int rnd = generator.nextInt(v.size() - 1);
        return (String)v.get(rnd);
    }

    public void initExercise() {
        String myLine;
        InputStreamReader flog	= null;
        LineNumberReader llog;
        Vector<String> valeur=new Vector<String>();
        lines=new ArrayList<String>();

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
            String line = getRandomElement(valeur);
            if (!lines.contains(line))
                lines.add(line);
        }

        readlist(lines);
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
                    try {
                        FileOutputStream out = new FileOutputStream("storage/emulated/0/Recordings/null/test.wav");
                        long totalAudioLen = 0;
                        long totalDataLen = totalAudioLen + 36;
                        long longSampleRate = RECORDER_SAMPLERATE;
                        int channels = 2;
                        long byteRate = RECORDER_BPP * RECORDER_SAMPLERATE * channels/8;
                        WriteWaveFileHeader(out, totalAudioLen, totalDataLen, longSampleRate, channels, byteRate);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    /*ArrayList<String> result = i.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
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
                    }*/

                    try {
                        TimeUnit.SECONDS.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
        }
    }

    private void WriteWaveFileHeader(
            FileOutputStream out, long totalAudioLen,
            long totalDataLen, long longSampleRate, int channels,
            long byteRate) throws IOException
    {
        byte[] header = new byte[44];

        header[0] = 'R';  // RIFF/WAVE header
        header[1] = 'I';
        header[2] = 'F';
        header[3] = 'F';
        header[4] = (byte) (totalDataLen & 0xff);
        header[5] = (byte) ((totalDataLen >> 8) & 0xff);
        header[6] = (byte) ((totalDataLen >> 16) & 0xff);
        header[7] = (byte) ((totalDataLen >> 24) & 0xff);
        header[8] = 'W';
        header[9] = 'A';
        header[10] = 'V';
        header[11] = 'E';
        header[12] = 'f';  // 'fmt ' chunk
        header[13] = 'm';
        header[14] = 't';
        header[15] = ' ';
        header[16] = 16;  // 4 bytes: size of 'fmt ' chunk
        header[17] = 0;
        header[18] = 0;
        header[19] = 0;
        header[20] = 1;  // format = 1
        header[21] = 0;
        header[22] = (byte) channels;
        header[23] = 0;
        header[24] = (byte) (longSampleRate & 0xff);
        header[25] = (byte) ((longSampleRate >> 8) & 0xff);
        header[26] = (byte) ((longSampleRate >> 16) & 0xff);
        header[27] = (byte) ((longSampleRate >> 24) & 0xff);
        header[28] = (byte) (byteRate & 0xff);
        header[29] = (byte) ((byteRate >> 8) & 0xff);
        header[30] = (byte) ((byteRate >> 16) & 0xff);
        header[31] = (byte) ((byteRate >> 24) & 0xff);
        header[32] = (byte) (2 * 16 / 8);  // block align
        header[33] = 0;
        header[34] = RECORDER_BPP;  // bits per sample
        header[35] = 0;
        header[36] = 'd';
        header[37] = 'a';
        header[38] = 't';
        header[39] = 'a';
        header[40] = (byte) (totalAudioLen & 0xff);
        header[41] = (byte) ((totalAudioLen >> 8) & 0xff);
        header[42] = (byte) ((totalAudioLen >> 16) & 0xff);
        header[43] = (byte) ((totalAudioLen >> 24) & 0xff);

        out.write(header, 0, 44);
    }

}
