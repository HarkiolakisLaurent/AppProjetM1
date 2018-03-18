package ceri.m1ilsen.applicationprojetm1.ui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import ceri.m1ilsen.applicationprojetm1.R;
import ceri.m1ilsen.applicationprojetm1.fragment.ClinicianHomePageFragment;
import ceri.m1ilsen.applicationprojetm1.fragment.ClinicianRecordingsFragment;
import ceri.m1ilsen.applicationprojetm1.fragment.ClinicianResultsFragment;
import ceri.m1ilsen.applicationprojetm1.fragment.MonitorPatientsFragment;

public class ClinicianActivity extends AppCompatActivity {

    private ClinicianHomePageFragment homePageFragment = new ClinicianHomePageFragment();
    private MonitorPatientsFragment patientsFragment = new MonitorPatientsFragment();
    private ClinicianRecordingsFragment recordingsFragment = new ClinicianRecordingsFragment();
    private ClinicianResultsFragment resultsFragment = new ClinicianResultsFragment();
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinician);
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
            case R.id.action_home:
                return true;
            case R.id.action_conf:
                Intent settings = new Intent(this, SettingsActivity.class);
                startActivity(settings);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
