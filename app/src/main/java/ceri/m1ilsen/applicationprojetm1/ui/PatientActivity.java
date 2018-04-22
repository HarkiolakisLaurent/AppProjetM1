package ceri.m1ilsen.applicationprojetm1.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import ceri.m1ilsen.applicationprojetm1.R;
import ceri.m1ilsen.applicationprojetm1.exercise.Session;
import ceri.m1ilsen.applicationprojetm1.fragment.CreateExerciseFragment;
import ceri.m1ilsen.applicationprojetm1.fragment.PatientHomePageFragment;
import ceri.m1ilsen.applicationprojetm1.fragment.PatientRecordingsFragment;
import ceri.m1ilsen.applicationprojetm1.fragment.PatientResultsFragment;
import ceri.m1ilsen.applicationprojetm1.fragment.PatientSettingsFragment;
import ceri.m1ilsen.applicationprojetm1.sqlite.MyApplicationDataSource;

public class PatientActivity extends AppCompatActivity {

    private PatientHomePageFragment homePageFragment = new PatientHomePageFragment();
    private CreateExerciseFragment exerciseFragment = new CreateExerciseFragment();
    private PatientRecordingsFragment recordingsFragment = new PatientRecordingsFragment();
    private PatientResultsFragment resultsFragment = new PatientResultsFragment();
    private PatientSettingsFragment settingsFragment = new PatientSettingsFragment();
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);

        setResult(101);

        final File resourcesDirectory = new File("/storage/emulated/0/App/Resources/");
        if (!resourcesDirectory.exists()) {
            resourcesDirectory.mkdirs();
        }
        initExercisesFiles();
        final File recordingsDirectory = new File("/storage/emulated/0/App/Recordings/"+getIntent().getStringExtra("connectedUserPseudo"));
        if (!recordingsDirectory.exists()) {
            recordingsDirectory.mkdirs();
        }
        final File exercisesDirectory = new File("/storage/emulated/0/App/Exercises/");
        if (!exercisesDirectory.exists()) {
            exercisesDirectory.mkdirs();
        }



        final MyApplicationDataSource BD = new MyApplicationDataSource(this);
        BD.open();
        SimpleDateFormat sdfDay = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat sdfHour = new SimpleDateFormat("HH:mm");
        Date resultdate = new Date(System.currentTimeMillis());
        String sessionName = "Session du "+sdfDay.format(resultdate)+" à "+sdfHour.format(resultdate);
        Session session = new Session(sessionName,0,null);
        BD.insertSession(session,getIntent().getExtras().getInt("connectedUserId"));
        getIntent().putExtra("sessionId",BD.getSessionIdFromTitle(sessionName));
        BD.close();
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, homePageFragment);
        fragmentTransaction.commit();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.patient_navigation_home:
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentContainer, homePageFragment);
                    fragmentTransaction.commit();
                    return true;
                case R.id.patient_navigation_exercises:
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentContainer, exerciseFragment);
                    fragmentTransaction.commit();
                    return true;
                case R.id.patient_navigation_recordings:
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentContainer, recordingsFragment);
                    fragmentTransaction.commit();
                    return true;
                case R.id.patient_navigation_results:
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentContainer, resultsFragment);
                    fragmentTransaction.commit();
                    return true;
                case R.id.patient_navigation_settings:
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentContainer, settingsFragment);
                    fragmentTransaction.commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_file, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_logout:
                final Activity activity = PatientActivity.this;
                AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
                alertDialog.setTitle("Déconnexion");
                alertDialog.setMessage("Souhaitez-vous vous déconnecter ?");
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Non",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Oui",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // déconnexion ici
                                activity.setResult(1000);
                                activity.finish();
                            }
                        });
                alertDialog.show();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void initExercisesFiles() {
        InputStream inputStream = getResources().openRawResource(R.raw.mots);
        try {
            if (inputStream != null) {
                // open a reader on the inputStream
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

                // String used to store the lines
                String str;
                StringBuilder buf = new StringBuilder();

                // Read the file
                while ((str = reader.readLine()) != null) {
                    buf.append(str + "\r\n");
                }
                // Close streams
                reader.close();
                inputStream.close();
                File file = new File("storage/emulated/0/App/Resources/WORDS_RESOURCE.txt");
                try {
                    if (!file.exists()) {
                        new File(file.getParent()).mkdirs();
                        file.createNewFile();
                        FileWriter fw = new FileWriter(file);
                        fw.write (buf.toString());
                        fw.close();
                    }
                } catch (IOException e) {
                    Log.e("", "Could not create file.", e);
                    return;
                }
            }
        } catch (java.io.FileNotFoundException e) {
            Toast.makeText(getApplicationContext(), "FileNotFoundException", Toast.LENGTH_LONG);
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "FileNotFoundException", Toast.LENGTH_LONG);
        }

        inputStream = getResources().openRawResource(R.raw.phrases);
        try {
            if (inputStream != null) {
                // open a reader on the inputStream
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

                // String used to store the lines
                String str;
                StringBuilder buf = new StringBuilder();

                // Read the file
                while ((str = reader.readLine()) != null) {
                    buf.append(str + "\r\n");
                }
                // Close streams
                reader.close();
                inputStream.close();
                File file = new File("storage/emulated/0/App/Resources/SENTENCES_RESOURCE.txt");
                try {
                    if (!file.exists()) {
                        new File(file.getParent()).mkdirs();
                        file.createNewFile();
                        FileWriter fw = new FileWriter(file);
                        fw.write (buf.toString());
                        fw.close();
                    }
                } catch (IOException e) {
                    Log.e("", "Could not create file.", e);
                    return;
                }
            }
        } catch (java.io.FileNotFoundException e) {
            Toast.makeText(getApplicationContext(), "FileNotFoundException", Toast.LENGTH_LONG);
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "FileNotFoundException", Toast.LENGTH_LONG);
        }
        inputStream = getResources().openRawResource(R.raw.textes);
        try {
            if (inputStream != null) {
                // open a reader on the inputStream
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

                // String used to store the lines
                String str;
                StringBuilder buf = new StringBuilder();

                // Read the file
                while ((str = reader.readLine()) != null) {
                    buf.append(str + "\r\n");
                }
                // Close streams
                reader.close();
                inputStream.close();
                File file = new File("storage/emulated/0/App/Resources/TEXT_RESOURCE.txt");
                try {
                    if (!file.exists()) {
                        new File(file.getParent()).mkdirs();
                        file.createNewFile();
                        FileWriter fw = new FileWriter(file);
                        fw.write (buf.toString());
                        fw.close();
                    }
                } catch (IOException e) {
                    Log.e("", "Could not create file.", e);
                    return;
                }
            }
        } catch (java.io.FileNotFoundException e) {
            Toast.makeText(getApplicationContext(), "FileNotFoundException", Toast.LENGTH_LONG);
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "FileNotFoundException", Toast.LENGTH_LONG);
        }
    }
}
