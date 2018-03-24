package ceri.m1ilsen.applicationprojetm1.ui;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import ceri.m1ilsen.applicationprojetm1.R;
import ceri.m1ilsen.applicationprojetm1.language.Language;
import ceri.m1ilsen.applicationprojetm1.sqlite.MyApplicationDataSource;
import ceri.m1ilsen.applicationprojetm1.user.Patient;

public class EditPatientProfileActivity extends AppCompatActivity {
    public TextView lastNameField;
    public TextView firstNameField;
    public TextView birthdayField;
    public EditText loginField;
    public EditText mailField;
    public Spinner languageField;
    public Spinner genreField;
    public EditText newPasswordField;
    public EditText confirmNewPasswordField;
    public Button saveChangesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_patient_profile);
        lastNameField = (TextView) findViewById(R.id.lastNameField);
        firstNameField = (TextView) findViewById(R.id.firstNameField);
        birthdayField = (TextView) findViewById(R.id.birthdayField);
        loginField = (EditText) findViewById(R.id.loginField);
        mailField = (EditText) findViewById(R.id.mailField);
        languageField = (Spinner) findViewById(R.id.languageField);
        genreField = (Spinner) findViewById(R.id.genreField);
        newPasswordField = (EditText) findViewById(R.id.newPasswordField);
        confirmNewPasswordField = (EditText) findViewById(R.id.confirmNewPasswordField);

        lastNameField.setText(getIntent().getStringExtra("connectedUserLastName"));
        firstNameField.setText(getIntent().getStringExtra("connectedUserFirstName"));
        birthdayField.setText(getIntent().getStringExtra("connectedUserBirthday"));
        loginField.setText(getIntent().getStringExtra("connectedUserPseudo"));
        mailField.setText(getIntent().getStringExtra("connectedUserMail"));
        languageField.setSelection(0);
        if (getIntent().getExtras().getBoolean("connectedUserGender") == true)
            genreField.setSelection(0);
        else
            genreField.setSelection(1);

        saveChangesButton = (Button) findViewById(R.id.saveChangesButton);
        saveChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplicationDataSource BD = new MyApplicationDataSource(getApplicationContext());
                BD.open();
                if(!(loginField.getText().toString().equals("")) && !(newPasswordField.getText().toString().equals("")) && !(confirmNewPasswordField.getText().toString().equals("")) && !(mailField.getText().toString().equals(""))) {
                    if (BD.verificationExistingPatientByPseudo(loginField.getText().toString(),
                            getIntent().getExtras().getInt("connectedUserId")) ||
                            BD.verificationExistingPatientByMail(mailField.getText().toString(),
                                    getIntent().getExtras().getInt("connectedUserId"))) {
                        if (newPasswordField.getText().toString().equals(confirmNewPasswordField.getText().toString())) {
                            Boolean sex = false;
                            if (genreField.getSelectedItemPosition() == 0) {
                                sex = true;
                            }
                            SimpleDateFormat formatter = new SimpleDateFormat("dd/MMM/yyyy");
                            java.util.Date utilDate = new java.util.Date();
                            try {
                                utilDate = formatter.parse(birthdayField.getText().toString());

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                            Patient patient = new Patient(mailField.getText().toString(), newPasswordField.getText().toString(), loginField.getText().toString(),
                                    lastNameField.getText().toString(), firstNameField.getText().toString(), sqlDate, sex, Language.Français, 0, null, null);
                            BD.updatePatient(getIntent().getExtras().getInt("connectedUserId"),patient);
                            /*Toast.makeText(getApplicationContext(),
                                    "Le patient aura les informations suivantes : "+
                                    "id : "+getIntent().getExtras().getInt("connectedUserId")+
                                    "mail : "+mailField.getText().toString()+
                                    "mdp : "+newPasswordField.getText().toString()+
                                    "login : "+loginField.getText().toString()+
                                    "nom : "+lastNameField.getText().toString()+
                                    "prenom : "+firstNameField.getText().toString()+
                                    "date : "+sqlDate+
                                    "genre : "+sex+
                                    "langue : "+Language.Français
                                    ,Toast.LENGTH_LONG).show();*/
                            BD.close();
                            getIntent().putExtra("connectedUserMail",mailField.getText().toString());
                            getIntent().putExtra("connectedUserPseudo",loginField.getText().toString());
                            getIntent().putExtra("connectedUserGender",sex);
                            getIntent().putExtra("connectedUserLanguage",Language.Français);
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
