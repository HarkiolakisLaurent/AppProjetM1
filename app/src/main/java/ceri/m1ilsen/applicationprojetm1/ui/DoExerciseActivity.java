package ceri.m1ilsen.applicationprojetm1.ui;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Environment;
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
import java.util.Random;
import java.util.Vector;

import ceri.m1ilsen.applicationprojetm1.R;
import ceri.m1ilsen.applicationprojetm1.exercise.Exercise;
import ceri.m1ilsen.applicationprojetm1.sqlite.MyApplicationDataSource;

public class DoExerciseActivity extends AppCompatActivity {

    private Button btnQuitter = null;
    private ImageButton recordingButton=null;
    private TextView txtView=null;
    List<String> lines = null;
    int position,i=0;
    private File exercisesDirectory = null;

    private static final int RECORDER_BPP = 16;
    private static final String AUDIO_RECORDER_FILE_EXT_WAV = ".wav";
    private static final String AUDIO_RECORDER_FOLDER = "App/Recordings";
    private static final String AUDIO_RECORDER_TEMP_FILE = "record_temp.raw";
    private static final int RECORDER_SAMPLERATE = 44100;
    private static final int RECORDER_CHANNELS = AudioFormat.CHANNEL_IN_STEREO;
    private static final int RECORDER_AUDIO_ENCODING = AudioFormat.ENCODING_PCM_16BIT;
    short[] audioData;

