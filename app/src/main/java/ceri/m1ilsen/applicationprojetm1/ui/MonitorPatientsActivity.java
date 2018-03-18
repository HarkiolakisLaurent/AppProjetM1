package ceri.m1ilsen.applicationprojetm1.ui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import ceri.m1ilsen.applicationprojetm1.R;

public class MonitorPatientsActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.clinician_navigation_patients:
                    return true;
                case R.id.clinician_navigation_recordings:
                    Intent recordings = new Intent(MonitorPatientsActivity.this, PatientRecordingsActivity.class);
                    startActivity(recordings);
                    return true;
                case R.id.clinician_navigation_results:
                    Intent results = new Intent(MonitorPatientsActivity.this, PatientResultsActivity.class);
                    startActivity(results);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor_patients);
    }
}
