package ceri.m1ilsen.applicationprojetm1.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import ceri.m1ilsen.applicationprojetm1.R;

/**
 * Created by le fléo on 16/03/2018.
 */

public class ClinicianResultsActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.clinician_navigation_patients:
                    Intent exercises = new Intent(ClinicianResultsActivity.this, MonitorPatientsActivity.class);
                    startActivity(exercises);
                    return true;
                case R.id.clinician_navigation_recordings:
                    Intent recordings = new Intent(ClinicianResultsActivity.this, PatientRecordingsActivity.class);
                    startActivity(recordings);
                    return true;
                case R.id.clinician_navigation_results:
                    Intent results = new Intent(ClinicianResultsActivity.this, PatientResultsActivity.class);
                    startActivity(results);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_results);

        String[] sessions ;


            }
}