    private AudioRecord recorder = null;
    private int bufferSize = 0;
    private Thread recordingThread = null;
    private boolean isRecording = false;
    //Complex[] fftTempArray;
    //Complex[] fftArray;
    int[] bufferData;
    int bytesRecorded;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_exercise);
        txtView=(TextView)findViewById(R.id.textExercice);

        position = getIntent().getExtras().getInt("exerciseReadWordsCount");

        if (getIntent().getExtras().getBoolean("isNewExercise") == true) {
            moveExerciseToExercisesDirectory();
            storeExercise();
        }
        else {
            continueExercise();
            storeExercise();
        }
        bufferSize = AudioRecord.getMinBufferSize
                (RECORDER_SAMPLERATE,RECORDER_CHANNELS,RECORDER_AUDIO_ENCODING)*3;

        audioData = new short [bufferSize]; //short array that pcm data is put into.

        recordingButton = (ImageButton) findViewById(R.id.recordingButton);
        recordingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (!isRecording) {
                    startRecording();
                }
                else {
                    stopRecording();
                    Toast.makeText(getApplicationContext(),"Bientôt : "+lines.get(position),Toast.LENGTH_LONG).show();
                }
            }
        });
        btnQuitter=(Button) findViewById(R.id.btnQuitter);
        btnQuitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(10000);
                finish();
            }
        });
    }

    private void storeExercise() {
        Exercise exercise = null;
        final MyApplicationDataSource BD = new MyApplicationDataSource(getApplicationContext());
        BD.open();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy_HH:mm:ss");
        Date resultdate = new Date(System.currentTimeMillis());
        switch(getIntent().getStringExtra("task")) {
            case("mots"):
                if (getIntent().getExtras().getBoolean("isNewExercise") == true) {
                    String exerciseName = getIntent().getStringExtra("patientPseudo")+"_LireMots_"+sdf.format(resultdate);
                    exercise = new Exercise(exerciseName,"mots", 0);
                    getIntent().putExtra("exerciseName",exerciseName);
                    BD.insertExercise(exercise,getIntent().getExtras().getInt("patientId"));
                }
                else {
                    exercise = new Exercise(getIntent().getStringExtra("exerciseName"), "mots",
                            getIntent().getExtras().getInt("exerciseReadWordsCount"));
                }
                getIntent().putExtra("exerciseId",BD.getExerciseIdByTitle(exercise.getName()));
                break;

            case("phrases"):
                if (getIntent().getExtras().getBoolean("isNewExercise") == true) {
                    String exerciseName = getIntent().getStringExtra("patientPseudo")+"_LirePhrases_"+sdf.format(resultdate);
                    exercise = new Exercise(exerciseName,"phrases",0);
                    getIntent().putExtra("exerciseName",exerciseName);
                    BD.insertExercise(exercise,getIntent().getExtras().getInt("patientId"));
                }
                else {
                    exercise = new Exercise(getIntent().getStringExtra("exerciseName"),"phrases",
                            getIntent().getExtras().getInt("exerciseReadWordsCount"));
                }
                getIntent().putExtra("exerciseId",BD.getExerciseIdByTitle(exercise.getName()));
                break;

            case("textes"):
                if (getIntent().getExtras().getBoolean("isNewExercise") == true) {
                    String exerciseName = getIntent().getStringExtra("patientPseudo")+"_LireTextes_"+sdf.format(resultdate);
                    exercise = new Exercise(exerciseName,"textes",0);
                    getIntent().putExtra("exerciseName",exerciseName);
                    BD.insertExercise(exercise,getIntent().getExtras().getInt("patientId"));
                }
                else {
                    exercise = new Exercise(getIntent().getStringExtra("exerciseName"),"textes",
                            getIntent().getExtras().getInt("exerciseReadWordsCount"));
                }
                getIntent().putExtra("exerciseId",BD.getExerciseIdByTitle(exercise.getName()));
                break;

            case("custom"):
                if (getIntent().getExtras().getBoolean("isNewExercise") == true) {
                    exercise = new Exercise(getIntent().getStringExtra("exerciseName"),"custom",0);
                }
                else {
                    exercise = new Exercise(getIntent().getStringExtra("exerciseName"),"custom",
                            getIntent().getExtras().getInt("exerciseReadWordsCount"));
                }
                getIntent().putExtra("exerciseId",BD.getExerciseIdByTitle(exercise.getName()));
                break;

            default:
                break;
        }
        BD.close();

        if (getIntent().getExtras().getBoolean("isNewExercise") == true) {
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
        }
    }

    public static String getRandomElement (Vector v) {
        Random generator = new Random();
        int rnd = generator.nextInt(v.size() - 1);
        return (String)v.get(rnd);
    }

    private void moveExerciseToExercisesDirectory() {
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

                case("custom"):
                    flog = new InputStreamReader(new FileInputStream(getIntent().getStringExtra("customExercisePath")) );
                    exercisesDirectory = new File("storage/emulated/0/App/Exercises/Custom");
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
            else {
                i--;
            }
        }
        txtView.setText(++position + "/" + lines.size() + "\n" + lines.get(position-1));
    }

    private void continueExercise() {
        String myLine;
        InputStreamReader flog	= null;
        LineNumberReader llog;
        Vector<String> valeur=new Vector<String>();
        lines=new ArrayList<String>();
        try {
            flog = new InputStreamReader(new FileInputStream("/"+getIntent().getStringExtra("exercisePath")+"/"
                    +getIntent().getStringExtra("exerciseName")+".txt"));
            llog = new LineNumberReader(flog);
            try {
                while ((myLine = llog.readLine()) != null) {
                    lines.add(myLine);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            txtView.setText(++position + "/" + lines.size() + "\n" + lines.get(position-1));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String getFilename(){
        String filepath = Environment.getExternalStorageDirectory().getPath();
        File file = new File(filepath,AUDIO_RECORDER_FOLDER+"/"+getIntent().getStringExtra("patientPseudo"));

        if (!file.exists()) {
            file.mkdirs();
        }
        return (file.getAbsolutePath() + "/" + getIntent().getStringExtra("exerciseName") +
                AUDIO_RECORDER_FILE_EXT_WAV);
    }

    private String getTempFilename() {
        String filepath = Environment.getExternalStorageDirectory().getPath();
        File file = new File(filepath,AUDIO_RECORDER_FOLDER);

        if (!file.exists()) {
            file.mkdirs();
        }

        File tempFile = new File(filepath,AUDIO_RECORDER_TEMP_FILE);

        if (tempFile.exists())
            tempFile.delete();

        return (file.getAbsolutePath() + "/" + AUDIO_RECORDER_TEMP_FILE);
    }

    private void startRecording() {
        recordingButton.setImageResource(R.drawable.micro1);
        recordingButton.setBackgroundResource(R.drawable.ic_recording_green);
        recorder = new AudioRecord(MediaRecorder.AudioSource.MIC,
                RECORDER_SAMPLERATE,
                RECORDER_CHANNELS,
                RECORDER_AUDIO_ENCODING,
                bufferSize);
        int i = recorder.getState();
        if (i==1)
            recorder.startRecording();

        isRecording = true;

        recordingThread = new Thread(new Runnable() {
            @Override
            public void run() {
                writeAudioDataToFile();
            }
        }, "AudioRecorder Thread");

        recordingThread.start();

        new Thread() {
            public void run() {
                try {

                    if (getIntent().getStringExtra("task").equals("custom")) {
                        Thread.sleep(Long.parseLong(getIntent().getStringExtra("customExerciseMaxDuration"))*1000);
                    }
                    else {
                        Thread.sleep(10000);
                    }
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            txtView.setText(++position + "/" + lines.size() + "\n" + lines.get(position-1));
                            MyApplicationDataSource BD = new MyApplicationDataSource(getApplicationContext());
                            BD.open();
                            BD.updateExerciseReadWordsCount(getIntent().getExtras().getInt("exerciseId"),position-1);
                            BD.close();
                            stopRecording();
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void writeAudioDataToFile() {
        byte data[] = new byte[bufferSize];
        String filename = getTempFilename();
        FileOutputStream os = null;

        try {
            os = new FileOutputStream(filename,true);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        int read = 0;
        if (null != os) {
            while(isRecording) {
                read = recorder.read(data, 0, bufferSize);
                if (read > 0){
                }

                if (AudioRecord.ERROR_INVALID_OPERATION != read) {
                    try {
                        os.write(data);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void stopRecording() {
        if (null != recorder){
            recordingButton.setImageResource(R.drawable.micro1);
            recordingButton.setBackgroundResource(R.drawable.ic_recording_red);
            isRecording = false;

            int i = recorder.getState();
            if (i==1)
                recorder.stop();
            recorder.release();

            recorder = null;
            recordingThread = null;
        }

        copyWaveFile(getTempFilename(),getFilename());
        deleteTempFile();
    }

    private void deleteTempFile() {
        File file = new File(getTempFilename());
        file.delete();
    }

    private void copyWaveFile(String inFilename,String outFilename){
        FileInputStream in = null;
        FileOutputStream out = null;
        long totalAudioLen = 0;
        long totalDataLen = totalAudioLen + 36;
        long longSampleRate = RECORDER_SAMPLERATE;
        int channels = 2;
        long byteRate = RECORDER_BPP * RECORDER_SAMPLERATE * channels/8;

        byte[] data = new byte[bufferSize];

        try {
            in = new FileInputStream(inFilename);
            out = new FileOutputStream(outFilename);
            totalAudioLen = in.getChannel().size();
            totalDataLen = totalAudioLen + 36;

            //AppLog.logString("File size: " + totalDataLen);

            WriteWaveFileHeader(out, totalAudioLen, totalDataLen,
                    longSampleRate, channels, byteRate);

            while(in.read(data) != -1) {
                out.write(data);
            }

            in.close();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
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
