package ceri.m1ilsen.applicationprojetm1.ui;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import ceri.m1ilsen.applicationprojetm1.R;
import ceri.m1ilsen.applicationprojetm1.fragment.PatientRecordingsFragment;

public class PatientActivity extends AppCompatActivity {

    private PatientRecordingsFragment frag1 = new PatientRecordingsFragment();
    private FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.patient_navigation_home:
                    return true;
                case R.id.patient_navigation_exercises:
                    return true;
                case R.id.patient_navigation_recordings:
                    fragmentTransaction.replace(R.id.fragmentContainer, frag1);
                    fragmentTransaction.commit();

                    return true;
                case R.id.patient_navigation_results:
                    return true;
            }
            return false;
        }
    };
}
