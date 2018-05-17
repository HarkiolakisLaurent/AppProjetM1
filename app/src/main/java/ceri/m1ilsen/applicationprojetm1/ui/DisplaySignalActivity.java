package ceri.m1ilsen.applicationprojetm1.ui;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import ceri.m1ilsen.applicationprojetm1.R;
import ceri.m1ilsen.applicationprojetm1.fragment.WaveformFragment;
import ceri.m1ilsen.applicationprojetm1.soundfile.CheapSoundFile;
import ceri.m1ilsen.applicationprojetm1.soundfile.WavFile;
import ceri.m1ilsen.applicationprojetm1.soundfile.WavFileException;
import ceri.m1ilsen.applicationprojetm1.sqlite.MyApplicationDataSource;


public class DisplaySignalActivity extends AppCompatActivity implements CheapSoundFile.ProgressListener{

    @Override
    public boolean reportProgress(double fractionComplete) {
        return false;
    }

    public static class ImplementWaveformFragment extends WaveformFragment {}

    public class DrawView extends View {
        Paint paint = new Paint();

        private void init() {
            paint.setColor(Color.BLACK);
        }

        public DrawView(Context context) {
            super(context);
            init();
        }

        @Override
        public void onDraw(Canvas canvas) {
            canvas.drawLine(25, 70, 25, 545, paint);
            canvas.drawLine(225, 70, 225, 545, paint);
            canvas.drawLine(425, 70, 425, 545, paint);
            canvas.drawLine(625, 70, 625, 545, paint);
            canvas.drawLine(825, 70, 825, 545, paint);
            canvas.drawLine(1025, 70, 1025, 545, paint);
            canvas.drawLine(1225, 70, 1225, 545, paint);
        }

    }
    public LinearLayout tv;
    public static MediaPlayer mediaPlayer;
    private boolean isPlaying = false;
    public ImageButton pl;
    public ImageButton stp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_signal);
        pl=(ImageButton)findViewById(R.id.playb);
        stp=(ImageButton)findViewById(R.id.stopb);
        Toast.makeText(getApplicationContext(),getIntent().getStringExtra("fileName"),Toast.LENGTH_LONG).show();
        final ImplementWaveformFragment wf = new ImplementWaveformFragment();
        wf.setFileName(getIntent().getExtras().getString("fileName"));

        getSupportFragmentManager().beginTransaction()
                .add(R.id.waveform, wf)
                .commit();
        tv = findViewById(R.id.tv);

        try {
            WavFile csf = WavFile.openWavFile(new File(getIntent().getExtras().getString("fileName")));
            long dur = csf.getDuration();
            tv.addView(new TextView(this),50,50);
            for(int i=0;i<dur;i+=2){
                Button btn =new Button(this);
                btn.setText("PH "+i/2);
                final int j=i;
                btn.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        if (isPlaying) {
                            mediaPlayer.stop();
                            isPlaying = false;
                        } else {
                            mediaPlayer = MediaPlayer.create(pl.getContext(), Uri.parse(getIntent().getExtras().getString("fileName")));
                            mediaPlayer.seekTo(j * 500);
                            int ed = (j * 500) + 1000;
                            mediaPlayer.start();
                            isPlaying = true;
                            if (mediaPlayer.getCurrentPosition() == ed) {
                                mediaPlayer.stop();
                            }
                        }
                    }
                });

                tv.addView(btn,100,80);
                tv.addView(new TextView(this),50,50);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (WavFileException e) {
            e.printStackTrace();
        }
        LinearLayout.LayoutParams linearLayoutParams =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
        DrawView drawView = new DrawView(this);
        getWindow().addContentView(drawView,linearLayoutParams);

        pl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPlaying) {
                    mediaPlayer.stop();
                    isPlaying = false;
                }else {
                    mediaPlayer = MediaPlayer.create(pl.getContext(), Uri.parse(getIntent().getExtras().getString("fileName")));
                    mediaPlayer.start();
                    isPlaying = true;
                    wf.setMediaPlay(true);
                    wf.waveformDraw();
                }
            }
        });
        stp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer != null) {
                    mediaPlayer.stop();

                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_retour, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_return:
                Activity activity = DisplaySignalActivity.this;
                activity.setResult(1);
                activity.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
