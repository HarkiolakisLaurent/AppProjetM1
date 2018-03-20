package ceri.m1ilsen.applicationprojetm1.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ceri.m1ilsen.applicationprojetm1.R;

public class EditClinicianProfileActivity extends AppCompatActivity {

    public Button saveChangesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_clinician_profile);
        saveChangesButton = (Button) findViewById(R.id.saveChangesButton);
        saveChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // mettre à jour les infos ici
                setResult(1);
                finish();

            }
        });
    }
}
