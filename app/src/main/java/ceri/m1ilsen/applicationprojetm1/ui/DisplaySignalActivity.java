package ceri.m1ilsen.applicationprojetm1.ui;

import android.app.Activity;
import android.graphics.Canvas;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.IOException;

import ceri.m1ilsen.applicationprojetm1.R;
import ceri.m1ilsen.applicationprojetm1.fragment.WaveformFragment;
import ceri.m1ilsen.applicationprojetm1.soundfile.CheapWAV;


public class DisplaySignalActivity extends AppCompatActivity {

    String fichier;

    public static class ImplementWaveformFragment extends WaveformFragment {}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_signal);
        Toast.makeText(getApplicationContext(),getIntent().getStringExtra("fileName"),Toast.LENGTH_LONG).show();
        ImplementWaveformFragment wf = new ImplementWaveformFragment();
        wf.setFileName(getIntent().getExtras().getString("fileName"));
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.waveform, wf)
                .commit();

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
