package ceri.m1ilsen.applicationprojetm1.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.io.File;

import ceri.m1ilsen.applicationprojetm1.R;
import ceri.m1ilsen.applicationprojetm1.fragment.CreateExerciseFragment;
import ceri.m1ilsen.applicationprojetm1.fragment.PatientHomePageFragment;
import ceri.m1ilsen.applicationprojetm1.fragment.PatientRecordingsFragment;
import ceri.m1ilsen.applicationprojetm1.fragment.PatientResultsFragment;
import ceri.m1ilsen.applicationprojetm1.fragment.PatientSettingsFragment;

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

        final File recordingsDirectory = new File("/storage/emulated/0/recordings/"+getIntent().getStringExtra("connectedUserPseudo"));
        if (!recordingsDirectory.exists()) {
            recordingsDirectory.mkdirs();
        }

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
}
