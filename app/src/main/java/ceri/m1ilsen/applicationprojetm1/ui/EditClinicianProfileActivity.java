package ceri.m1ilsen.applicationprojetm1.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import ceri.m1ilsen.applicationprojetm1.R;
import ceri.m1ilsen.applicationprojetm1.language.Language;
import ceri.m1ilsen.applicationprojetm1.sqlite.MyApplicationDataSource;
import ceri.m1ilsen.applicationprojetm1.user.Clinician;
import ceri.m1ilsen.applicationprojetm1.user.Patient;

public class EditClinicianProfileActivity extends AppCompatActivity {

    public EditText loginField;
    public EditText mailField;
    public EditText newPasswordField;
    public EditText confirmNewPasswordField;
    public Button saveChangesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_clinician_profile);

        loginField = (EditText) findViewById(R.id.loginField);
        mailField = (EditText) findViewById(R.id.mailField);
        newPasswordField = (EditText) findViewById(R.id.newPasswordField);
        confirmNewPasswordField = (EditText) findViewById(R.id.confirmNewPasswordField);

        loginField.setText(getIntent().getStringExtra("connectedUserPseudo"));
        mailField.setText(getIntent().getStringExtra("connectedUserMail"));

        saveChangesButton = (Button) findViewById(R.id.saveChangesButton);
        saveChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // mettre à jour les infos ici
                /*MyApplicationDataSource BD = new MyApplicationDataSource(getApplicationContext());
                BD.open();
                if(!(loginField.getText().toString().equals("")) && !(newPasswordField.getText().toString().equals("")) && !(confirmNewPasswordField.getText().toString().equals("")) && !(mailField.getText().toString().equals(""))) {
                    if (!BD.verificationClinicianByPseudoAndPassword(loginField.getText().toString(), newPasswordField.getText().toString()) && !BD.verificationClinicianByMailAndPassword(mailField.getText().toString(),newPasswordField.getText().toString())) {
                        if (newPasswordField.getText().toString().equals(confirmNewPasswordField.getText().toString())) {
                            Clinician clinician = new Clinician(mailField.getText().toString(), newPasswordField.getText().toString(),
                                    loginField.getText().toString(),null);
                            BD.updateClinician(getIntent().getExtras().getInt("connectedUserId"),clinician);
                            BD.close();
                            Intent intent = getIntent();
                            intent.putExtra("connectedUserMail",mailField.getText().toString());
                            intent.putExtra("connectedUserPseudo",loginField.getText().toString());
                            setResult(10001,intent);
                            finish();
                        }
                    }
                }
                BD.close();*/

                MyApplicationDataSource BD = new MyApplicationDataSource(getApplicationContext());
                BD.open();
                if(!(loginField.getText().toString().equals("")) && !(newPasswordField.getText().toString().equals("")) && !(confirmNewPasswordField.getText().toString().equals("")) && !(mailField.getText().toString().equals(""))) {
                    if (BD.verificationExistingClinicianByPseudo(loginField.getText().toString(),
                            getIntent().getExtras().getInt("connectedUserId")) ||
                            BD.verificationExistingClinicianByMail(mailField.getText().toString(),
                                    getIntent().getExtras().getInt("connectedUserId"))) {
                        if (newPasswordField.getText().toString().equals(confirmNewPasswordField.getText().toString())) {

                            Clinician clinician = new Clinician(mailField.getText().toString(), newPasswordField.getText().toString(), loginField.getText().toString(), null);
                            BD.updateClinician(getIntent().getExtras().getInt("connectedUserId"),clinician);
                            BD.close();
                            getIntent().putExtra("connectedUserMail",mailField.getText().toString());
                            getIntent().putExtra("connectedUserPseudo",loginField.getText().toString());
                            setResult(10001,getIntent());
                            finish();
                        }
                    }
                }

                BD.close();
                setResult(11,getIntent());
                finish();

            }
        });
    }
}
