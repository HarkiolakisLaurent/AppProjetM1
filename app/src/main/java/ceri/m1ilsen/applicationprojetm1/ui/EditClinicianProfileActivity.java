package ceri.m1ilsen.applicationprojetm1.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import ceri.m1ilsen.applicationprojetm1.R;
import ceri.m1ilsen.applicationprojetm1.language.Language;
import ceri.m1ilsen.applicationprojetm1.sqlite.MyApplicationDataSource;
import ceri.m1ilsen.applicationprojetm1.user.Clinician;
import ceri.m1ilsen.applicationprojetm1.user.Patient;

public class EditClinicianProfileActivity extends AppCompatActivity {

    public TextView loginField;
    public EditText mailField;
    public EditText newPasswordField;
    public EditText confirmNewPasswordField;
    public Button saveChangesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_clinician_profile);

        loginField = (TextView) findViewById(R.id.loginField);
        mailField = (EditText) findViewById(R.id.mailField);
        newPasswordField = (EditText) findViewById(R.id.newPasswordField);
        confirmNewPasswordField = (EditText) findViewById(R.id.confirmNewPasswordField);

        loginField.setText(getIntent().getStringExtra("connectedUserPseudo"));
        mailField.setText(getIntent().getStringExtra("connectedUserMail"));

        saveChangesButton = (Button) findViewById(R.id.saveChangesButton);
        saveChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplicationDataSource BD = new MyApplicationDataSource(getApplicationContext());
                BD.open();
                if(!(mailField.getText().toString().equals(""))) {
                    if (BD.verificationExistingClinicianByPseudo(loginField.getText().toString(),
                            getIntent().getExtras().getInt("connectedUserId"))) {
                        if (!(newPasswordField.getText().toString().equals("")) && !(confirmNewPasswordField.getText().toString().equals(""))) {
                            if (newPasswordField.getText().toString().equals(confirmNewPasswordField.getText().toString())) {
                                Clinician clinician = new Clinician(mailField.getText().toString(), newPasswordField.getText().toString(), loginField.getText().toString(), null);
                                BD.updateClinician(getIntent().getExtras().getInt("connectedUserId"),clinician);
                                BD.close();
                                getIntent().putExtra("connectedUserMail",mailField.getText().toString());
                                setResult(10001,getIntent());
                                finish();
                            }
                        }
                        else if ((newPasswordField.getText().toString().equals("")) && (confirmNewPasswordField.getText().toString().equals(""))) {
                            Clinician clinician = new Clinician(mailField.getText().toString(), newPasswordField.getText().toString(), loginField.getText().toString(), null);
                            BD.partiallyUpdateClinician(getIntent().getExtras().getInt("connectedUserId"),clinician);
                            BD.close();
                            getIntent().putExtra("connectedUserMail", mailField.getText().toString());
                            setResult(10001, getIntent());
                            finish();
                        }
                    }
                }

            }
        });
    }
}
