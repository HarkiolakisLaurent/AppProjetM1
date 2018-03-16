package ceri.m1ilsen.applicationprojetm1.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

import ceri.m1ilsen.applicationprojetm1.R;
import ceri.m1ilsen.applicationprojetm1.exercise.Exercise;
import ceri.m1ilsen.applicationprojetm1.exercise.Session;
import ceri.m1ilsen.applicationprojetm1.sqlite.CommentsDataSource;
import ceri.m1ilsen.applicationprojetm1.sqlite.Contexte;
import ceri.m1ilsen.applicationprojetm1.user.Patient;

public class MainActivity extends AppCompatActivity {
    private CommentsDataSource datasource;
    private TextView signUp = null;
    private Button signIn = null;
    private Button testLaurent;
    private Button testMeryem;
    public EditText mail;
    public EditText mdp;
    public TextView forgotPassword;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        testLaurent = (Button) findViewById(R.id.testLaurent);
        testLaurent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextActivity = new Intent(MainActivity.this, EditProfileActivity.class);
                startActivity(nextActivity);
            }
        });
        testMeryem = (Button) findViewById(R.id.testMeryem);
        testMeryem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextActivity = new Intent(MainActivity.this, CreateExerciceActivity2.class);
                startActivity(nextActivity);
            }
        });

        signIn = (Button) findViewById(R.id.signin);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextActivity = new Intent(MainActivity.this, PatientSheetHomeActivity.class);

                BD.open();
                if (BD.verification(mail.getText().toString(), mdp.getText().toString()) ){
                    int p = BD.getPatientP(mail.getText().toString(), mdp.getText().toString());
                    Session session  = new Session(new Date(System.currentTimeMillis()), null , null , p);
                    BD.insertSession(session);
                    BD.close();
                    Contexte.patient=p;
                    startActivity(nextActivity);

                } else if (BD.verificationM(mail.getText().toString(), mdp.getText().toString())) {
                    int p = BD.getPatientM(mail.getText().toString(), mdp.getText().toString());
                    Session session  = new Session(new Date(System.currentTimeMillis()), null , null , p);
                    BD.insertSession(session);
                    BD.close();
                    Contexte.patient=p;
                    startActivity(nextActivity);

                } else if (BD.verificationC(mail.getText().toString(), mdp.getText().toString()) || BD.verificationCM(mail.getText().toString(), mdp.getText().toString()) ) {
                    nextActivity.setAction("ClinicienSheetHomeActivity.class");
                    BD.close();
                    startActivity(nextActivity);
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
