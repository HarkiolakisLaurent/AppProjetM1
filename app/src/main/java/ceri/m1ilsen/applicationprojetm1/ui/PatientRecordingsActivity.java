package ceri.m1ilsen.applicationprojetm1.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;

import ceri.m1ilsen.applicationprojetm1.R;
import ceri.m1ilsen.applicationprojetm1.adapter.RecordingsListViewAdapter;

public class PatientRecordingsActivity extends AppCompatActivity {


    private ListView lv;
    private RecordingsListViewAdapter recordingsAdapter;
    private ArrayList<String> data = new ArrayList<String>();
    private File dataPath;
    private TextView numberOfRecordings;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.patient_navigation_exercises:
                    Intent exercises = new Intent(PatientRecordingsActivity.this, CreateExerciseActivity.class);
                    startActivity(exercises);
                    return true;
                case R.id.patient_navigation_recordings:
                    return true;
                case R.id.patient_navigation_results:
                    Intent results = new Intent(PatientRecordingsActivity.this, PatientResultsActivity.class);
                    startActivity(results);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_recordings);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                //Cela signifie que la permission à déjà été
                //demandé et l'utilisateur l'a refusé
                //Vous pouvez aussi expliquer à l'utilisateur pourquoi
                //cette permission est nécessaire et la redemander
            } else {
                //Sinon demander la permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
            }
        }

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
            }
        }

        lv=(ListView)findViewById(R.id.listResults);
        dataPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        data = new ArrayList(Arrays.asList(dataPath.list(new FilenameFilter() {
            public boolean accept(File directory, String fileName) {
                return fileName.endsWith(".wav") || fileName.endsWith(".mp3");
            }
        })));
        //data.add(new String("Le DATE à HEURE, vous avez obtenu 75"));

        numberOfRecordings = (TextView) findViewById(R.id.numberOfRecordings);
        if (data.size() == 0)
            numberOfRecordings.setText("Aucun enregistrement disponible");
        else if (data.size() == 1)
            numberOfRecordings.setText(data.size()+" enregistrement est disponible");
        else
            numberOfRecordings.setText(data.size()+" enregistrements sont disponibles");

        RecordingsListViewAdapter adapter = new RecordingsListViewAdapter(this, R.layout.recording_item_view, data, dataPath);
        lv.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_file, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_home:
                Intent homepage = new Intent(this, PatientHomePageActivity.class);
                startActivity(homepage);
                return true;
            case R.id.action_conf:
                Intent settings = new Intent(this, ConfigureExerciseActivity.class);
                startActivity(settings);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
