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

import java.util.Date;

import ceri.m1ilsen.applicationprojetm1.R;
import ceri.m1ilsen.applicationprojetm1.exercise.Session;
import ceri.m1ilsen.applicationprojetm1.sqlite.CommentsDataSource;
import ceri.m1ilsen.applicationprojetm1.sqlite.Contexte;
import ceri.m1ilsen.applicationprojetm1.user.Clinician;
import ceri.m1ilsen.applicationprojetm1.user.Patient;

public class MainActivity extends AppCompatActivity {
    private CommentsDataSource datasource;
    private TextView signUp = null;
    private Button signIn = null;
    private Button testPatient;
    private Button testClinicien;
    public EditText mail;
    public EditText mdp;
    public TextView forgotPassword;

    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        mail = (EditText) findViewById(R.id.mail);
        mdp = (EditText) findViewById(R.id.password);
        signUp = (TextView) findViewById(R.id.signup);

        final CommentsDataSource BD = new CommentsDataSource(this);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextActivity = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(nextActivity);
            }
        });

        signIn = (Button) findViewById(R.id.signin);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent patientActivity = new Intent(MainActivity.this, PatientActivity.class);

                BD.open();
                if (BD.verificationPatientByPseudoAndPassword(mail.getText().toString(), mdp.getText().toString()) ){
                    String patient = BD.getPatientPseudoByPseudoAndPassword(mail.getText().toString(), mdp.getText().toString());
                    /*Session session  = new Session(new Date(System.currentTimeMillis()), null , null , p);
                    BD.insertSession(session);
                    Contexte.patient=p;*/
                    patientActivity.putExtra("connectedUserPseudo",patient);
                    BD.close();
                    startActivity(patientActivity);

                } else if (BD.verificationPatientByMailAndPassword(mail.getText().toString(), mdp.getText().toString())) {
                    String patient = BD.getPatientPseudoByMailAndPassword(mail.getText().toString(), mdp.getText().toString());
                    /*Session session  = new Session(new Date(System.currentTimeMillis()), null , null , p);
                    BD.insertSession(session);
                    Contexte.patient=p;*/
                    patientActivity.putExtra("connectedUserPseudo",patient);
                    BD.close();
                    startActivity(patientActivity);

                } else if (BD.verificationClinicianByPseudoAndPassword(mail.getText().toString(), mdp.getText().toString())) {
                    Intent clinicianActivity = new Intent(MainActivity.this, ClinicianActivity.class);
                    String clinicianPseudo = BD.getClinicianPseudoByPseudoAndPassword(mail.getText().toString(), mdp.getText().toString());
                    int clinicianId = BD.getClinicianIdByPseudo(clinicianPseudo);
                    clinicianActivity.putExtra("connectedUserPseudo",clinicianPseudo);
                    clinicianActivity.putExtra("connectedUserId",clinicianId);
                    BD.close();
                    startActivity(clinicianActivity);

                }

                else if (BD.verificationClinicianByMailAndPassword(mail.getText().toString(), mdp.getText().toString())) {
                    Intent clinicianActivity = new Intent(MainActivity.this, ClinicianActivity.class);
                    String clinicianPseudo = BD.getClinicianPseudoByMailAndPassword(mail.getText().toString(), mdp.getText().toString());
                    int clinicianId = BD.getClinicianIdByPseudo(clinicianPseudo);
                    clinicianActivity.putExtra("connectedUserPseudo",clinicianPseudo);
                    clinicianActivity.putExtra("connectedUserId",clinicianId);
                    BD.close();
                    startActivity(clinicianActivity);
                }
                else {
                    Toast.makeText(getApplicationContext(),"Identifiant et/ou mot de passe incorrects",Toast.LENGTH_SHORT).show();
                }
            }
        });
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
}
