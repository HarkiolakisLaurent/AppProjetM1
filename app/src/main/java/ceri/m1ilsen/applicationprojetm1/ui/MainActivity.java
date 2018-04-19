package ceri.m1ilsen.applicationprojetm1.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import ceri.m1ilsen.applicationprojetm1.R;
import ceri.m1ilsen.applicationprojetm1.sqlite.MyApplicationDataSource;
import ceri.m1ilsen.applicationprojetm1.user.Clinician;
import ceri.m1ilsen.applicationprojetm1.user.Patient;

public class MainActivity extends AppCompatActivity {
    private TextView signUp;
    private Button signIn;
    private EditText mail;
    private EditText mdp;
    private TextView forgotPassword;

    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;
    private static final int MY_PERMISSIONS_REQUEST_RECORD_AUDIO = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkNeededPermissions();

        mail = (EditText) findViewById(R.id.mail);
        mdp = (EditText) findViewById(R.id.password);
        signUp = (TextView) findViewById(R.id.signup);
        forgotPassword = (TextView) findViewById(R.id.forgotPassword);
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent passwordRecovery = new Intent(MainActivity.this,PatientActivity.class   );
                startActivity(passwordRecovery);
            }
        });
        final MyApplicationDataSource BD = new MyApplicationDataSource(this);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signupActivity = new Intent(MainActivity.this, SignUpActivity.class);
                startActivityForResult(signupActivity,1000);
            }
        });

        signIn = (Button) findViewById(R.id.signin);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent patientActivity = new Intent(MainActivity.this, PatientActivity.class);

                BD.open();
                if (BD.verificationPatientByPseudoAndPassword(mail.getText().toString(), mdp.getText().toString()) ){
                    Patient patient = BD.getPatientByPseudoAndPassword(mail.getText().toString(), mdp.getText().toString());
                    int patientId = BD.getPatientIdByPseudo(patient.getPseudo());
                    /*Session session  = new Session(new Date(System.currentTimeMillis()), null , null , p);
                    BD.insertSession(session);
                    Contexte.patient=p;*/
                    patientActivity.putExtra("connectedUserMail",patient.getMail());
                    patientActivity.putExtra("connectedUserPseudo",patient.getPseudo());
                    patientActivity.putExtra("connectedUserLastName",patient.getLastName());
                    patientActivity.putExtra("connectedUserFirstName",patient.getFirstName());
                    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                    patientActivity.putExtra("connectedUserBirthday",df.format(patient.getBirthday()));
                    patientActivity.putExtra("connectedUserGender",patient.isGender());
                    patientActivity.putExtra("connectedUserLanguage",patient.getSpokenLanguage());
                    patientActivity.putExtra("connectedUserId",patientId);
                    BD.close();
                    startActivityForResult(patientActivity,1000);

                } else if (BD.verificationPatientByMailAndPassword(mail.getText().toString(), mdp.getText().toString())) {
                    Patient patient = BD.getPatientByMailAndPassword(mail.getText().toString(), mdp.getText().toString());
                    int patientId = BD.getPatientIdByPseudo(patient.getPseudo());
                    /*Session session  = new Session(new Date(System.currentTimeMillis()), null , null , p);
                    BD.insertSession(session);
                    Contexte.patient=p;*/
                    patientActivity.putExtra("connectedUserMail",patient.getMail());
                    patientActivity.putExtra("connectedUserPseudo",patient.getPseudo());
                    patientActivity.putExtra("connectedUserLastName",patient.getLastName());
                    patientActivity.putExtra("connectedUserFirstName",patient.getFirstName());
                    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                    patientActivity.putExtra("connectedUserBirthday",df.format(patient.getBirthday()));
                    if (patient.isGender() == true)
                        patientActivity.putExtra("connectedUserGender",patient.isGender());
                    else
                        patientActivity.putExtra("connectedUserGender",patient.isGender());
                    patientActivity.putExtra("connectedUserLanguage",patient.getSpokenLanguage());
                    patientActivity.putExtra("connectedUserId",patientId);
                    BD.close();
                    startActivityForResult(patientActivity,1000);

                } else if (BD.verificationClinicianByPseudoAndPassword(mail.getText().toString(), mdp.getText().toString())) {
                    Intent clinicianActivity = new Intent(MainActivity.this, ClinicianActivity.class);
                    Clinician clinician = BD.getClinicianByPseudoAndPassword(mail.getText().toString(), mdp.getText().toString());
                    int clinicianId = BD.getClinicianIdByPseudo(clinician.getPseudo());
                    clinicianActivity.putExtra("connectedUserMail",clinician.getMail());
                    clinicianActivity.putExtra("connectedUserPseudo",clinician.getPseudo());
                    clinicianActivity.putExtra("connectedUserId",clinicianId);
                    BD.close();
                    startActivityForResult(clinicianActivity,1000);

                }

                else if (BD.verificationClinicianByMailAndPassword(mail.getText().toString(), mdp.getText().toString())) {
                    Intent clinicianActivity = new Intent(MainActivity.this, ClinicianActivity.class);
                    Clinician clinician = BD.getClinicianByMailAndPassword(mail.getText().toString(), mdp.getText().toString());
                    int clinicianId = BD.getClinicianIdByPseudo(clinician.getPseudo());
                    clinicianActivity.putExtra("connectedUserMail",clinician.getMail());
                    clinicianActivity.putExtra("connectedUserPseudo",clinician.getPseudo());
                    clinicianActivity.putExtra("connectedUserId",clinicianId);
                    BD.close();
                    startActivityForResult(clinicianActivity,1000);
                }
                else {
                    Toast.makeText(getApplicationContext(),"Identifiant et/ou mot de passe incorrects",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void checkNeededPermissions() {

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                //Cela signifie que la permission à déjà été
                //demandé et l'utilisateur l'a refusé
                //Vous pouvez aussi expliquer à l'utilisateur pourquoi
                //cette permission est nécessaire et la redemander
            } else {
                //Sinon demander la permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
            }
        }

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
            }
        }

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.RECORD_AUDIO)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_RECORD_AUDIO);
            }
        }
    }

    @Override
    public void onResume() {
        //datasource.open();
        super.onResume();
    }

    @Override
    public void onPause() {
        //datasource.close();
        super.onPause();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        //Check the result and request code here and update ur activity class
        if ((requestCode == 1000)/* && (resultCode == Activity.RESULT_OK)*/) {
            mail.setText("");
            mdp.setText("");
        }
    }
}
