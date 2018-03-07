package ceri.m1ilsen.applicationprojetm1.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;

import ceri.m1ilsen.applicationprojetm1.R;

public class RecordingsActivity extends AppCompatActivity {


    private ListView lv;
    private RecordingsListViewAdapter recordingsAdapter;
    private ArrayList<String> data = new ArrayList<String>();
    private File dataPath;
    private TextView numberOfRecordings;
    private Button exerciseMenu;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recordings);

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
                return fileName.endsWith(".wav") || fileName.endsWith(".mp3") || fileName.endsWith(".mp4");
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

        exerciseMenu = (Button) findViewById(R.id.exerciseMenu);
        exerciseMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextActivity = new Intent(RecordingsActivity.this, CreateExerciceActivity2.class);
                startActivity(nextActivity);
            }
        });
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
                Intent homepage = new Intent(this, HomePageActivity.class);
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
