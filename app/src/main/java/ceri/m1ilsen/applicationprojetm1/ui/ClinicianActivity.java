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
import android.widget.Toast;

import java.io.File;

import ceri.m1ilsen.applicationprojetm1.R;
import ceri.m1ilsen.applicationprojetm1.fragment.ClinicianHomePageFragment;
import ceri.m1ilsen.applicationprojetm1.fragment.ClinicianRecordingsFragment;
import ceri.m1ilsen.applicationprojetm1.fragment.ClinicianResultsFragment;
import ceri.m1ilsen.applicationprojetm1.fragment.ClinicianSettingsFragment;
import ceri.m1ilsen.applicationprojetm1.fragment.MonitorPatientsFragment;

public class ClinicianActivity extends AppCompatActivity {

    private ClinicianHomePageFragment homePageFragment = new ClinicianHomePageFragment();
    private MonitorPatientsFragment patientsFragment = new MonitorPatientsFragment();
    private ClinicianRecordingsFragment recordingsFragment = new ClinicianRecordingsFragment();
    private ClinicianResultsFragment resultsFragment = new ClinicianResultsFragment();
    private ClinicianSettingsFragment settingsFragment = new ClinicianSettingsFragment();
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinician);

        final File recordingsDirectory = new File("/storage/emulated/0/App/Recordings/");
        if (!recordingsDirectory.exists()) {
            recordingsDirectory.mkdirs();
        }
        final File exercisesDirectory = new File("/storage/emulated/0/App/Exercises/");
        if (!exercisesDirectory.exists()) {
            exercisesDirectory.mkdirs();
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
                case R.id.clinician_navigation_home:
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentContainer, homePageFragment);
                    fragmentTransaction.commit();
                    return true;
                case R.id.clinician_navigation_patients:
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentContainer, patientsFragment);
                    fragmentTransaction.commit();
                    return true;
                case R.id.clinician_navigation_recordings:
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentContainer, recordingsFragment);
                    fragmentTransaction.commit();
                    return true;
                case R.id.clinician_navigation_results:
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentContainer, resultsFragment);
                    fragmentTransaction.commit();
                    return true;
                case R.id.clinician_navigation_settings:
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
                final Activity activity = ClinicianActivity.this;
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
