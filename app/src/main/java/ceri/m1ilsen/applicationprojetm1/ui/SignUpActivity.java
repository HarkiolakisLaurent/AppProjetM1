package ceri.m1ilsen.applicationprojetm1.ui;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.CheckBox;
import android.widget.Toast;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import ceri.m1ilsen.applicationprojetm1.language.Language;
import ceri.m1ilsen.applicationprojetm1.user.*;
import ceri.m1ilsen.applicationprojetm1.R;
import ceri.m1ilsen.applicationprojetm1.sqlite.MyApplicationDataSource;

public class SignUpActivity extends AppCompatActivity {
    private EditText pseudo;
    private EditText nom;
    private EditText prenom;
    private EditText mdp;
    private EditText mail;
    private EditText mdpc;
    private EditText birth;
    private EditText favouriteWord;
    private CheckBox tg;
    private Spinner genre;
    private Spinner langue;
    private ImageButton dateChooser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        pseudo =  (EditText) findViewById(R.id.loginField);
        mdp =  (EditText) findViewById(R.id.newPasswordField);
        nom =  (EditText) findViewById(R.id.lastNameField);
        prenom =  (EditText) findViewById(R.id.firstNameField);
        mail =  (EditText) findViewById(R.id.mailField);
        mdpc =  (EditText) findViewById(R.id.confirmNewPasswordField);
        tg = (CheckBox) findViewById(R.id.clini);
        birth = (EditText) findViewById(R.id.birthdayField);
        genre = (Spinner) findViewById(R.id.genreField);
        langue = (Spinner) findViewById(R.id.languageField);
        favouriteWord = (EditText) findViewById(R.id.favouriteWordField);
        dateChooser = (ImageButton) findViewById(R.id.dateChooser);
        dateChooser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(0);
            }
        });
    }

    @Override
    @Deprecated
    protected Dialog onCreateDialog(int id) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(this, datePickerListener, year, month, day);
        dialog.getDatePicker().setMaxDate(c.getTimeInMillis());
        return  dialog;
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            Calendar now = Calendar.getInstance();
            now.set(selectedYear,selectedMonth,selectedDay);
            if (selectedDay > 9 && selectedMonth > 9) {
                birth.setText(selectedDay+"/"+(selectedMonth + 1)+"/"+selectedYear);
            }
            else {
                if (selectedDay < 10)
                    birth.setText("0"+selectedDay+"/"+(selectedMonth + 1)+"/"+selectedYear);
                if (selectedMonth < 10)
                    birth.setText(selectedDay+"/0"+(selectedMonth + 1)+"/"+selectedYear);
                if (selectedDay < 10 && selectedMonth < 10)
                    birth.setText("0"+selectedDay+"/0"+(selectedMonth + 1)+"/"+selectedYear);
            }

        }
    };

    public void creerCompte(View view) {
        MyApplicationDataSource BD = new MyApplicationDataSource(this);
        BD.open();
        if(!(pseudo.getText().toString().equals("")) && !(mdp.getText().toString().equals("")) && !(mdpc.getText().toString().equals("")) && !(mail.getText().toString().equals("")) && !(favouriteWord.getText().toString().equals("")) && !(prenom.getText().toString().equals("")) && !(nom.getText().toString().equals(""))) {
            if (!BD.verificationPatientByPseudoAndPassword(pseudo.getText().toString(), mdp.getText().toString()) && !BD.verificationPatientByMailAndPassword(mail.getText().toString(),mdp.getText().toString())) {
                if (mdp.getText().toString().equals(mdpc.getText().toString())) {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    sdf.setLenient(true);
                    try {
                        Date d = sdf.parse(birth.getText().toString() );
                        String t = sdf.format(d);
                        if (t.compareTo(birth.getText().toString()) == 0) {
                            if (mail.getText().toString().matches("[A-Za-z_\\-\\.]*[@]\\w*[\\.][A-Za-z]*")) {
                                if (!tg.isChecked()) {
                                    Boolean sex = false;
                                    if (genre.getSelectedItemPosition() == 0) {
                                        sex = true;
                                    }
                                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MMM/yyyy");
                                    java.util.Date utilDate = new java.util.Date();
                                    try {
                                        utilDate = formatter.parse(birth.getText().toString());

                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                                    Patient patient = new Patient(mail.getText().toString(), mdp.getText().toString(), pseudo.getText().toString(),
                                            nom.getText().toString(), prenom.getText().toString(), sqlDate, sex, Language.Français, 0, null, favouriteWord.getText().toString(), null);
                                    BD.insertPatient(patient);

                                    final File recordingsDirectory = new File("/storage/emulated/0/App/Recordings/" + pseudo.getText().toString());
                                    if (!recordingsDirectory.exists()) {
                                        recordingsDirectory.mkdirs();
                                    }
                                    final File exercisesDirectory = new File("/storage/emulated/0/App/Exercises/");
                                    if (!exercisesDirectory.exists()) {
                                        exercisesDirectory.mkdirs();
                                    }

                                } else {
                                    Clinician clinician = new Clinician(mail.getText().toString(), mdp.getText().toString(), pseudo.getText().toString(), favouriteWord.getText().toString(), null);
                                    BD.insertClinician(clinician);

                                    final File recordingsDirectory = new File("/storage/emulated/0/App/Recordings/");
                                    if (!recordingsDirectory.exists()) {
                                        recordingsDirectory.mkdirs();
                                    }
                                    final File exercisesDirectory = new File("/storage/emulated/0/App/Exercises/");
                                    if (!exercisesDirectory.exists()) {
                                        exercisesDirectory.mkdirs();
                                    }

                                }
                                this.setResult(1000);
                                this.finish();
                            } else {
                                Toast.makeText(this, "Le format de l'adresse email saisie est invalide", Toast.LENGTH_LONG).show();
                            }
                        }
                        else{
                            Toast.makeText(this,"Le format de la date de naissance saisie est invalide",Toast.LENGTH_LONG).show();
                        }

                    } catch (Exception e) {
                        Toast.makeText(this,"Le format de la date de naissance saisie est invalide",Toast.LENGTH_LONG).show();
                    }

                }else{
                    Toast.makeText(this,"Les deux mots de passes saisis sont différents",Toast.LENGTH_LONG).show();
                }
            }else{
                Toast.makeText(this,"Ce compte existe déjà",Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(this,"Vous devez remplir tous les champs",Toast.LENGTH_LONG).show();
        }
        BD.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_retour, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_return:
                Activity activity = SignUpActivity.this;
                activity.setResult(1);
                activity.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}