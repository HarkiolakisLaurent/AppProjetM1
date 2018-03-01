package ceri.m1ilsen.applicationprojetm1.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ToggleButton;

import java.sql.Date;

import ceri.m1ilsen.applicationprojetm1.language.Language;
import ceri.m1ilsen.applicationprojetm1.user.*;
import ceri.m1ilsen.applicationprojetm1.R;
import ceri.m1ilsen.applicationprojetm1.sqlite.CommentsDataSource;


public class SignUpActivity extends AppCompatActivity {
    public EditText pseudo;
    public EditText mdp;
    public EditText mail;
    public EditText mdpc;
    public EditText birth;
    public ToggleButton tg;
    public Spinner genre;
    public Spinner langue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        pseudo =  (EditText) findViewById(R.id.pseudo);
        mdp =  (EditText) findViewById(R.id.mdp);
        mail =  (EditText) findViewById(R.id.mail);
        mdpc =  (EditText) findViewById(R.id.mdpc);
        tg = (ToggleButton) findViewById(R.id.clini);
        birth = (EditText) findViewById(R.id.date_n);
        genre = (Spinner) findViewById(R.id.genre);
        langue = (Spinner) findViewById(R.id.langue);

    }

    public void retour(View view) {
        Intent intent = new Intent(this,MainActivity.class);
        CommentsDataSource BD = new CommentsDataSource(this);
        BD.open();
        if((pseudo.getText() != null) && (mdp.getText() != null) && (mdpc.getText() != null) && (mail.getText() != null)) {
            if (!BD.verification(pseudo.getText().toString(), mdp.getText().toString())) {
                if (mdp.getText().toString().equals(mdpc.getText().toString())) {
                    if (!tg.isActivated()) {
                        Boolean sex = false;
                        if (genre.getSelectedItemPosition() == 0) {
                            sex = true;
                        }
                        String[] nums = birth.getText().toString().split(""+birth.getText().toString().charAt(2));
                        String zeDate = nums[2]+"-"+nums[1]+"-"+nums[0];
                        Patient P = new Patient(mail.getText().toString(), mdp.getText().toString(), pseudo.getText().toString(),
                                "", "", Date.valueOf(zeDate), sex, Language.Français, null, null, null);
                        BD.insertPatient(P);
                    } else {
                        Clinician P = new Clinician(mail.getText().toString(), mdp.getText().toString(), pseudo.getText().toString(),
                                "", "", null);
                        BD.insertClinicien(P);
                    }
                    startActivity(intent);
                }
            }
        }
        BD.close();
    }

    public void Exo (View view) {
        Intent intent = new Intent(this,CreateExerciceActivity.class);
        startActivity(intent);
    }
}



